package pomdp;

import exception.InconsistantException;

import java.util.TreeMap;
import java.util.TreeSet;

public class StateSet extends TreeMap<String, State> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public State getState(String id) {
        return this.get(id);
    }

    public boolean addState(String id, State state) {
        if (this.containsKey(id)) {
            throw new InconsistantException("Found duplicated state ID: " + id);
        }
        if (!id.equals(state.getId())) {
            throw new InconsistantException(
                    "Index ID: " + id + " State ID: " + state.getId());
        }
        this.put(id, state);
        return true;
    }

    @Override
    public String toString() {
        return this.keySet().toString();
    }
}
