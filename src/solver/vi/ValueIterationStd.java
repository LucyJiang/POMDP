package solver.vi;

import model.POMDPImp;
import common.ValueFunctionImp;
import solver.IterationStats;

public abstract class ValueIterationStd extends ValueIteration {

	protected POMDPImp pomdp;
	protected ValueFunctionImp current;
	protected ValueFunctionImp old;

	protected void initValueIteration(POMDPImp pomdp)
	{
		this.pomdp=pomdp;
		initIteration();
		iterationStats=new ValueIterationStats(pomdp);
	}

	public POMDPImp getPomdp() {
		return pomdp;
	}

	public ValueFunctionImp getValueFunction() {
		return current;
	}

	public ValueFunctionImp getOldValueFunction() {
		return old;
	}

	public abstract IterationStats iterate();

	public void registerValueIterationStats(){
		if (current!=null){
			((ValueIterationStats)iterationStats).iteration_vector_count.add(new Integer(current.size()));
		}
	registerIterationTime();
	}
}
