package test;

import model.POMDP;
import solver.*;
import solver.criteria.Criteria;
import solver.criteria.MaxIterationsCriteria;
import solver.criteria.ValueConvergenceCriteria;
import solver.iteration.ValueIterationTimer;

import java.util.ArrayList;

/**
 * Created by LeoDong on 27/04/2015.
 */
public class Tester {

    private ArrayList<Criteria> criterias;
    private POMDP POMDP;

    public Tester(POMDP model){
        this.POMDP = model;
        criterias = new ArrayList<Criteria>();
    }

    public void limitMaxIterationNumber(int max){
        this.criterias.add(new MaxIterationsCriteria(max));
    }

    public void limitValueConvergence(double epsi){
        this.criterias.add(new ValueConvergenceCriteria(epsi));
    }

    public TestResult TestQMDP(){
        Solver solver= new QmdpSolver(this.POMDP);
        for (Criteria c : this.criterias){
            solver.addCriteria(c);
        }
        solver.run();
        TestResult result = new TestResult("QMDP",this.POMDP,solver);
        return result;
    }

    public TestResult TestIncrementalPruning(double epsi){
        Solver solver= new IncrementalPruningSolver(this.POMDP,epsi);
        for (Criteria c : this.criterias){
            solver.addCriteria(c);
        }
        solver.run();
        TestResult result = new TestResult("IncrementalPruning",this.POMDP,solver);
        return result;
    }

    public TestResult TestGreedyPBVI(){
        PointBasedSolverParam param =new PointBasedSolverParam(
                PointBasedSolverParam.BACKUP_SYNC_FULL,
                PointBasedSolverParam.EXPAND_GREEDY_ERROR_REDUCTION,100);
        return TestPBVI("GreedyPBVI", this.POMDP, param);
    }

    public TestResult TestExploratoryPBVI(){
        PointBasedSolverParam param=new PointBasedSolverParam(
                PointBasedSolverParam.BACKUP_SYNC_FULL,
                PointBasedSolverParam.EXPAND_EXPLORATORY_ACTION,1);
        return TestPBVI("ExploratoryPBVI", this.POMDP, param);
    }

    public TestResult TestPerseusPBVI(){
        PointBasedSolverParam param=new PointBasedSolverParam(
                PointBasedSolverParam.BACKUP_ASYNC_FULL,
                PointBasedSolverParam.EXPAND_RANDOM_EXPLORE,1,100,100);
        return TestPBVI("PerseusPBVI", this.POMDP, param);
    }


    private TestResult TestPBVI(String name,POMDP model, PointBasedSolverParam param){

        Solver solver= new PointBasedSolver(model,param);
        for (Criteria c : this.criterias){
            solver.addCriteria(c);
        }
        solver.run();
        TestResult result = new TestResult(name,model,solver);
        return result;
    }




}
