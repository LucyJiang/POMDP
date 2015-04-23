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

    /**
     * Lark's algorithm for purging a set of vectors
     *
     * @param remainAction
     * @return winners, initially empty, is filled with vectors that have non-empty witness regions
     */
    private ActionSet filter(ActionSet remainAction, StateSet ss) {
        ActionSet winners = new ActionSet();
        Action winner;
        RealVector infoState;
        //infoState = this.getInfoState(ss);

        for (State currentState: ss.states()){
            winner = currentState.getActionSet().getHighestRewardAction();
            winners.addAction(winner.getId(),winner);
            remainAction.removeAction(winner.getId());

            while (!remainAction.actions().isEmpty()){
                for (Action currentAction: remainAction.actions()){
                    infoState = this.dominate(currentAction,winners);
                    if (infoState.getDimension()==0){//? ??
                        remainAction.removeAction(currentAction.getId());
                    }else {
                        winner = this.getWinnerWithBelief(infoState,remainAction);
                        winners.addAction(winner.getId(),winner);
                        remainAction.removeAction(winner.getId());
                    }
                }
            }
        }
        return winners;
    }

    /**
     * Linear-programming approach to finding an information state
     * in a vector's witness region
     *
     * @return
     */
    private RealVector dominate(Action a, ActionSet as) {
        RealVector infoState;
    }

    private RealVector getInfoState (StateSet ss){
        RealVector infoState = new ArrayRealVector();
        double observationAfterAction;
        for (State currentState: ss.states()){
            for (Action action: currentState.getActionSet().actions()){
                observationAfterAction = action.getToStates().getObservation();
            }


            infoState.append(currentState.getObservation());
        }
        return infoState;
    }

    private RealVector getActionRewardVector(ActionSet as){
        RealVector rewardVector = new ArrayRealVector();
        for (Action currentAction: as.actions()){
            rewardVector.append(currentAction.getReward());
        }
    }

    public Action getWinnerWithBelief(RealVector infoState, ActionSet as){
        Action winner = null;
        double reward;
        double highestReward = -1;
        double actionReward;
        double observation;
        String toStateId;
        for (Action currentAction: as.actions()){
            actionReward = currentAction.getReward();
            toStateId = currentAction.getToStates().getId().substring(1);
            observation = infoState.getEntry(Integer.parseInt(toStateId));
            //observation = currentAction.getToStates().getObservation();
            reward = actionReward * observation;
            if(reward>=highestReward){
                winner=currentAction;
                highestReward=reward;
            }
        }
        return winner;
    }

}
