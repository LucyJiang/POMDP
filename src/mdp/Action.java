/**
 * 
 */
package mdp;

import java.util.TreeMap;

/**
 * @author LucyJiang
 *
 */
public class Action {
	private TreeMap<State, Float> outStates;
	private float discountFactor;
	
	public Action(TreeMap<State, Float> outStates, float df){
		this.outStates = outStates;
		this.discountFactor = df;
	}
	
	public float getRewardForState(State s){
		if (outStates.containsKey(s)) {
			return s.getReward();
		} else {
			throw new NoFoundStateException(
					"Cannot find target State in specific Action");
		}
	}
	
	public void changeProForState(State s, float pro){
		if (outStates.containsKey(s)) {
			outStates.replace(s, pro);
		} else {
			throw new NoFoundStateException(
					"Cannot find target State in specific Action");
		}
	}
	
	public float getProbForState(State s){
		if (outStates.containsKey(s)) {
			return outStates.get(s);
		} else {
			throw new NoFoundStateException(
					"Cannot find target State in specific Action");
		}
	}
	
	public TreeMap<State, Float> getOutStates(){
		return this.outStates;
	}
	
	public float getDiscountFactor(){
		return this.discountFactor;
	}
}