package pomdp;

public class  State {
    private String id;
    private ActionSet actionSet;
    private double observation;



    public State(String id, double observation) {
        this.id = id;
        this.actionSet = new ActionSet();
        this.observation = observation;

    }


    public boolean addAction(Action action) {
        actionSet.addAction(action.getId(), action);
        return true;
    }

    public StateSet getOutStates() {
        StateSet ss = new StateSet();
        for (Action a: this.actionSet.actions()){
            for (ActionOutTriple aot:a.getOutTriples()){
                ss.addState(aot.getState());
            }
        }
        return ss;
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
        return "[!]\t" + id + "\t:" +
               " actionSet=" + actionSet.ids() +
               " observation=" + observation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof State)) return false;

        State state = (State) o;

        if (Double.compare(state.observation, observation) != 0) return false;
        if (actionSet != null ? !actionSet.equals(state.actionSet)
                              : state.actionSet != null) return false;
        if (id != null ? !id.equals(state.id) : state.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = actionSet != null ? actionSet.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        temp = Double.doubleToLongBits(observation);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
