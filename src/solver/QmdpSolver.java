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

package solver;

// imports

import common.AlphaVector;
import model.POMDP;
import common.ValueFunctionImp;
import model.Vector;
import solver.iteration.ValueIterationSolver;

public class QmdpSolver extends ValueIterationSolver {
    
	public AlphaVector Vt;
	
	public QmdpSolver(POMDP pomdp){
        this.getTimer().start();
        this.pomdp = pomdp;
		current=new ValueFunctionImp(pomdp.numS());
		for(int a=0; a<pomdp.numA(); a++)
		    current.push(new AlphaVector(pomdp.numS(),a));
		Vt=new AlphaVector(pomdp.numS());
        this.getTimer().recordInitTime();
	}


    @Override
	public void iterate() {
        this.getTimer().start();
		old=current.copy();
		current=new ValueFunctionImp(pomdp.numS());
		for(int a=0; a<pomdp.numA(); a++){
            //mdpValueUpdate
            Vector vec = new Vector(pomdp.TforA(a).scalarMultiply(pomdp.gamma()).operate(Vt.getVectorRef()));
            vec = new Vector(vec.add(pomdp.getRewardValueFunction(a).getAlphaVector(0).getVectorRef()));
            AlphaVector res=new AlphaVector(vec, a);

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
        recordVectorCount();
        this.getTimer().recordIterTime();
	}
} // qmdpFlat