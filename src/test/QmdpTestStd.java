package test;

import model.POMDPImp;

import solver.Criteria;
import solver.MaxIterationsCriteria;
import solver.vi.ValueConvergenceCriteria;
import solver.vi.ValueIterationStats;
import solver.bounds.QmdpStd;

public class QmdpTestStd {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
        POMDPImp pomdp=(POMDPImp)POMDPImp.Factory.parse("test.POMDP");
        QmdpStd algo= new QmdpStd(pomdp);
		double epsi=1e-6*(1-pomdp.gamma())/(2*pomdp.gamma());
		algo.addStopCriteria(new MaxIterationsCriteria(100));
		algo.addStopCriteria(new ValueConvergenceCriteria(epsi,Criteria.CC_MAXDIST));
		algo.run();
		System.out.println(algo.getValueFunction());
		ValueIterationStats stat=(ValueIterationStats) algo.getStats();
		System.out.println(stat);
		//System.out.println(val);
	}

}
