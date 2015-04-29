package solver;

import solver.criteria.Criteria;
import solver.util.Timer;

/**
 * Created by LeoDong on 27/04/2015.
 */
public interface Solver {
    public void run();

    public void addCriteria(Criteria c);

    public Timer getTimer();
}
