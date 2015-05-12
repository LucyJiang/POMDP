package solver;

import solver.criteria.Criteria;
import solver.util.Timer;

/**
 * General interface for solver
 */
public interface Solver {
    /**
     * run the solver
     */
    public void run();

    /**
     * add stop criteria to solver
     * @param c
     */
    public void addCriteria(Criteria c);

    /**
     * get the Timer in the solver
     * @return
     */
    public Timer getTimer();
}
