package algorithm;

import exception.IllegalParameterException;
import pomdp.POMDP;
import pomdp.State;

import java.util.Map;
import java.util.TreeMap;

/**
 *
 */
public class Belief {
    private TreeMap<String,Double> _this = new TreeMap<String,Double>();

    public void initBelief(POMDP model){
        for (Map.Entry<String, State> e : model.getStateSet().pairs()){
            _this.put(e.getKey(),e.getValue().getObservation());
        }
    }

    public double getBelief(String stateID) throws IllegalParameterException{
        if(!_this.containsKey(stateID)){
            throw new IllegalParameterException("No id for state: "+stateID);
        }
        return _this.get(stateID);
    }

    public void setBelief(String stateID,double belief) throws IllegalParameterException{
        if(belief>=1||belief<=0){
            throw new IllegalParameterException("belief should between 0 and 1");
        }
        _this.put(stateID, belief);
    }

    public void updateBelief(POMDPAlgorithm alg){
        //alg.updateBeliefForState(State s)
    }

    @Override
    public String toString() {
        return "Belief:" + _this.toString();
    }
}
