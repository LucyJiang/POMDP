/** ------------------------------------------------------------------------- *
 * libpomdp
 * ========
 * File: Pomdp.java
 * Description: interface to represent Pomdp problem specifications
 * Copyright (c) 2009, 2010 Diego Maniloff 
 --------------------------------------------------------------------------- */

package model;

import common.BeliefState;
import common.BeliefStateImp;
import common.ValueFunctionImp;
import org.apache.commons.math3.linear.RealMatrix;


/** Interface to represent pomdp problem specifications
 * @author Diego Maniloff 
 * @author Mauricio Araya
*/

public interface POMDP {

    /// tao(b,a,o)
    public BeliefState nextBeliefState(BeliefState b, int a, int o);

    /// R(b,a): scalar value
    public double expectedImmediateReward(BeliefState b, int a);

    /// P(o|b,a): 1 x o in vector form for all o's
    public Vector observationProbabilities(BeliefState bel, int a);

    /// T(s,a,s'): s x s' matrix
    public RealMatrix TforA(int a);

    /// O(s',a,o): s' x o matrix
    public RealMatrix ZforA(int a);
    
    /// R(s,a): 1 x s vector
    public Vector RforA(int a);

    /// initial belief state
    public BeliefState getInitBeliefState();

    /** Get the number of states.
	@return the number of states */
    public int numS();

    /** Get the number of actions.
	@return the number of actions */
    public int numA();

    /** Get the number of observations.
	@return the number of observations */
    public int numO();

    /** Get the Gamma value.
	@return the gamma value*/
    public double gamma();

    /** Get the name of an action.
	@param a the action
 	@return the name of the action*/
    public String actionName(int a);

    /** Get the name of an observation.
	@param o the action
 	@return the name of the action*/
    public String observationName(int o);

    /** Get the name of a state.
	@param s the state
 	@return the name of the state*/
    public String stateName(int s);

    public int getRandomAction();

    public int getRandomObservation(BeliefStateImp bel, int a);

    public double getRewardMax();

    public double getRewardMin();

    public double getRewardMaxMin();

    public ValueFunctionImp getRewardValueFunction(int a);
}
