
package common;


import model.Vector;

/**
 * Representation of a set of alpha vectors and their associated actions for
 * direct control (if possible)
 *
 * @author Diego Maniloff
 * @author Mauricio Araya
 */
public interface ValueFunction {

    public double V(BeliefState b);

    public int[] getActions();

    public int size();

    public AlphaVector getAlphaVector(int idx);

    public Vector getAlphaValues(int idx);

} // ValueFunction
