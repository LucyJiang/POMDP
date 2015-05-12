/** ------------------------------------------------------------------------- *
 * libpomdp
 --------------------------------------------------------------------------- */
package common;


import model.Vector;

/**
 * Value Function
 */
public interface ValueFunction {

    /**
     * take a BeliefState, then get a double (reward)
     * @param b
     * @return
     */
    public double V(BeliefState b);

    /**
     * get the actions' index in the alpha-vectors
     * @return
     */
    public int[] getActions();

    public int size();

    /**
     * get idx's AlphaVector
     * @param idx
     * @return
     */
    public AlphaVector getAlphaVector(int idx);

    public Vector getAlphaValues(int idx);

}
