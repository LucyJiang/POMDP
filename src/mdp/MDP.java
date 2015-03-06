package mdp;

public class MDP {
	
	private StateSet stateSet;
	private State currentState;
	
	public MDP(StateSet st){
		this.stateSet = st;
	}
	
	public static MDP generateRandomly(/*constraints*/){
		
		return null;
	}
	
	public static MDP importFromFile(/*FileName*/){
		return null;
	}
	
	public static MDP phraseString(String str){
		return null;
	}
	
	public StateSet getStateSet(){
		return this.stateSet;
	}
}
