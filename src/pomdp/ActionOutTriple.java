package pomdp;

/**
 * Created by LeoDong on 21/04/2015.
 */
public class ActionOutTriple {
    private State state;
    private double probability;
    private double reward;

    public ActionOutTriple(State s, double p, double r) {
        state = s;
        probability = p;
        reward = r;
    }

    public State getState() {
        return state;
    }

    public double getProbability() {
        return probability;
    }

    public double getReward() {
        return reward;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActionOutTriple)) return false;

        ActionOutTriple that = (ActionOutTriple) o;

        if (Double.compare(that.probability, probability) != 0) return false;
        if (Double.compare(that.reward, reward) != 0) return false;
        if (state != null ? !state.equals(that.state) : that.state != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = state != null ? state.hashCode() : 0;
        temp = Double.doubleToLongBits(probability);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(reward);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer(
                "{ ");
        sb.append("state=").append(state.getId());
        sb.append(", probability=").append(probability);
        sb.append(", reward=").append(reward);
        sb.append('}');
        return sb.toString();
    }
}
