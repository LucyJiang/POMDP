package solver.iteration;

import solver.Solver;
import solver.criteria.Criteria;

import java.util.ArrayList;

public abstract class Iteration implements Solver {

    protected Timer timer;
    protected ArrayList<Criteria> criterias;
    protected long startTime;

    @Override
    public void run() {
        while (!stop()) {
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

    private boolean stop() {
        for (Criteria c : criterias) {
            if (c.check(this))
                return true;
        }
        return false;
    }

    protected abstract Timer iterate();

    protected void startTimer() {
        startTime = System.currentTimeMillis();
    }

    protected void initIteration() {
        criterias = new ArrayList<Criteria>();
    }

    protected void writeInitTime() {
        timer.writeInitTime(System.currentTimeMillis() - startTime);
    }

    protected void writeIterTime() {
        timer.writeTimeRecords(System.currentTimeMillis() - startTime);
    }

}