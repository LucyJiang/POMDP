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

    private ArrayList<Solver> solvers = new ArrayList<Solver>();
    private ArrayList<Criteria> criterias = new ArrayList<Criteria>();
    private POMDP POMDP;

    public Tester(){

    }

    public static void TestPBVI(POMDP model,PbParams pbParams ){
//        PointBasedStd algo= new PointBasedStd(model,pbParams);
//        algo.addCriteria(new MaxIterationsCriteria(100));
//        algo.addCriteria(new ValueConvergenceCriteria(epsi,
//                                                      Criteria.CC_MAXDIST));
//        algo.run();
//        System.out.println(algo.getValueFunction());
//        ValueIterationTimer
//                stat=(ValueIterationTimer) algo.getTimer();
//        System.out.println(stat);
//        //System.out.println(val);
    }




}
