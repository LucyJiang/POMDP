package solver.iteration;

import model.POMDP;
import common.ValueFunctionImp;

public abstract class ValueIterationSolver extends IterationSolver {

    protected POMDP pomdp;
    protected ValueFunctionImp current;
    protected ValueFunctionImp old;

    public POMDP getPOMDP() {
        return pomdp;
    }

    public ValueFunctionImp getValueFunction() {
        return current;
    }

    public ValueFunctionImp getOldValueFunction() {
        return old;
    }

    public void recordVectorCount(){
        if (current != null) {
            ValueIterationTimer vit = (ValueIterationTimer) getTimer();
            vit.recordVectorCount(current.size());
        }
    }

    @Override
    public Timer getTimer() {
        if(this.timer==null){
            this.timer= new ValueIterationTimer();
        }
        return this.timer;
    }
}
