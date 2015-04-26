package algorithm;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import pomdp.*;

/**
*
*/
public class IP extends POMDPAlgorithm {

    public IP() {
        super();
    }

    /**
     * @return the Action to move
     */
    protected Action decide() {
        //TODO core algorithm, decide the action to move

        Action act = null; //TODO the action adapted
        RealVector constants = new ArrayRealVector(new double[] { 1, -2, 1 }, false);

        return act;
    }


}
