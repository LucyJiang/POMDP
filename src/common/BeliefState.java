/** ------------------------------------------------------------------------- *
 * libpomdp
 * ========
 * File: BeliefState.java
 * Description: interface to implement different representations
 *              of a belief state
 *              properties are to be filled by extending classes
 * Copyright (c) 2009, 2010 Diego Maniloff, Mauricio Araya 
 --------------------------------------------------------------------------- */

package common;


import model.Vector;

public interface BeliefState {


    /**
     * Get belief state point as a custom vector.
     *
     * @return belief-point
     */
    public Vector getPoint();

    /**
     * Compare with other belief-state.
     *
     * @param bel a belief-state.
     * @return true if equals, false else
     */
    public boolean compare(BeliefState bel);


    /**
     * Create a proper copy of the belief-state.
     *
     * @return a belief-state copy
     */
    public BeliefState copy();
}

