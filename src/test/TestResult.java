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
        final StringBuffer sb = new StringBuffer("TestResult{");
        sb.append("testName='").append(testName).append('\'');
        sb.append(", initTime=").append(initTime);
        sb.append(", iterTime=").append(iterTime);
        sb.append(", iterationNumber=").append(iterationNumber);
        sb.append(", vectorNumber=").append(vectorNumber);
        sb.append(", valueFunction=").append(valueFunction);
        sb.append(", value=").append(value);
        sb.append('}');
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

    private double iterationNumber;

}
