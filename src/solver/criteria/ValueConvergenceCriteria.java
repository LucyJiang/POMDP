package solver.criteria;

import common.AlphaVector;
import common.ValueFunction;
import common.ValueFunctionImp;
import model.Vector;
import solver.iteration.IterationSolver;
import solver.iteration.ValueIterationSolver;

public class ValueConvergenceCriteria extends Criteria {

    static final int MIN_ITERATIONS = 5;
    double epsilon;

    public ValueConvergenceCriteria(double epsilon) {
        this.epsilon = epsilon;
    }

    public boolean check(IterationSolver i) {
        ValueIterationSolver vi = (ValueIterationSolver) i;
        ValueFunction newv = vi.getValueFunction();
        ValueFunction oldv = vi.getOldValueFunction();
        if (oldv == null || newv.size() != oldv.size()) {
            System.out.println("eval = Infinity");
            return false;
        }
        ((ValueFunctionImp) newv).sort();
        ((ValueFunctionImp) oldv).sort();
        double conv = 0;
        for (int j = 0; j < newv.size(); j++) {
            AlphaVector newAlpha = newv.getAlphaVector(j);
            AlphaVector oldAlpha = oldv.getAlphaVector(j);
            if (newAlpha.getAction() != oldAlpha.getAction()) {
                System.out.println("eval = Infinity");
                return false;
            }
            Vector perf = new Vector(newAlpha.getVectorCopy().mapMultiply(-1.0)
                                             .add(oldAlpha.getVectorRef()));
            double a_value = perf.getL1Norm();

            if (a_value > conv)
                conv = a_value;
        }
        System.out.println("eval = " + conv);
        if (conv <= epsilon && i.getTimer().getIterNumber() > MIN_ITERATIONS)
            return (true);
        return false;
    }

    @Override
    public boolean valid(IterationSolver vi) {
        if (vi instanceof ValueIterationSolver) {
            return true;
        }
        return false;
    }


}
