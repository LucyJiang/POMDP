package solver.pb;

import common.AlphaVector;
import common.BeliefState;
import model.POMDP;
import common.BeliefMdpImp;
import common.BeliefStateImp;
import model.POMDPImp;
import common.ValueFunctionImp;
import model.Vector;
import solver.IterationStats;
import solver.vi.ValueIterationStd;

public class PointBasedStd extends ValueIterationStd {
	
	BeliefMdpImp bmdp;
	PointSet fullBset;
	PointSet newBset;
	PbParams params;
	
	public AlphaVector getLowestAlpha(){
		double best_val=bmdp.getRewardMaxMin();
		best_val=best_val/(1-bmdp.gamma());
		return(new AlphaVector(new Vector(bmdp.numS(),best_val),-1));
	}
	
	public PointBasedStd(POMDPImp pomdp,PbParams params){
		startTimer();
		initValueIteration(pomdp);
		this.params=params;
		bmdp=new BeliefMdpImp(pomdp);
		current = new ValueFunctionImp(pomdp.numS());
		current.push(getLowestAlpha());
		registerInitTime();
	}
	
	public IterationStats iterate() {
		startTimer();
		old=current;
		expand();

		for (int i=0;i<params.backupHorizon;i++){
			backup();
		}
		current.prune();
		if (params.isNewPointsOnly())
			fullBset=newBset;
		//System.out.println(current);
		registerValueIterationStats();
    	return iterationStats;
	}

	protected void backup(){
		switch(params.getBackupMethod()){
		case PbParams.BACKUP_SYNC_FULL:
			current=syncBackup(fullBset);
			break;
		case PbParams.BACKUP_SYNC_NEWPOINTS:
			current=syncBackup(newBset);
			break;
		case PbParams.BACKUP_ASYNC_FULL:
			current=asyncBackup(fullBset);
			break;
		case PbParams.BACKUP_ASYNC_NEWPOINTS:
			current=asyncBackup(fullBset);
			break;
		}
	}

	private AlphaVector backup(BeliefState bel, ValueFunctionImp vf){
		AlphaVector alpha_max=null;
		double alpha_max_val=Double.NEGATIVE_INFINITY;
		for (int a=0;a<bmdp.numA();a++){
			AlphaVector alpha_sum=new AlphaVector(bmdp.numS(),a);
			for (int o=0;o<bmdp.numO();o++){
				double max_val=Double.NEGATIVE_INFINITY;
				AlphaVector max_vect=null;
				for (int idx=0;idx<vf.size();idx++){
					AlphaVector prev= vf.getAlphaVector(idx);
					AlphaVector vect=bmdp.projection(prev, a, o);
					double val=vect.eval(bel);
					if (val>max_val){
						max_val=val;
						max_vect=vect;
					}
				}
				alpha_sum.add(max_vect);
			}
			AlphaVector re=bmdp.getRewardValueFunction(a).getBestAlpha(bel);
			alpha_sum.add(re);
			double alpha_val=alpha_sum.eval(bel);
			if (alpha_val>alpha_max_val){
				alpha_max_val=alpha_val;
				alpha_max=alpha_sum;
			}
		}
		return(alpha_max);
	}
	
	private ValueFunctionImp asyncBackup(PointSet bset) {
		ValueFunctionImp newv=new ValueFunctionImp(bmdp.numS());
		PointSet testBset = bset.copy();
		while(testBset.size()!=0){
			BeliefState bel=testBset.getRandom();
			bset.remove(bel);
			AlphaVector alpha=backup(bel,old);
			if (alpha.eval(bel) >= old.V(bel))
				newv.push(alpha);
			else
				newv.push(old.getBestAlpha(bel));
			PointSet tabu=new PointSet();
			for (BeliefState beltest:testBset){
				if (newv.V(beltest) >= old.V(beltest)){
					tabu.add(beltest);
				}
			}
			for (BeliefState beltest:tabu)
				testBset.remove(beltest);
		}
		return newv;
	}

	protected ValueFunctionImp syncBackup(PointSet bset){
		ValueFunctionImp newv=new ValueFunctionImp(bmdp.numS());
		for (BeliefState bel:bset){
			newv.push(backup(bel,old));
		}
		return newv;
	}
	
	protected void expand() {
		newBset = new PointSet();
		if (fullBset == null) {
			fullBset = new PointSet();
			fullBset.add(bmdp.getInitBeliefState());
			newBset.add(bmdp.getInitBeliefState());
		}
		if (fullBset.size() >= params.getMaxTotalPoints())
			return;
		
		PointSet testBset = fullBset.copy();
		while (fullBset.size() < params.getMaxTotalPoints()
				|| newBset.size() < params.getMaxNewPoints()) {
			BeliefStateImp point = null;
			switch (params.getExpandMethod()) {
			case PbParams.EXPAND_GREEDY_ERROR_REDUCTION:
				point = collectGreedyErrorReduction(testBset);
				break;
			case PbParams.EXPAND_EXPLORATORY_ACTION:
				point = collectExploratoryAction(testBset);
				break;
			case PbParams.EXPAND_RANDOM_EXPLORE_STATIC:
			case PbParams.EXPAND_RANDOM_EXPLORE_DYNAMIC:
				point = collectRandomExplore(testBset,bmdp);
				break;
			}
			if (point!=null){
				fullBset.add(point);
				newBset.add(point.copy());
			}
			if(testBset.size() == 0){
				if (params.getExpandMethod()==PbParams.EXPAND_RANDOM_EXPLORE_STATIC )
					testBset=fullBset.copy();
				else
					break;
			}
		}
	}

	private BeliefStateImp collectExploratoryAction(PointSet testBset) {
		BeliefStateImp b=(BeliefStateImp) testBset.remove(0);
		double max_dist=Double.NEGATIVE_INFINITY;
		BeliefStateImp bnew=null;
		for (int a=0;a<bmdp.numA();a++){
			int o = bmdp.getRandomObservation(b, a);
			BeliefStateImp ba = (BeliefStateImp) bmdp.nextBeliefState(b, a, o);
			double dist=distance(ba,fullBset);
			if (dist > max_dist){
				max_dist=dist;
				bnew=ba;
			}
		}
		if (max_dist==0.0) 
			bnew=null;
		return(bnew);
	}

	private BeliefStateImp collectGreedyErrorReduction(PointSet testBset) {
		double max_val=Double.NEGATIVE_INFINITY;
		BeliefState bprime = null;
		int aprime = -1;
		int oprime = -1;
		for (BeliefState b:testBset){	
			for (int a=0;a<bmdp.numA();a++){
				double sum_err=0;
				for (int o=0;o<bmdp.numO();o++){
					double err=bmdp.getTau(a, o).operate(b.getPoint()).getL1Norm();
					double tttt = minError(bmdp.nextBeliefState(b, a, o),testBset);
                    err*=minError(bmdp.nextBeliefState(b, a, o),testBset);
					sum_err+=err;
				}
				
				if (sum_err>max_val){
					max_val=sum_err;
					bprime=b;
					aprime=a;
				}
			}
		}
		max_val=Double.NEGATIVE_INFINITY;
		for (int o=0;o<bmdp.numO();o++){
			double err=(bmdp.getTau(aprime, o).operate(bprime.getPoint())).getL1Norm();
			err*=minError(bmdp.nextBeliefState(bprime, aprime, o),testBset);
			if (err>max_val){
				max_val=err;
				oprime=o;
			}
		}
		testBset.remove(bprime);
		return (BeliefStateImp) (bmdp.nextBeliefState(bprime, aprime, oprime));
	}

	private double minError(BeliefState beliefState,PointSet bset){
		double rmax=bmdp.getRewardMax()/(1.0 - bmdp.gamma());
		double rmin=bmdp.getRewardMin()/(1.0 - bmdp.gamma());
		double min_val=Double.POSITIVE_INFINITY;
		for (BeliefState b:bset){
			double sum=0;
			AlphaVector vect=current.getBestAlpha(b);
			for (int s=0;s<bmdp.numS();s++){
				double bdiff=beliefState.getPoint().getEntry(s)- b.getPoint().getEntry(
                        s);
				if (bdiff>=0)
					sum+=(rmax - vect.getVectorRef().getEntry(s))*bdiff;
				else
					sum+=(rmin - vect.getVectorRef().getEntry(s))*bdiff;
			}
			if (sum<min_val)
				min_val=sum;
		}
		return(min_val);
	}
	
	public static BeliefStateImp collectRandomExplore(PointSet testBset,
                                                      POMDP bmdp) {
		BeliefStateImp b=(BeliefStateImp) testBset.remove(0);
		BeliefStateImp bprime;
		int a = bmdp.getRandomAction();
		int o = bmdp.getRandomObservation(b, a);
		bprime = (BeliefStateImp) bmdp.nextBeliefState(b, a, o);
		return bprime;
	}

	private double distance(BeliefStateImp ba,PointSet newBset) {
		double min_val=Double.POSITIVE_INFINITY;
	    for (BeliefState bprime:newBset){
	    	Vector vect=bprime.getPoint().copy();
	    	vect = new Vector(vect.mapMultiply(-1.0).add(ba.getPoint()));
	    	double val=vect.getL1Norm();
	    	if (val < min_val)
	    		min_val=val;
	    }
		return min_val;
	}
	
	
}
