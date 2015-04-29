package solver.criteria;

import solver.iteration.IterationSolver;

public abstract class Criteria {
    public abstract boolean check(IterationSolver i);

    public abstract boolean valid(IterationSolver i);
}
