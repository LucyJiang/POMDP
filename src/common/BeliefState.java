/** ------------------------------------------------------------------------- *
 * libpomdp
 --------------------------------------------------------------------------- */

package common;


import model.Vector;

/**
 * BeliefState, a belif point associated with a probability
 */
public interface BeliefState {

    /**
     * get the belief point in the state
     * @return
     */
    public Vector getPoint();

    /**
     * Compare two BeliefState
     */
    public boolean compare(BeliefState bel);


    /**
     * get a copy of this BeliefState
     */
    public BeliefState copy();
}

