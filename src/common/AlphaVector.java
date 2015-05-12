/** ------------------------------------------------------------------------- *
 * libpomdp
 --------------------------------------------------------------------------- */

package common;

import model.Vector;

public class AlphaVector implements Comparable<AlphaVector> {

    protected Vector v;
    protected int a;


    public AlphaVector(Vector v, int a) {
        this.v = v;
        this.a = a;
    }

    public AlphaVector(int dim) {
        this(new Vector(dim), -1);
    }


    public AlphaVector(int dim, int a) {
        this(new Vector(dim), a);
    }


    public double eval(BeliefState bel) {
        return (v.dotProduct(bel.getPoint()));
    }


    public int getAction() {
        return a;
    }

    public void setAction(int a) {
        this.a = a;
    }

    public AlphaVector copy() {
        return (new AlphaVector(v.copy(), a));
    }


    public Vector getVectorCopy() {
        return (v.copy());
    }


    public int size() {
        return (v.getDimension());
    }


    public int compareTo(AlphaVector vec, double delta) {
        return (v.compareTo(vec.v));
    }

    public Vector getVectorRef() {
        return (v);
    }


    public int compareTo(AlphaVector vec) {
        return (v.compareTo(vec.v));
    }


    public void setValues(Vector v) {
        this.v = v;
    }


    public void set(AlphaVector res) {
        setValues(res.v.copy());
        setAction(res.a);
    }


    public void setValue(int idx, double value) {
        v.setEntry(idx, value);
    }


    public void add(AlphaVector alpha) {
        add(alpha.v);
    }


    public void add(Vector vec) {
        v = new Vector(v.add(vec));
    }
}
