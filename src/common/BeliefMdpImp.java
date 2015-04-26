package common;

import model.POMDP;
import model.POMDPImp;
import model.Vector;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

import java.io.Serializable;

public class BeliefMdpImp implements BeliefMdp {

    POMDPImp pom;
    RealMatrix tau[][];

    public BeliefMdpImp(POMDPImp pom) {
        this.pom = pom;
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
        return pom.expectedImmediateReward(b, a);
    }

    @Override
    public Vector observationProbabilities(BeliefState bel, int a) {
        return pom.observationProbabilities(bel, a);
    }

    @Override
    public RealMatrix TforA(int a) {
        return pom.TforA(a);
    }

    @Override
    public RealMatrix ZforA(int a) {
        return pom.ZforA(a);
    }

    @Override
    public Vector RforA(int a) {
        return pom.RforA(a);
    }

    @Override
    public BeliefState getInitBeliefState() {
        return pom.getInitBeliefState();
    }

    @Override
    public int numS() {
        return pom.numS();
    }

    @Override
    public int numA() {
        return pom.numA();
    }

    @Override
    public int numO() {
        return pom.numO();
    }

    @Override
    public double gamma() {
        return pom.gamma();
    }

    @Override
    public String actionName(int a) {
        return pom.actionName(a);
    }

    @Override
    public String observationName(int o) {
        return pom.observationName(o);
    }

    @Override
    public String stateName(int s) {
        return pom.stateName(s);
    }

    public double getRewardMax() {
        return (pom.getRewardMax());
    }

    public double getRewardMin() {
        return (pom.getRewardMin());
    }

    public double getRewardMaxMin() {
        return (pom.getRewardMaxMin());
    }

    public int getRandomObservation(BeliefStateImp bel, int a) {
        return pom.getRandomObservation(bel, a);
    }

    public ValueFunctionImp getRewardValueFunction(int a) {
        return pom.getRewardValueFunction(a);
    }

    public POMDP getPomdp() {
        return pom;
    }


    public RealMatrix getTau(int a, int o) {
        System.out.println("!"+a+"  "+o);
        return (tau[o][a].copy());
    }


    public AlphaVector projection(AlphaVector alpha, int a, int o) {
        Vector vect = new Vector(numS());
        vect = new Vector(vect.add(tau[o][a].scalarMultiply(gamma())
                                          .operate(alpha.getVectorRef())));
        return (new AlphaVector(vect, a));
    }

}
