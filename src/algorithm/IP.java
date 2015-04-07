package algorithm;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import pomdp.Action;
import pomdp.ActionSet;
import pomdp.State;
import pomdp.StateSet;

import java.util.Iterator;

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
        constants.
        return act;
    }

    /**
     * Lark's algorithm for purging a set of vectors
     *
     * @param remainAction
     * @return winners, initially empty, is filled with vectors that have non-empty witness regions
     */
    private ActionSet filter(ActionSet remainAction, StateSet ss) {
        ActionSet winners;
        Iterator<State> stateIter = ss.states().iterator();

        while(stateIter.hasNext()){
            State s = stateIter.next();

        }

    }

    /**
     * Linear-programming approach to finding an information state
     * in a vector's witness region
     *
     * @return
     */
    private double dominate(Action, ActionSet) {

    }

}
