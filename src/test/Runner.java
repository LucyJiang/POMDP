package test;

import model.POMDP;
import model.POMDPImp;
import solver.PointBasedSolver;
import solver.PointBasedSolverParam;
import solver.criteria.MaxIterationsCriteria;
import solver.criteria.ValueConvergenceCriteria;
import solver.iteration.ValueIterationTimer;

/**
 * Created by LeoDong on 28/04/2015.
 */
public class Runner {
    public static void main(String[] args) {

        POMDP pomdp= POMDPImp.Factory.parse("test.POMDP");
        System.out.println(pomdp);

        Tester tester = new Tester(pomdp);

        double epsi=1e-6*(1-pomdp.gamma())/(2*pomdp.gamma());
        tester.limitMaxIterationNumber(50);
        tester.limitValueConvergence(epsi);

        TestResult r1 = tester.TestExploratoryPBVI();
        TestResult r2 = tester.TestGreedyPBVI();
        TestResult r3 = tester.TestIncrementalPruning(epsi);
        TestResult r4 = tester.TestPerseusPBVI();
        TestResult r5 = tester.TestQMDP();

        System.out.println(r1);
        System.out.println(r2);
        System.out.println(r3);
        System.out.println(r4);
        System.out.println(r5);



    }
}
