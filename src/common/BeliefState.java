/** ------------------------------------------------------------------------- *
 * libpomdp
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

