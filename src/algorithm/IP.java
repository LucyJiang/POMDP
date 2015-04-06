package algorithm;

import pomdp.Action;
import pomdp.ActionSet;

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

        return act;
    }

    /**
     * Lark's algorithm for purging a set of vectors
     *
     * @param remainAction
     * @return winners, initially empty, is filled with vectors that have non-empty witness regions
     */
    private ActionSet filter(ActionSet remainAction) {

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
