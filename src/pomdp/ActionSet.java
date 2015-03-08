package pomdp;

import exception.InconsistantException;

import java.util.TreeMap;

public class ActionSet extends TreeMap<String,Action> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    public boolean addAction(String id, Action action) {
        if (this.containsKey(id)) {
            throw new InconsistantException("Found duplicated action ID: " + id);
        }
        if (!id.equals(action.getId())) {
            throw new InconsistantException(
                    "Index ID: " + id + " Action ID: " + action.getId());
        }
        this.put(id, action);
        return true;
    }

    @Override
    public String toString() {
        return this.keySet().toString();
    }
	
}

