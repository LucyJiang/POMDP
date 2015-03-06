package mdp;

public class State {
	private ActionSet actionSet;
	private float reward;
	
	public State(float reward, ActionSet as){
		this.reward = reward;
		this.actionSet = as;
	}
	

	
	public void decreaseByFactor(float factor){
		if(factor>=1||factor<=0){
			throw new IllegalArgumentException("factor should be in (0,1)");
		}else{
			this.reward = this.reward*factor;
		}
	}
	
	public float transition(int indexOfAction, State s){
		return this.actionSet.get(indexOfAction).getProbForState(s);
	}
	
	public float getRewardFunction(int indexOfAction, State s){
		Action action =  this.actionSet.get(indexOfAction);
		float factor =action.getDiscountFactor();
		return factor*action.getRewardForState(s);
	}
	
	public float getReward(){
		return this.reward;
	}
	
	public ActionSet getActionSet(){
		return this.actionSet;
	}
	public StateSet getNextStates(){
		StateSet ss = new StateSet();
		for(Action a:actionSet){
			ss.addAll(a.getOutStates().keySet());
		}
		return ss;
	}
}
