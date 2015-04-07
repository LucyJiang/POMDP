package pomdp;

import exception.InconsistentException;

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

    public String toString() {
        List<String> strings = new LinkedList<String>();
        for (Action a : actions()){
            strings.add(a.toString());
        }
        return String.join("\n",strings);
    }
	
}

