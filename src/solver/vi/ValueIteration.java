package solver.vi;

import common.ValueFunction;
import solver.Iteration;
import solver.IterationStats;

public abstract class ValueIteration extends Iteration {
	
	public IterationStats getStats() {
		return(iterationStats);
	}
	
	public abstract void registerValueIterationStats();
		
	public abstract ValueFunction getValueFunction();
	public abstract ValueFunction getOldValueFunction();
}
