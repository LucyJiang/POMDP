package solver.vi;

import common.AlphaVector;
import common.CustomVector;
import common.ValueFunction;
import common.ValueFunctionImp;
import model.Vector;
import solver.Criteria;
import solver.Iteration;

public class ValueConvergenceCriteria extends Criteria {

	double epsilon;
	int convCriteria;
	
	static final int MIN_ITERATIONS = 5;
	
	public boolean check(Iteration i) {
		ValueIteration vi=(ValueIteration)i;
		ValueFunction newv=vi.getValueFunction();
		ValueFunction oldv=vi.getOldValueFunction();
		if (oldv==null  || newv.size()!=oldv.size()){
			System.out.println("Eval(" + i.getStats().iterations + ") = Inf");
			return false;
		}
		((ValueFunctionImp)newv).sort();
		((ValueFunctionImp)oldv).sort();
		double conv=0;
		for(int j=0; j<newv.size(); j++){
			AlphaVector newAlpha=newv.getAlphaVector(j);
			AlphaVector oldAlpha=oldv.getAlphaVector(j);
			if (newAlpha.getAction()!=oldAlpha.getAction()){
				System.out.println("Eval(" + i.getStats().iterations + ") = Inf");
				return false;
			}
			Vector perf= new Vector(newAlpha.getVectorCopy().mapMultiply(-1.0).add(oldAlpha.getVectorRef()));
			double a_value=0;
			switch(convCriteria){
			case CC_MAXEUCLID:
				a_value = perf.getNorm();
				break;
			case CC_MAXDIST:
				a_value = perf.getNorm();
				break;
			}
			if (a_value > conv)
				conv=a_value;
		}
		System.out.println("Eval(" + i.getStats().iterations + ") = " + conv);
		if (conv <= epsilon && i.getStats().iterations > MIN_ITERATIONS)
			return(true);
		return false;
 	}

	@Override
	public boolean valid(Iteration vi) {
		if (vi instanceof ValueIteration){
			return true;
		}
		return false;
	}

	public ValueConvergenceCriteria(double epsilon,int convCriteria) {
		this.epsilon=epsilon;
		this.convCriteria=convCriteria;
	}
	


}
