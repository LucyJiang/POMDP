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

    public boolean isConsistant(){
        if (model==null || initialStateID==null){
            return false;
        }
        if (!model.getStateSet().containsKey(initialStateID)){
            return false;
        }
        return true;
    }
}