package test;

import model.POMDPImp;

import solver.criteria.Criteria;
import solver.criteria.MaxIterationsCriteria;
import solver.criteria.ValueConvergenceCriteria;
import solver.iteration.ValueIterationTimer;
import solver.PbParams;
import solver.PointBasedStd;

public class PerseusDynamicTest {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		//tiger/tiger.95.POMDP
        POMDPImp pomdp=(POMDPImp) POMDPImp.Factory.parse("test.POMDP");
        double epsi=1e-6*(1-pomdp.gamma())/(2*pomdp.gamma());
		PbParams params=new PbParams(PbParams.BACKUP_ASYNC_FULL,PbParams.EXPAND_RANDOM_EXPLORE_DYNAMIC,1,100,100);
		PointBasedStd algo= new PointBasedStd(pomdp,params);
		algo.addCriteria(new MaxIterationsCriteria(100));
		algo.addCriteria(new ValueConvergenceCriteria(epsi,
                                                      Criteria.CC_MAXDIST));
		algo.run();
		System.out.println(algo.getValueFunction());
		ValueIterationTimer
                stat=(ValueIterationTimer) algo.getTimer();
		System.out.println(stat);
		//System.out.println(val);
	}

}
