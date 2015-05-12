/** ------------------------------------------------------------------------- *
 * libpomdp
 --------------------------------------------------------------------------- */


package model;

import common.AlphaVector;
import org.apache.commons.math3.linear.RealMatrix;

/**
 * Belief MDP
 */
public interface BMDP extends POMDP {

    /**
     * get the inner POMDP
     * @return
     */
    public POMDP getPomdp();

    /**
     * Tau function
     * @param a
     * @param o
     * @return
     */
    public RealMatrix getTau(int a, int o);

    /**
     * get the target alpha-vector projected from a alpha-vector with a given
     * action and observation
     * @param alpha
     * @param a
     * @param o
     * @return
     */
    public AlphaVector projection(AlphaVector alpha, int a, int o);
}
