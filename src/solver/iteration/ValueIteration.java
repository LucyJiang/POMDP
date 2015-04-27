package solver.iteration;

import common.ValueFunction;

public abstract class ValueIteration extends Iteration {
	
	public Timer getTimer() {
		return(timer);
	}
	
	public abstract void registerValueIterationStats();
		
	public abstract ValueFunction getValueFunction();
	public abstract ValueFunction getOldValueFunction();
}
