package test;

import model.POMDPImp;

import solver.Criteria;
import solver.MaxIterationsCriteria;
import solver.vi.ValueConvergenceCriteria;
import solver.vi.ValueIterationStats;
import solver.exact.IncrementalPruningStd;

public class IncrementalPruningTest {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		//tiger/tiger.95.POMDP
        POMDPImp pomdp=(POMDPImp)POMDPImp.Factory.parse("test.POMDP");
        double epsi=1e-6*(1-pomdp.gamma())/(2*pomdp.gamma());
		IncrementalPruningStd algo= new IncrementalPruningStd(pomdp,epsi);
		algo.addStopCriteria(new MaxIterationsCriteria(100));
		algo.addStopCriteria(new ValueConvergenceCriteria(epsi,Criteria.CC_MAXDIST));
		algo.run();
		System.out.println(algo.getValueFunction());
		ValueIterationStats stat=(ValueIterationStats) algo.getStats();
		System.out.println(stat);
		//System.out.println(val);
	}

}
