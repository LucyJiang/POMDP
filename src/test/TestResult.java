package test;

import common.ValueFunction;
import model.POMDP;
import solver.Solver;
import solver.iteration.ValueIterationSolver;
import solver.iteration.ValueIterationTimer;

import java.util.ArrayList;
import java.util.List;

/**
 * TestResult including time and value relative information
 */
public class TestResult {
    // test name
    private String testName;

    // time consume on initial
    private long initTime;

    // time consume on each iteration
    private ArrayList<Long> iterTime;

    // iteration number
    private int iterationNumber;

    // time consume totally
    private double totalTime;

    //For ValueIterationSolver
    private ArrayList<Integer> vectorNumber;
    private ValueFunction valueFunction;
    private double value;

    private TestResult() {
        this.testName = "";
        this.initTime = 0l;
        this.iterTime = null;
        this.iterationNumber = 0;
        this.totalTime = 0l;

        this.vectorNumber = null;
        this.valueFunction = null;
        this.value = 0;
    }

    /**
     * Constructor, it will pick up information from the given solver
     * @param name
     * @param model
     * @param solver
     */
    public TestResult(String name, POMDP model, Solver solver) {
        this.testName = name;
        this.initTime = solver.getTimer().getInitTime();
        this.iterTime = solver.getTimer().getTimeRecords();
        this.totalTime = solver.getTimer().getTotalTime();

        this.iterationNumber = solver.getTimer().getIterNumber();
        if (solver instanceof ValueIterationSolver) {
            ValueIterationSolver solverP = (ValueIterationSolver) solver;
            this.vectorNumber = ((ValueIterationTimer) solverP.getTimer())
                    .getVectorCounter();
            this.valueFunction = solverP.getValueFunction();
            this.value = this.valueFunction.V(model.getInitBeliefState());
        } else {
            this.vectorNumber = null;
            this.valueFunction = null;
            this.value = Double.NaN;
        }
    }

    /**
     * figure out a average TestResult from the List of TestResult
     * @param name
     * @param list
     * @return
     */
    public static TestResult average(String name, List<TestResult> list) {
        int size = list.size();
        TestResult res = new TestResult();

        for (TestResult r : list) {
            res.initTime += r.initTime;
            res.iterationNumber += r.iterationNumber;
            res.totalTime += r.totalTime;

            if (res.iterTime == null) {
                res.iterTime = (ArrayList<Long>) r.iterTime.clone();
            } else {
                for (int i = 0; i < res.iterTime.size(); i++) {
                    res.iterTime
                            .set(i, res.iterTime.get(i) + r.iterTime.get(i));
                }
            }

            if (res.vectorNumber == null) {
                res.vectorNumber = (ArrayList<Integer>) r.vectorNumber.clone();
            } else {
                for (int i = 0; i < res.vectorNumber.size(); i++) {
                    res.vectorNumber.set(i,
                                         res.vectorNumber.get(i) +
                                         r.vectorNumber.get(i));
                }
            }
            res.value += r.value;
            res.valueFunction = r.valueFunction;
        }


        res.testName = name;
        res.initTime = res.initTime / size;

        res.iterationNumber = res.iterationNumber / size;
        res.totalTime = res.totalTime / size;

        for (int i = 0; i < res.iterTime.size(); i++) {
            res.iterTime.set(i, res.iterTime.get(i) / size);
        }
        for (int i = 0; i < res.vectorNumber.size(); i++) {
            res.vectorNumber.set(i, res.vectorNumber.get(i) / size);
        }

        res.value = res.value / size;
        return res;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer(
                "TestResult:[" + testName + "]\n");
        sb.append("---------------------\n");
        sb.append("Iter Number\t|\t").append(iterationNumber).append("\n");
        sb.append("Total Time\t|\t").append(totalTime).append("\n");

        sb.append("Init Time\t|\t").append(initTime).append("\n");
        sb.append("Iter Time\t|\t").append(iterTime).append("\n");

        sb.append("Vect Number\t|\t").append(vectorNumber).append("\n");
        sb.append("Value Exp\t|\t").append(value).append("\n");
        sb.append("Value Func:\n").append(valueFunction).append("\n");
        sb.append("=====================");
        return sb.toString();
    }

    public String getTestName() {
        return testName;
    }

    public long getInitTime() {
        return initTime;
    }

    public List<Long> getIterTime() {
        return iterTime;
    }

    public double getIterationNumber() {
        return iterationNumber;
    }

    public List<Integer> getVectorNumber() {
        return vectorNumber;
    }

    public ValueFunction getValueFunction() {
        return valueFunction;
    }

    public double getValue() {
        return value;
    }

    public double getTotalTime() {
        return totalTime;
    }

}
