package algorithm;

import pomdp.Action;

/**
 * Created by LeoDong on 12/03/2015.
 */
public class AlgorithmB extends POMDPAlgorithm {

    public AlgorithmB() {
        super();
    }

    /**
     * @return the Action to move
     */
    protected Action step() {
        //TODO core algorithm, decide the action to move

        Action act = null; //TODO the action adapted

        this.currentState = act.getToState();
        return act;
    }
}