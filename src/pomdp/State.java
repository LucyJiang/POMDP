package pomdp;

public class State {
    private String id;
    private ActionSet actionSet;
    private double observation;

    private StateSet fromStates;
    private StateSet toStates;


    public State(String id, double observation) {
        this.id = id;
        this.actionSet = new ActionSet();
        this.fromStates = new StateSet();
        this.toStates = new StateSet();
        this.observation = observation;

    }

    public State addFromState(State s) {
        return fromStates.put(s.id, s);
    }

    public State addToState(State s) {
        return toStates.put(s.id, s);
    }

    public boolean addAction(Action action) {
        actionSet.put(action.getId(), action);
        return true;
    }

    public StateSet getToStates() {
        return toStates;
    }

    public StateSet getFromStates() {

        return fromStates;
    }

    public ActionSet getActionSet() {
        return actionSet;
    }

    public String getId() {
        return id;
    }

    public double getObservation() {
        return observation;
    }

    public void setObservation(double observation) {
        this.observation = observation;
    }

    @Override
    public String toString() {
        return "[!] " + id + "{" +
               " actionSet=" + actionSet +
               " fromStates=" + fromStates +
               " toStates=" + toStates +
               " [observation=" + observation +
               "]}";
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        State clone = (State) super.clone();
        clone.id = id;
        clone.actionSet = (ActionSet) actionSet.clone();
        clone.observation = observation;
        clone.fromStates = (StateSet) fromStates.clone();
        clone.toStates = (StateSet) toStates.clone();
        return clone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof State)) return false;

        State state = (State) o;

        if (Double.compare(state.observation, observation) != 0) return false;
        if (actionSet != null ? !actionSet.equals(state.actionSet)
                              : state.actionSet != null) return false;
        if (fromStates != null ? !fromStates.equals(state.fromStates)
                               : state.fromStates != null) return false;
        if (id != null ? !id.equals(state.id) : state.id != null) return false;
        if (toStates != null ? !toStates.equals(state.toStates)
                             : state.toStates != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = actionSet != null ? actionSet.hashCode() : 0;
        result = 31 * result + (fromStates != null ? fromStates.hashCode() : 0);
        result = 31 * result + (toStates != null ? toStates.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        temp = Double.doubleToLongBits(observation);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
