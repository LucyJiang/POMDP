/** BeliefMdp class. 
This interface represents a Belief MDP. 
At this point, this assumes that a belief MDP
always comes from a POMDP, but this might change in
the future if a MDP interface is defined.
@author Mauricio Araya
*/

package model;

import common.AlphaVector;
import org.apache.commons.math3.linear.RealMatrix;

public interface BMDP extends POMDP {
    
    /** Obtain the POMDP for which the belief MDP was calculated. 
	@return the POMDP reference
    */
    public POMDP getPomdp();
    /** Get the tau matrix. The tau matrix is the matrix that
	represents the projection over the next value, given an action
	and an observation. Formally is the matrix Matrix diag(O^{o,a})*T^{o,a}.
	@param a the action
	@param o the observation*/
    public RealMatrix getTau(int a,int o);

    public AlphaVector projection(AlphaVector alpha, int a, int o);
}
