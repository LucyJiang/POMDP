package solver.criteria;

import solver.iteration.IterationSolver;

public class MaxIterationsCriteria extends Criteria {

    int max_iter;

    public MaxIterationsCriteria(int maxIter) {
        this.max_iter = maxIter;
    }

    @Override
    public boolean check(IterationSolver i) {
        if (i.getTimer().getIterNumber() < max_iter)
            return false;
        return true;
    }

    @Override
    public boolean valid(IterationSolver vi) {
        return true;
    }

}
