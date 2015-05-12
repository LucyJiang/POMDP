/** ------------------------------------------------------------------------- *
 * libpomdp
 --------------------------------------------------------------------------- */
package common;

import model.Vector;
import util.Utils;

public class BeliefStateImp implements BeliefState {

    /**
     * Belief Point (probability distribution)
     */
    private Vector belief;

    /**
     * associated P(o|b,a) for the belief
     * @return
     */

    private double poba = -1.0;


    /**
     * Constructor with a p(o|b,a)
     * @param belief
     * @param poba
     */
    public BeliefStateImp(Vector belief, double poba) {
        this.belief = belief;
        this.poba = poba;
    }

    /**
     * Constructor with a default p(o|b,a)
     * @param belief
     */
    public BeliefStateImp(Vector belief) {
        this(belief, -1);
    }


    /**
     * generate a random BeliefState as the initial BeliefState
     * @param size
     * @return
     */
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


}
