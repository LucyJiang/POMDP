/** ------------------------------------------------------------------------- *
 * libpomdp
 * ========
 * File: belStateSparseMTJ.java
 * Description: 
 * Copyright (c) 2009, 2010 Diego Maniloff 
 * Copyright (c) 2010 Mauricio Araya  
 --------------------------------------------------------------------------- */

package common;

// imports

import model.Vector;

import java.io.Serializable;

public class BeliefStateImp implements BeliefState {

    // sparse representation of the belief
    private Vector belief;

    // associated P(o|b,a)
    private double poba = -1.0;

    // associated alpha vector id
    private int planid = -1;

    // constructor
    // in case this is the initial belief, poba = 0.0
    public BeliefStateImp(Vector belief, double poba) {
        this.belief = belief;
        this.poba = poba;
    }

    public BeliefStateImp(Vector belief) {
        this(belief, -1);
    }
    // calling this method should be for debugging
    // purposes only, otherwise we loose the sparse rep

    public Vector getPoint() {
        return belief;
    }


    public BeliefState copy() {
        return (new BeliefStateImp(belief, poba));
    }


    public static BeliefStateImp generateRandom(int size) {
        Vector v = new Vector(size);
        for (int i = 0; i < size; i++) {
            v.setEntry(i, Utils.random.nextDouble());
        }
        v.scale(v.getL1Norm());
        return new BeliefStateImp(v);
    }


    public boolean compare(BeliefState arg0) {
        return (belief.equals(arg0.getPoint()));
    }


} // BeliefStateStandard
