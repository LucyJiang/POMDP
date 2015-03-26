/**
 *
 */
package pomdp;

/**
 *
 */
public class Action {
    private String id;
    private State fromState;
    private State toState;
    private double reward;

    public Action(
            String id,
            State fromState,
            State toState,
            double reward) {

        this.fromState = fromState;
        this.toState = toState;
        this.reward = reward;
        this.id = id;

        this.fromState.addAction(this);
        this.toState.addFromState(this.fromState);
        this.fromState.addToState(this.toState);
    }


    public String getId() {
        return id;
    }

    public State getFromState() {
        return fromState;
    }

    public State getToState() {
        return toState;
    }

    public double getReward() {
        return reward;
    }

    @Override
    public String toString() {
        return "[@] "+id+"\t: " + fromState.getId() +
               "\t==>\t" + toState.getId() +
               "\t[reward = " + reward + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Action)) return false;

        Action action = (Action) o;

        if (Double.compare(action.reward, reward) != 0) return false;
        if (fromState != null ? !fromState.equals(action.fromState)
                              : action.fromState != null) return false;
        if (id != null ? !id.equals(action.id) : action.id != null)
            return false;
        if (toState != null ? !toState.equals(action.toState)
                            : action.toState != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id != null ? id.hashCode() : 0;
        result = 31 * result + (fromState != null ? fromState.hashCode() : 0);
        result = 31 * result + (toState != null ? toState.hashCode() : 0);
        temp = Double.doubleToLongBits(reward);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}