/**
 *
 */
package pomdp;

import java.util.Collection;
import java.util.Set;
import java.util.TreeMap;

/**
 *
 */
public class Action {
    private String id;
    private State in;
    private TreeMap<String, ActionOutTriple> outs;

    public Action(
            String id,
            State inState) {

        this.id = id;
        this.in = inState;
        this.outs = new TreeMap<String, ActionOutTriple>();
    }

    public void addOutState(State s, double probability, double reward){
        this.outs.put(s.getId(),new ActionOutTriple(s,probability,reward));
    }


    public String getId() {
        return id;
    }

    public State getInState() {
        return in;
    }

    public Collection<ActionOutTriple> getOutTriples() {
        return outs.values();
    }


    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[@] "+id+"\n");
        for (ActionOutTriple aot: getOutTriples()){
            sb.append(aot);
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Action)) return false;

        Action action = (Action) o;

        if (id != null ? !id.equals(action.id) : action.id != null)
            return false;
        if (in != null ? !in.equals(action.in) : action.in != null)
            return false;
        if (outs != null ? !outs.equals(action.outs) : action.outs != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (in != null ? in.hashCode() : 0);
        result = 31 * result + (outs != null ? outs.hashCode() : 0);
        return result;
    }
}