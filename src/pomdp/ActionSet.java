package pomdp;

import exception.InconsistentException;

import java.util.*;

public class ActionSet {

    private TreeMap<String,Action> _this = new TreeMap<String, Action>();

    public boolean addAction(Action action) {
        _this.put(action.getId(),action);
        return true;
    }

    // check whether the ActionSet contains key id
    public boolean contains(String id) {
        return _this.containsKey(id);
    }

    // Get all the actions in this ActionSet
    public Collection<Action> actions() {
        return _this.values();
    }

    // Get all the ids in this ActionSet
    public Set<String> ids() {
        return _this.keySet();
    }

    // Get all the mappings in this ActionSet
    public Set<Map.Entry<String, Action>> pairs() {
        return _this.entrySet();
    }


    public void removeAction(String id){
        if(!_this.containsKey(id)){
            throw new InconsistentException("No action found in the ActionSet for id: "+id);
        }
        _this.remove(id);
    }

    /**
     *
     * @return null when no action
     */
    public Action getHighestRewardAction(){
        Action winner = null;
        double highestReward = Double.MIN_VALUE;

        for (Action currentAction: this.actions()){
            for(ActionOutTriple aot: currentAction.getOutTriples()){
                if (aot.getProbability()*aot.getReward()>=highestReward){
                    highestReward = aot.getProbability()*aot.getReward();
                    winner = currentAction;
                }
            }
        }
        return winner;
    }

    public String toString() {
        List<String> strings = new LinkedList<String>();
        for (Action a : actions()){
            strings.add(a.toString());
        }
        return String.join("\n",strings);
    }
	
}

