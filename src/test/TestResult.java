package test;

import common.ValueFunction;
import model.POMDP;
import solver.Solver;
import solver.iteration.ValueIterationSolver;
import solver.iteration.ValueIterationTimer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LeoDong on 27/04/2015.
 */
public class TestResult {
    private String testName;
    private long initTime;
    private List<Long> iterTime;
    private int iterationNumber;

    //For ValueIterationSolver
    private List<Integer> vectorNumber;
    private ValueFunction valueFunction;
    private double value;

    public TestResult(String name,POMDP model, Solver solver){
        this.testName = name;
        this.initTime = solver.getTimer().getInitTime();
        this.iterTime = solver.getTimer().getTimeRecords();

        this.iterationNumber = solver.getTimer().getIterNumber();
        if(solver instanceof ValueIterationSolver){
            ValueIterationSolver solverP = (ValueIterationSolver)solver;
            this.vectorNumber = ((ValueIterationTimer)solverP.getTimer()).getVectorCounter();
            this.valueFunction = solverP.getValueFunction();
            this.value = this.valueFunction.V(model.getInitBeliefState());
        }else{
            this.vectorNumber = null;
            this.valueFunction = null;
            this.value = Double.NaN;
        }

    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TestResult:["+testName+"]\n");
        sb.append("---------------------\n");
        sb.append("Iter Number\t|\t").append(iterationNumber).append("\n");

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



}
