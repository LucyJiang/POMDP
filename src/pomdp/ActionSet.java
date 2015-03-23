package pomdp;

import exception.InconsistantException;

import java.util.*;

public class ActionSet {

    private TreeMap<String,Action> _this = new TreeMap<String, Action>();

    public boolean addAction(String id, Action action) {
        if (_this.containsKey(id)) {
            throw new InconsistantException("Found duplicated action ID: " + id);
        }
        if (!id.equals(action.getId())) {
            throw new InconsistantException(
                    "Index ID: " + id + " Action ID: " + action.getId());
        }
        _this.put(id, action);
        return true;
    }

    public boolean contains(String id) {
        return _this.containsKey(id);
    }

    public Collection<Action> actions() {
        return _this.values();
    }

    public Set<String> ids() {
        return _this.keySet();
    }

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

