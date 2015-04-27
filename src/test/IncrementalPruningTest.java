package test;

import model.POMDPImp;

import solver.criteria.Criteria;
import solver.criteria.MaxIterationsCriteria;
import solver.criteria.ValueConvergenceCriteria;
import solver.iteration.ValueIterationTimer;
import solver.IncrementalPruningStd;

public class IncrementalPruningTest {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		//tiger/tiger.95.POMDP
        POMDPImp pomdp=(POMDPImp) POMDPImp.Factory.parse("test.POMDP");
        double epsi=1e-6*(1-pomdp.gamma())/(2*pomdp.gamma());
		IncrementalPruningStd algo= new IncrementalPruningStd(pomdp,epsi);
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
