package algorithm;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import pomdp.POMDP;

/**
*
*/
public class Configuration {
    private POMDP model;
    private String initialState;

    public Configuration(POMDP model, String initialState) {
        this.model = model;
        this.initialState = initialState;
    }

    public POMDP getModel() {
        return model;
    }

    public String getInitialStateID() {
        return initialState;
    }

    /**
     * Check whether the configuration is consistent.
     * i.e. model!=null, initialStateID!=null and initialStateID
     * is included in the StateSet of the model
     *
     * @return @code{true} iff configuration is consistent
     */
    public boolean isConsistent(){
        if (model==null || initialState==null){
            return false;
        }
        if (!model.getS().contains(this.initialState)){
            return false;
        }
        return true;
    }
}
