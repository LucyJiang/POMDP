package model;

import org.apache.commons.math3.exception.NullArgumentException;
import org.apache.commons.math3.exception.NumberIsTooLargeException;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

/**
 * Created by LeoDong on 26/04/2015.
 */
public class Vector extends ArrayRealVector implements Comparable<Vector> {
    public Vector() {
        super();
    }

    public Vector(int size) {
        super(size);
    }

    public Vector(int size, double preset) {
        super(size, preset);
    }

    public Vector(double[] d) {
        super(d);
    }

    public Vector(double[] d, boolean copyArray) throws NullArgumentException {
        super(d, copyArray);
    }

    public Vector(double[] d, int pos, int size)
            throws NullArgumentException, NumberIsTooLargeException {
        super(d, pos, size);
    }

    public Vector(Double[] d) {
        super(d);
    }

    public Vector(Double[] d, int pos, int size)
            throws NullArgumentException, NumberIsTooLargeException {
        super(d, pos, size);
    }

    public Vector(RealVector v) throws NullArgumentException {
        super(v);
    }

    public Vector(ArrayRealVector v) throws NullArgumentException {
        super(v);
    }

    public Vector(ArrayRealVector v, boolean deep) {
        super(v, deep);
    }

    public Vector(
            ArrayRealVector v1, ArrayRealVector v2) {
        super(v1, v2);
    }

    public Vector(
            ArrayRealVector v1, RealVector v2) {
        super(v1, v2);
    }

    public Vector(
            RealVector v1, ArrayRealVector v2) {
        super(v1, v2);
    }

    public Vector(ArrayRealVector v1, double[] v2) {
        super(v1, v2);
    }

    public Vector(
            double[] v1, ArrayRealVector v2) {
        super(v1, v2);
    }

    public Vector(double[] v1, double[] v2) {
        super(v1, v2);
    }


    public void scale(double d) {
        for (int i = 0; i < this.getDimension(); i++) {
            this.setEntry(i, this.getEntry(i) * d);
        }
    }

    public int compareTo(Vector vprime, double delta) {
        for (int i = 0; i < this.getDimension(); i++) {
            if (this.getEntry(i) > vprime.getEntry(i) + delta)
                return 1;
            if (this.getEntry(i) < vprime.getEntry(i) - delta)
                return -1;
        }
        return 0;
    }

    @Override
    public Vector copy() {
        return new Vector(super.copy());
    }

    public int compareTo(Vector arg0) {
        return compareTo(arg0, 0.0);
    }
}
