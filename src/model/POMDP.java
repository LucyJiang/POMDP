
package model;

import common.BeliefState;
import common.ValueFunctionImp;
import org.apache.commons.math3.linear.RealMatrix;


public interface POMDP {

    /**
     * Tau function
     */
    public BeliefState nextBeliefState(BeliefState b, int a, int o);

    /**
     * Immediate Reward function
     */
    public double expectedImmediateReward(BeliefState b, int a);

    /**
     * observation probabilities
     */
    public Vector observationProbabilities(BeliefState bel, int a);

    /**
     * T function matrix
     * @param a
     * @return
     */
    public RealMatrix TforA(int a);

    /**
     * O function matrix
     * @param a
     * @return
     */
    public RealMatrix ZforA(int a);

    /**
     * R function vector
     * @param a
     * @return
     */
    public Vector RforA(int a);

    /**
     * Initial BeliefState
     * @return
     */
    public BeliefState getInitBeliefState();

    // getters of #S, #A, #O and their name
    public int numS();

    public int numA();

    public int numO();

    public String actionName(int a);
    public String observationName(int o);
    public String stateName(int s);

    public double gamma();
    public int getRandomAction();
    public int getRandomObservation(BeliefState bel, int a);
    public double getRewardMax();
    public double getRewardMin();
    public double getRewardMaxMin();

    public ValueFunctionImp getRewardValueFunction(int a);
}
