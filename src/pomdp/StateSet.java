package pomdp;

import exception.InconsistentException;

import java.util.*;

public class StateSet {

    private TreeMap<String, State> _this = new TreeMap<String, State>();

    public State getState(String id) {
        return _this.get(id);
    }

    public boolean addState(String id, State state) {
        if (_this.containsKey(id)) {
            throw new InconsistentException("Found duplicated state ID: " + id);
        }
        if (!id.equals(state.getId())) {
            throw new InconsistentException(
                    "Index ID: " + id + " State ID: " + state.getId());
        }
        _this.put(id, state);
        return true;
    }

    public boolean contains(String id) {
        return _this.containsKey(id);
    }

    public Collection<State> states() {
        return _this.values();
    }

    public Set<String> ids() {
        return _this.keySet();
    }

    public Set<Map.Entry<String, State>> pairs() {
        return _this.entrySet();
    }

    public String toString() {
        List<String> strings = new LinkedList<String>();
        for (State s : states()){
            strings.add(s.toString());
        }
        return String.join("\n", strings);
    }
}
