package solver.criteria;

import solver.iteration.IterationSolver;

/**
 * Criteria for iteration stop
 */
public abstract class Criteria {
    /**
     * check is this criteria satisfy
     * @param i
     * @return
     */
    public abstract boolean check(IterationSolver i);

    /**
     * check is this criteria valid
     * @param i
     * @return
     */
    public abstract boolean valid(IterationSolver i);
}
