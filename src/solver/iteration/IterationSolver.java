package solver.iteration;

import solver.Solver;
import solver.criteria.Criteria;

import java.util.ArrayList;

public abstract class IterationSolver implements Solver {

    protected Timer timer;
    protected ArrayList<Criteria> criterias = new ArrayList<Criteria>();

    @Override
    public void run() {
        while (!checkStop()) {
            System.out.println(
                    "== Iteration " + timer.getIterNumber() + " ==");
            iterate();
        }
    }

    @Override
    public void addCriteria(Criteria c) {
        if (c.valid(this))
            criterias.add(c);
    }

    private boolean checkStop() {
        for (Criteria c : criterias) {
            if (c.check(this))
                return true;
        }
        return false;
    }

    protected abstract void iterate();



}