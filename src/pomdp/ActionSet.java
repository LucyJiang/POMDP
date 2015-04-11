package pomdp;

import exception.InconsistentException;
import org.apache.commons.math3.linear.RealVector;

import java.util.*;

public class ActionSet {

    private TreeMap<String,Action> _this = new TreeMap<String, Action>();

    public boolean addAction(String id, Action action) {
        if (_this.containsKey(id)) {
            throw new InconsistentException("Found duplicated action ID: " + id);
        }
        if (!id.equals(action.getId())) {
            throw new InconsistentException(
                    "Index ID: " + id + ", Action ID: " + action.getId());
        }
        _this.put(id, action);
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

    // [MOD] on 10 Apr start
    public void removeAction(String id){
        if(!_this.containsKey(id)){
            throw new InconsistentException("No action found in the ActionSet for id: "+id);
        }
        _this.remove(id);
    }

    public Action getHighestRewardAction(){
        Action winner = null;
        double highestReward = -1;
        double currentReward;

        for (Action currentAction: this.actions()){
            currentReward = currentAction.getReward();
            if (currentReward>=highestReward){
                winner = currentAction;
                highestReward = currentReward;
            }
        }
        return winner;
    }
    // [MOD] on 10 Apr end

    public String toString() {
        List<String> strings = new LinkedList<String>();
        for (Action a : actions()){
            strings.add(a.toString());
        }
        return String.join("\n",strings);
    }
	
}

