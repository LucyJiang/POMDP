package test;

import model.POMDPImp;

import solver.Criteria;
import solver.MaxIterationsCriteria;
import solver.vi.ValueConvergenceCriteria;
import solver.vi.ValueIterationStats;
import solver.pb.PbParams;
import solver.pb.PointBasedStd;

public class PbviFastTest {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		//tiger/tiger.95.POMDP
        POMDPImp pomdp=(POMDPImp)POMDPImp.Factory.parse("test.POMDP");
        double epsi=1e-6*(1-pomdp.gamma())/(2*pomdp.gamma());
		PbParams params=new PbParams(PbParams.BACKUP_SYNC_FULL,PbParams.EXPAND_EXPLORATORY_ACTION,1);
		PointBasedStd algo= new PointBasedStd(pomdp,params);
		algo.addStopCriteria(new MaxIterationsCriteria(100));
		algo.addStopCriteria(new ValueConvergenceCriteria(epsi,Criteria.CC_MAXDIST));
		algo.run();
		System.out.println(algo.getValueFunction());
		ValueIterationStats stat=(ValueIterationStats) algo.getStats();
		System.out.println(stat);
		//System.out.println(val);
	}

}
