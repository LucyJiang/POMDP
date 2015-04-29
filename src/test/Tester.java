package test;

import model.POMDP;
import solver.IncrementalPruningSolver;
import solver.PointBasedSolver;
import solver.PointBasedSolverParam;
import solver.Solver;
import solver.criteria.Criteria;
import solver.criteria.MaxIterationsCriteria;
import solver.criteria.ValueConvergenceCriteria;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by LeoDong on 27/04/2015.
 */
public class Tester {

    private ArrayList<Criteria> criterias;
    private POMDP POMDP;

    public Tester(POMDP model) {
        this.POMDP = model;
        criterias = new ArrayList<Criteria>();
    }

    public static List<TestResult> FullTestSet(
            POMDP pomdp,
            int setNum,
            int iterNum) {

        Tester tester = new Tester(pomdp);
        tester.limitMaxIterationNumber(iterNum);
        tester.limitValueConvergence(tester.getDefaultEpsi());

        HashMap<String, ArrayList<TestResult>> rr
                = new HashMap<String, ArrayList<TestResult>>();
        for (int i = 0; i < setNum; i++) {
            List<TestResult> res = Tester.FullTest(tester);
            for (TestResult r : res) {
                if (!rr.containsKey(r.getTestName())) {
                    rr.put(r.getTestName(), new ArrayList<TestResult>());
                }
                rr.get(r.getTestName()).add(r);
            }
        }
        List<TestResult> finalRes = new ArrayList<TestResult>();

        for (String key : rr.keySet()) {
            //avg
            List<TestResult> r = rr.get(key);
            assert r.size() == setNum;
            finalRes.add(TestResult.average(key, r));
        }
        return finalRes;
    }

    private static List<TestResult> FullTest(Tester tester) {
        System.out.println(tester.POMDP);
        ArrayList<TestResult> testResults = new ArrayList<TestResult>();

        System.out.println("Test [GreedyPBVI]:");
        testResults.add(tester.UnitTestGreedyPBVI());
        System.out.println("Test [ExploratoryPBVI]:");
        testResults.add(tester.UnitTestExploratoryPBVI());
        System.out.println("Test [PerseusPBVI]:");
        testResults.add(tester.UnitTestPerseusPBVI());
        System.out.println("Test [IncrementalPruning]:");
        testResults
                .add(tester.UnitTestIncrementalPruning(tester.getDefaultEpsi()));

        System.out.println("\nTest Results:\n");
        for (TestResult r : testResults) {
            System.out.println(r + "\n");
        }
        return testResults;
    }

    public static List<TestResult> FullTest(POMDP pomdp, int iterNum) {
        Tester tester = new Tester(pomdp);
        tester.limitMaxIterationNumber(iterNum);
        tester.limitValueConvergence(tester.getDefaultEpsi());
        return Tester.FullTest(tester);
    }

    public double getDefaultEpsi() {
        return 1e-6 * (1 - this.POMDP.gamma()) / (2 * this.POMDP.gamma());
    }

    public void limitMaxIterationNumber(int max) {
        this.criterias.add(new MaxIterationsCriteria(max));
    }

    public void limitValueConvergence(double epsi) {
        this.criterias.add(new ValueConvergenceCriteria(epsi));
    }

    public TestResult UnitTestIncrementalPruning(double epsi) {
        Solver solver = new IncrementalPruningSolver(this.POMDP, epsi);
        for (Criteria c : this.criterias) {
            solver.addCriteria(c);
        }
        solver.run();
        TestResult result = new TestResult("IncrementalPruning",
                                           this.POMDP,
                                           solver);
        return result;
    }

    public TestResult UnitTestGreedyPBVI() {
        PointBasedSolverParam param = new PointBasedSolverParam(
                PointBasedSolverParam.BACKUP_SYNC_FULL,
                PointBasedSolverParam.EXPAND_GREEDY_ERROR_REDUCTION, 1);
        return TestPBVI("GreedyPBVI", this.POMDP, param);
    }

    public TestResult UnitTestExploratoryPBVI() {
        PointBasedSolverParam param = new PointBasedSolverParam(
                PointBasedSolverParam.BACKUP_SYNC_FULL,
                PointBasedSolverParam.EXPAND_EXPLORATORY_ACTION, 1);
        return TestPBVI("ExploratoryPBVI", this.POMDP, param);
    }

    public TestResult UnitTestPerseusPBVI() {
        PointBasedSolverParam param = new PointBasedSolverParam(
                PointBasedSolverParam.BACKUP_ASYNC_FULL,
                PointBasedSolverParam.EXPAND_RANDOM_EXPLORE, 1, 100);
        return TestPBVI("PerseusPBVI", this.POMDP, param);
    }


    private TestResult TestPBVI(
            String name,
            POMDP model,
            PointBasedSolverParam param) {

        Solver solver = new PointBasedSolver(model, param);
        for (Criteria c : this.criterias) {
            solver.addCriteria(c);
        }
        solver.run();
        TestResult result = new TestResult(name, model, solver);
        return result;
    }


}
