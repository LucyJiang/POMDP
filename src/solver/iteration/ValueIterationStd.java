package solver.iteration;

import model.POMDP;
import common.ValueFunctionImp;

public abstract class ValueIterationStd extends ValueIteration {

    protected POMDP pomdp;
    protected ValueFunctionImp current;
    protected ValueFunctionImp old;

    protected void initValueIteration(POMDP pomdp) {
        this.pomdp = pomdp;
        initIteration();
        timer = new ValueIterationTimer();
    }

    public POMDP getPOMDP() {
        return pomdp;
    }

    public ValueFunctionImp getValueFunction() {
        return current;
    }

    public ValueFunctionImp getOldValueFunction() {
        return old;
    }

    public abstract Timer iterate();

    public void registerValueIterationStats() {
        if (current != null) {
            ((ValueIterationTimer) timer).iteration_vector_count
                    .add(new Integer(current.size()));
        }
        writeIterTime();
    }

}
