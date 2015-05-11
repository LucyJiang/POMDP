package common;

import model.Vector;
import util.Utils;

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

    public static BeliefStateImp generateRandom(int size) {
        Vector v = new Vector(size);
        for (int i = 0; i < size; i++) {
            v.setEntry(i, Utils.random.nextDouble());
        }
        v.scale(v.getL1Norm());
        return new BeliefStateImp(v);
    }

    public Vector getPoint() {
        return belief;
    }

    public BeliefState copy() {
        return (new BeliefStateImp(belief, poba));
    }

    public boolean compare(BeliefState arg0) {
        return (belief.equals(arg0.getPoint()));
    }


} // BeliefStateStandard
