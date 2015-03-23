package algorithm;

import pomdp.POMDP;

/**
 * Created by LeoDong on 12/03/2015.
 */
public class Configuration {
    private POMDP model;
    private String initialStateID;

    public Configuration(POMDP model, String initialStateID) {
        this.model = model;
        this.initialStateID = initialStateID;
    }

    public POMDP getModel() {
        return model;
    }

    public String getInitialStateID() {
        return initialStateID;
    }

    /**
     * Check whether the configuration is consistent.
     * i.e. model!=null, initialStateID!=null and initialStateID
     * is included in the StateSet of the model
     *
     * @return @code{true} iff configuration is consistent
     */
    public boolean isConsistent(){
        if (model==null || initialStateID==null){
            return false;
        }
        if (!model.getStateSet().contains(initialStateID)){
            return false;
        }
        return true;
    }
}
