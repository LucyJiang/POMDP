package model;

import common.AlphaVector;
import common.BeliefState;
import common.BeliefStateImp;
import common.ValueFunctionImp;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

public class BMDPImp implements BMDP {

    POMDP pomdp;
    RealMatrix tau[][];

    public BMDPImp(POMDP pomdp) {
        this.pomdp = pomdp;
        init();
    }

    private void init() {
        tau = new RealMatrix[numO()][numA()];
        for (int a = 0; a < numA(); a++) {
            RealMatrix tMat = this.TforA(a);
            RealMatrix oMat = this.ZforA(a);
            // oMat.transpose();
            // System.out.println(oMat.toString());
            for (int o = 0; o < numO(); o++) {
                RealMatrix oDiag = MatrixUtils.createRealMatrix(numS(), numS());
                for (int s = 0; s < numS(); s++) {
                    oDiag.setEntry(s, s, oMat.getEntry(s, o));
                }
                tau[o][a] = tMat.multiply(oDiag);
            }
        }
    }


    public BeliefState nextBeliefState(BeliefState b, int a, int o) {
        Vector vect = new Vector(tau[o][a].operate(b.getPoint()));
        vect.scale(1.0 / vect.getL1Norm());
        return (new BeliefStateImp(vect));
    }

    @Override
    public double expectedImmediateReward(BeliefState b, int a) {
        return pomdp.expectedImmediateReward(b, a);
    }

    @Override
    public Vector observationProbabilities(BeliefState bel, int a) {
        return pomdp.observationProbabilities(bel, a);
    }

    @Override
    public RealMatrix TforA(int a) {
        return pomdp.TforA(a);
    }

    @Override
    public RealMatrix ZforA(int a) {
        return pomdp.ZforA(a);
    }

    @Override
    public Vector RforA(int a) {
        return pomdp.RforA(a);
    }

    @Override
    public BeliefState getInitBeliefState() {
        return pomdp.getInitBeliefState();
    }

    @Override
    public int numS() {
        return pomdp.numS();
    }

    @Override
    public int numA() {
        return pomdp.numA();
    }

    @Override
    public int numO() {
        return pomdp.numO();
    }

    @Override
    public double gamma() {
        return pomdp.gamma();
    }

    @Override
    public String actionName(int a) {
        return pomdp.actionName(a);
    }

    @Override
    public String observationName(int o) {
        return pomdp.observationName(o);
    }

    @Override
    public String stateName(int s) {
        return pomdp.stateName(s);
    }

    @Override
    public int getRandomAction() {
        return pomdp.getRandomAction();
    }

    public double getRewardMax() {
        return (pomdp.getRewardMax());
    }

    public double getRewardMin() {
        return (pomdp.getRewardMin());
    }

    public double getRewardMaxMin() {
        return (pomdp.getRewardMaxMin());
    }

    public int getRandomObservation(BeliefStateImp bel, int a) {
        return pomdp.getRandomObservation(bel, a);
    }

    public ValueFunctionImp getRewardValueFunction(int a) {
        return pomdp.getRewardValueFunction(a);
    }

    public POMDP getPomdp() {
        return pomdp;
    }


    public RealMatrix getTau(int a, int o) {
        return (tau[o][a].copy());
    }


    public AlphaVector projection(AlphaVector alpha, int a, int o) {
        Vector vect = new Vector(numS());
        vect = new Vector(vect.add(tau[o][a].scalarMultiply(gamma())
                                          .operate(alpha.getVectorRef())));
        return (new AlphaVector(vect, a));
    }

}
