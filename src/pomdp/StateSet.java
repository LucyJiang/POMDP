package pomdp;

import exception.InconsistentException;

import java.util.*;

public class StateSet {

    private TreeMap<String, State> _this = new TreeMap<String, State>();

    public State getState(String id) {
        return _this.get(id);
    }

    public boolean addState(State state) {
        _this.put(state.getId(), state);
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
