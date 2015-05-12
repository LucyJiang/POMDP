package solver.iteration;

import solver.Solver;
import solver.criteria.Criteria;
import solver.util.Timer;

import java.util.ArrayList;

/**
 * Iteration Solver
 */
public abstract class IterationSolver implements Solver {

    protected Timer timer;
    protected ArrayList<Criteria> criterias = new ArrayList<Criteria>();

    @Override
    public void run() {
        System.out.print(
                "*START:");
        while (!checkStop()) {
            System.out.print(
                    "*Iter[" + timer.getIterNumber() + "]:");
            iterate();
        }
        System.out.print("\n");
    }

    @Override
    public void addCriteria(Criteria c) {
        if (c.valid(this))
            criterias.add(c);
    }

    /**
     * check all criterias
     * @return
     */
    private boolean checkStop() {
        for (Criteria c : criterias) {
            if (c.check(this))
                return true;
        }
        return false;
    }

    protected abstract void iterate();


}