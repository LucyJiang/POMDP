/** ------------------------------------------------------------------------- *
 * libpomdp
 --------------------------------------------------------------------------- */
package common;


import model.Vector;

public interface ValueFunction {

    public double V(BeliefState b);

    public int[] getActions();

    public int size();

    public AlphaVector getAlphaVector(int idx);

    public Vector getAlphaValues(int idx);

} // ValueFunction
