package solver;

import solver.criteria.Criteria;
import solver.util.Timer;


public interface Solver {
    public void run();

    public void addCriteria(Criteria c);

    public Timer getTimer();
}
