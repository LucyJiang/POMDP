/** ------------------------------------------------------------------------- *
 * libpomdp
 * ========
 * File: mdp.java
 * Description: offline upper and lower bounds based on the underlying
 *              fully observable MDP (Vmdp, Qmdp, and Blind)
 *              have a look at the README references [6,2]
 *              do not try on large problems as this will run out of mem
 * Copyright (c) 2009, 2010 Diego Maniloff 
 * W3: http://www.cs.uic.edu/~dmanilof
 --------------------------------------------------------------------------- */

package solver.bounds;

// imports

import common.AlphaVector;
import model.POMDPImp;
import common.ValueFunctionImp;
import solver.IterationStats;
import solver.vi.ValueIterationStd;

public class QmdpStd extends ValueIterationStd {
    
	public AlphaVector Vt;
	
	public QmdpStd(POMDPImp pomdp){
		startTimer();
		initValueIteration(pomdp);
		current=new ValueFunctionImp(pomdp.numS());
		for(int a=0; a<pomdp.numA(); a++)
		    current.push(new AlphaVector(pomdp.numS(),a));
		Vt=new AlphaVector(pomdp.numS());
		registerInitTime();
	}
	
	@Override
	public IterationStats iterate() {
		startTimer();
		old=current.copy();
		current=new ValueFunctionImp(pomdp.numS());
		for(int a=0; a<pomdp.numA(); a++){
			AlphaVector res=pomdp.mdpValueUpdate(Vt, a);
    	    current.push(res);
    	}
		for (int s=0;s<pomdp.numS();s++){
			double colmax=Double.NEGATIVE_INFINITY;
			for(int a=0; a<pomdp.numA(); a++){
				double val=current.getAlphaElement(a,s);
				if (val > colmax)
					colmax=val;
			}
			Vt.setValue(s, colmax);
		}
		registerValueIterationStats();
    	return iterationStats;
	}
} // qmdpFlat