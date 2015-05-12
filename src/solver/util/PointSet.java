package solver.util;

import common.BeliefState;
import model.Vector;
import util.Utils;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * PointSet, actually a wrapper of ArrayList<BeliefState>
 */
public class PointSet implements Iterable<BeliefState> {
    ArrayList<BeliefState> set;

    public PointSet() {
        set = new ArrayList<BeliefState>();
    }

    /**
     * add a new BeliefState into the PointSet
     * @param arg0
     * @return
     */
    public boolean add(BeliefState arg0) {
        if (!member(arg0))
            return set.add(arg0);
        return false;
    }

    public boolean member(BeliefState arg0) {
        for (BeliefState it : set) {
            //System.out.println(it);
            if (it.compare(arg0))
                return true;
        }
        return false;
    }

    public BeliefState get(int arg0) {
        return set.get(arg0);
    }

    public BeliefState remove(int arg0) {
        return set.remove(arg0);
    }

    public boolean remove(BeliefState arg0) {
        return set.remove(arg0);
    }

    public void addAll(PointSet adder) {
        for (BeliefState it : adder) {
            set.add(it);
        }
    }

    public int size() {
        return (set.size());
    }

    /**
     * get the iterator
     * @return
     */
    public Iterator<BeliefState> iterator() {
        return (set.iterator());
    }

    /**
     * get a BeliefState randomly
     * @return
     */
    public BeliefState getRandom() {
        return get(Utils.random.nextInt(Integer.MAX_VALUE) % size());
    }

    public PointSet copy() {
        PointSet ret = new PointSet();
        for (BeliefState bel : set) {
            ret.add(bel.copy());
        }
        return ret;
    }

    public String toString() {
        String ret = "Point Set\n";
        for (int i = 0; i < size(); i++) {
            ret += "p" + i + "\t[";
            Vector v = set.get(i).getPoint();
            for (int j = 0; j < v.getDimension(); j++) {
                ret += v.getEntry(j) + " ";
            }
            ret += "]\n";
        }
        return ret;
    }

}
