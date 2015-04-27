/** ------------------------------------------------------------------------- *
 * libpomdp
 * ========
 * File:
 * Description: Represent a POMDP model using a flat representation and
 *              sparse matrices and vectors. This class can be constructed
 *              from a pomdpSpecSparseMTJ object after parsing a .pomdp file.
 *              Sparse matrices by matrix-toolkits-java,
 *              every matrix will be CustomMatrix:
 *
 * S =
 *  (3,1)        1
 *  (2,2)        2
 *  (3,2)        3
 *  (4,3)        4
 *  (1,4)        5
 * A =
 *   0     0     0     5
 *   0     2     0     0
 *   1     3     0     0
 *   0     0     4     0
 * Copyright (c) 2009, 2010, 2011 Diego Maniloff
 * Copyright (c) 2010, 2011 Mauricio Araya
 --------------------------------------------------------------------------- */

package model;

// imports
import common.*;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class POMDPImp implements POMDP {

    // ------------------------------------------------------------------------
    // properties
    // ------------------------------------------------------------------------

    // number of states
    private List<String> S = new ArrayList<String>(); //NS
    private List<String> A = new ArrayList<String>(); //NA
    private List<String> O = new ArrayList<String>(); //NO

    // transition model: a x s x s'
    private HashMap<Integer,RealMatrix> T = new HashMap<Integer,RealMatrix>();
    // observation model: a x s' x o
    private HashMap<Integer,RealMatrix> Z = new HashMap<Integer,RealMatrix>();
    // reward model: a x s'
    private HashMap<Integer, Vector> R = new HashMap<Integer, Vector>();

    // discount factor
    private double gamma;
    // starting belief
    private BeliefStateImp initBelief;

    // ------------------------------------------------------------------------
    // methods
    // ------------------------------------------------------------------------

    /// tao(b,a,o)
    public BeliefState nextBeliefState(BeliefState b, int a, int o) {
        // long start = System.currentTimeMillis();
        // System.out.println("made it to tao");
        BeliefState bPrime;
        // compute T[a]' * b1
        Vector b1 = b.getPoint();
        Vector b2 = new Vector(TforA(a).transpose().operate(b1));
        // System.out.println("Elapsed in tao - T[a] * b1" +
        // (System.currentTimeMillis() - start));

        // element-wise product with Z[a](:,o)
        b2 = new Vector(b2.ebeMultiply(ZforA(a).getColumnVector(o)));
        // System.out.println("Elapsed in tao - Z[a] .* b2" +
        // (System.currentTimeMillis() - start));

        // compute P(o|b,a) - norm1 is the sum of the absolute values
        double poba = b2.getL1Norm();
        // make sure we can normalize
        if (poba < 0.00001) {
            // System.err.println("Zero prob observation - resetting to init");
            // this branch will have poba = 0.0
            bPrime = initBelief;
        } else {
            // safe to normalize now
            b2.scale(1.0 / poba);
            bPrime = new BeliefStateImp(b2, poba);
        }
        // System.out.println("Elapsed in tao" + (System.currentTimeMillis() -
        // start));
        // return
        return bPrime;
    }

    /// R(b,a)
    public double expectedImmediateReward(BeliefState bel, int a) {
        Vector b = ((BeliefStateImp) bel).belief;
        return b.dotProduct(RforA(a));
    }

    // P(o|b,a) in vector form for all o's

    public Vector observationProbabilities(BeliefState b, int a) {
        Vector b1 = b.getPoint();
        Vector Tb = new Vector(TforA(a).operate(b1));
        Vector Poba = new Vector(ZforA(a).transpose().operate(Tb));
        return Poba;
    }

    //done
    public RealMatrix TforA(int a) {
        return T.get(a).copy();
    }

    //done
    public RealMatrix ZforA(int a) {
        return Z.get(a).copy();
    }

    //done
    public Vector RforA(int a) {
        return R.get(a).copy();
    }

    //done
    public BeliefState getInitBeliefState() {
        return initBelief.copy();
    }

    //done
    public int numS() {
        return S.size();
    }

    //done
    public int numA() {
        return A.size();
    }

    //done
    public int numO() {
        return O.size();
    }

    //done
    public double gamma() {
        return gamma;
    }

    //done
    @Override
    public String actionName(int a) {
        return A.get(a);
    }

    //done
    @Override
    public String observationName(int o) {
        return O.get(o);
    }

    //done
    @Override
    public String stateName(int s) {
        return S.get(s);
    }

    public int getRandomAction() {
        return (Utils.random.nextInt(numA()));
    }

    /// ???
    public int getRandomObservation(BeliefStateImp bel, int a) {
        double roulette = Utils.random.nextDouble();
        Vector vect = new Vector(ZforA(a).transpose().operate(bel.getPoint()));
        double sum = 0.0;
        for (int o = 0; o < numO(); o++) {
            sum += vect.getEntry(o);
            if (roulette < sum)
                return o;
        }
        return (-1);
    }


    //TODO
    public ValueFunctionImp getRewardValueFunction(int a) {
        ValueFunctionImp vf = new ValueFunctionImp(numS());
        vf.push(RforA(a), a);
        return vf;
    }

    //done
    public double getRewardMax() {
        double max_val = Double.NEGATIVE_INFINITY;
        for (int a = 0; a < numA(); a++) {
            double test_val = getRewardMax(a);
            if (test_val > max_val)
                max_val = test_val;
        }
        return max_val;
    }

    //done
    public double getRewardMin() {
        double min_val = Double.POSITIVE_INFINITY;
        for (int a = 0; a < numA(); a++) {
            double test_val = getRewardMin(a);
            if (test_val < min_val)
                min_val = test_val;
        }
        return min_val;
    }

    //done
    public double getRewardMaxMin() {
        double max_val = Double.NEGATIVE_INFINITY;
        for (int a = 0; a < numA(); a++) {
            double test_val = getRewardMin(a);
            if (test_val > max_val)
                max_val = test_val;
        }
        return max_val;
    }

    //done
    public double getRewardMin(int a) {
        return R.get(a).getMinValue();
    }

    //done
    public double getRewardMax(int a) {
        return R.get(a).getMaxValue();
    }

    //TODO
    public AlphaVector getRewardVec(int a, BeliefState bel) {
        return (new AlphaVector(RforA(a), a));
    }

    //done,need reform
    public String toString() {
        String rep = "Dimensions:\n";

        rep += "|S|: " + numS() + ", ";
        rep += "|A|: " + numA() + ", ";
        rep += "|Z|: " + numO() + "\n";
        rep += "Transition table: \n";
        for (int a=0; a< numA(); a++) rep += "T:"+actionName(a)+"\n" + T.get(a).toString()+"\n";
        rep += "Oservation table: \n";
        for (int a=0; a< numA(); a++) rep += "Z:"+actionName(a)+"\n" + Z.get(a).toString()+"\n";
        rep += "Rewards table: \n";
        for (int a=0; a< numA(); a++) rep += "R:"+actionName(a)+"\n" + R.get(a).toString()+"\n";
        rep += "Initial Belief: \n";
        rep += initBelief.getPoint().toString();
        return rep;
    }

    public static class Factory{
        public static POMDP parse(String filename){
            POMDPImp model = new POMDPImp();
            String content = "";
            try {
                File file = new File("data/"+filename);
                if (file.isFile() && file.exists()){
                    InputStreamReader
                            read = new InputStreamReader(new FileInputStream(file), "GBK");
                    BufferedReader br = new BufferedReader(read);
                    String line;
                    while((line = br.readLine())!=null){
                        content =content + line + "\n";
                    }
                    read.close();
                }else{
                    System.out.println("Cannot find file");
                }
            }catch (Exception e){}


            String[] list = content.split("\n\n");
            for(int i=0;i<list.length;i++){
                if (list[i].startsWith("SETUP:")){
                    String[] setList = list[i].split("\n");
                    for(int j=1;j<setList.length;j++){
                        if(setList[j].startsWith("discount:")){
                            model.gamma = Double.parseDouble(setList[j].split(" ")[1]);
                        }else if(setList[j].startsWith("values:")){
                            String value = setList[j].split(" ")[1];
                            if (value.equals("reward")){
//                                model.sign = 1;
                            }else{
//                                model.sign = -1;
                            }
                        }else if(setList[j].startsWith("states:")){
                            String[] stateList = setList[j].split(" ");
                            for(int k=1;k<stateList.length;k++){
                                model.S.add(stateList[k]);
                            }
                        }else if(setList[j].startsWith("actions:")){
                            String[] actionList = setList[j].split(" ");
                            for(int k=1;k<actionList.length;k++){
                                model.A.add(actionList[k]);
                            }
                        }else if(setList[j].startsWith("observations:")){
                            String[] observationList = setList[j].split(" ");
                            for (int k=1;k<observationList.length;k++){
                                model.O.add(observationList[k]);
                            }
                        }
                    }
                }
                else if(list[i].startsWith("IB:")){
                    double[] b = new double[model.S.size()];
                    String[] BRows = list[i].split("\n");
                    String[] rowValues = BRows[1].split(" ");
                    for (int k=0;k<rowValues.length;k++){
                        b[k] = Double.parseDouble(rowValues[k]);
                    }
                    model.initBelief = new BeliefStateImp(new Vector(b),0d);

                }
                else if(list[i].startsWith("T:")){
                    String[] TmatrixRows = list[i].split("\n");
                    String action = TmatrixRows[0].split(" ")[1];
                    int stateNum = model.S.size();
                    double[][] t = new double[stateNum][stateNum];
                    if(TmatrixRows[1].equals("identity")){
                        for (int j=0;j<stateNum;j++){
                            t[j][j] = 1;
                        }
                        model.T.put(model.A.indexOf(action), MatrixUtils.createRealMatrix(t));
                    }else{
                        for(int j=1;j<TmatrixRows.length;j++){
                            String[] rowValues = TmatrixRows[j].split(" ");
                            for (int k=0;k<rowValues.length;k++){
                                t[j-1][k] = Double.parseDouble(rowValues[k]);
                            }
                        }
                        model.T.put(model.A.indexOf(action),MatrixUtils.createRealMatrix(t));
                    }
                }
                else if(list[i].startsWith("O:")){
                    int stateNum = model.S.size();
                    int observationNum = model.O.size();
                    double[][] o = new double[stateNum][observationNum];
                    String[] OmatrixRows = list[i].split("\n");
                    String action = OmatrixRows[0].split(" ")[1];
                    if(OmatrixRows[1].equals("uniform")){
                        for(int j=0;j<stateNum;j++){
                            for(int k=0;k<observationNum;k++){
                                o[j][k] = 1.0/observationNum;
                            }
                        }
                        model.Z.put(model.A.indexOf(action),MatrixUtils.createRealMatrix(o));
                    }else{
                        for (int j=1;j<OmatrixRows.length;j++){
                            String[] rowValues = OmatrixRows[j].split(" ");
                            for (int k=0;k<rowValues.length;k++){
                                o[j-1][k] = Double.parseDouble(rowValues[k]);
                            }
                        }
                        model.Z.put(model.A.indexOf(action),MatrixUtils.createRealMatrix(o));
                    }
                }
                else if(list[i].startsWith("R:")){
                    int stateNum = model.S.size();
                    double[] r = new double[stateNum];
                    String[] RRows = list[i].split("\n");
                    String action = RRows[0].split(" ")[1];
                    String[] rowValues = RRows[1].split(" ");
                    for (int k=0;k<stateNum;k++){
                        r[k] = Double.parseDouble(rowValues[k]);
                    }
                    model.R.put(model.A.indexOf(action),new Vector(r));
                }
            }
            return model;
        }
    }

}

