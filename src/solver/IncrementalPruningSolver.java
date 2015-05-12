package solver;

import common.AlphaVector;
import common.ValueFunctionImp;
import model.BMDP;
import model.BMDPImp;
import model.POMDP;
import solver.iteration.ValueIterationSolver;
import solver.iteration.ValueIterationTimer;

import java.util.ArrayList;

/**
 * Incremental Pruning Solver
 */
public class IncrementalPruningSolver extends ValueIterationSolver {

    /**
     * Belief MDP
     */
    BMDP bmdp;

    /**
     * parameter for IP (delta)
     */
    private double delta;

    public IncrementalPruningSolver(POMDP pomdp, double delta) {
        this.getTimer().start();
        this.pomdp = pomdp;
        this.delta = delta;
        // build BMDP
        bmdp = new BMDPImp(pomdp);
        current = new ValueFunctionImp(pomdp.numS());
        current.push(new AlphaVector(bmdp.numS()));
        this.getTimer().recordInitTime();
    }

    /**
     * value iteration
     */
    public void iterate() {
        this.getTimer().start();
        old = current;
        current = new ValueFunctionImp(bmdp.numS());
        for (int a = 0; a < bmdp.numA(); a++) {
            /* Perform Projections */
            ArrayList<ValueFunctionImp> psi = new ArrayList<ValueFunctionImp>();
            for (int o = 0; o < bmdp.numO(); o++) {
                ValueFunctionImp proj = new ValueFunctionImp(bmdp.numS());
                for (int idx = 0; idx < old.size(); idx++) {
                    AlphaVector alpha = old.getAlphaVector(idx);
                    AlphaVector res = bmdp.projection(alpha, a, o);
                    proj.push(res);
                }
                ((ValueIterationTimer) this.getTimer())
                        .registerLp(proj.prune(delta));
                psi.add(proj);
            }
            ValueFunctionImp rewFunc = bmdp.getRewardValueFunction(a);
            psi.add(rewFunc);

            /* Calculate Cross Sum */
            while (psi.size() > 1) {
                ValueFunctionImp vfA = psi.remove(0);
                ValueFunctionImp vfB = psi.remove(0);
                vfA.crossSum(vfB);
                ((ValueIterationTimer) this.getTimer())
                        .registerLp(vfA.prune(delta));
                psi.add(vfA);
            }
            ValueFunctionImp vfA = psi.remove(0);
            current.merge(vfA);
        }
        ((ValueIterationTimer) this.getTimer())
                .registerLp(current.prune(delta));
        recordVectorCount();
        this.getTimer().recordIterTime();
    }

}
