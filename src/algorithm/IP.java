package algorithm;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import pomdp.*;

import java.util.Vector;

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
        ActionSet winners = new ActionSet();
        Action winner;
        RealVector infoState

        for (State currentState: ss.states()){
            winner = currentState.getActionSet().getHighestRewardAction();
            winners.addAction(winner.getId(),winner);
            remainAction.removeAction(winner.getId());

            while (!remainAction.actions().isEmpty()){
                for (Action currentAction: remainAction.actions()){
                    infoState = this.dominate(currentAction,winners);
                    if (!infoState){
                        remainAction.removeAction(currentAction.getId());
                    }else {
                        winner =
                    }
                }
            }
        }

    }

    /**
     * Linear-programming approach to finding an information state
     * in a vector's witness region
     *
     * @return
     */
    private RealVector dominate(Action a, ActionSet as) {

    }


}
