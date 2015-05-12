
package model;

import common.AlphaVector;
import common.BeliefState;
import common.BeliefStateImp;
import common.ValueFunctionImp;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import util.Utils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class POMDPImp implements POMDP {

    /**
     * Set of State, Action, Observation's names
     */
    private List<String> S = new ArrayList<String>(); //NS
    private List<String> A = new ArrayList<String>(); //NA
    private List<String> O = new ArrayList<String>(); //NO

    /**
     * T function matrix
     */
    private HashMap<Integer, RealMatrix> T = new HashMap<Integer, RealMatrix>();

    /**
     * Z function matrix
     */
    private HashMap<Integer, RealMatrix> Z = new HashMap<Integer, RealMatrix>();

    /**
     * R function matrix
     */
    private HashMap<Integer, Vector> R = new HashMap<Integer, Vector>();

    /**
     * discount factor
     */
    private double gamma;

    /**
     * initial belief of the model
     */
    private BeliefStateImp initBelief;


    /**
     * BeliefState update method
     * tao(b,a,o)
     */
    public BeliefState nextBeliefState(BeliefState b, int a, int o) {

        BeliefState bPrime;
        Vector b1 = b.getPoint();
        Vector b2 = new Vector(TforA(a).transpose().operate(b1));

        //update vector b2
        b2 = new Vector(b2.ebeMultiply(ZforA(a).getColumnVector(o)));

        // P(o|b,a)
        double poba = b2.getL1Norm();
        // make sure we can normalize
        if (poba < 0.00001) {
            // P(o|b,a) = 0
            bPrime = initBelief;
        } else {
            // safe to normalize now
            b2.scale(1.0 / poba);
            bPrime = new BeliefStateImp(b2, poba);
        }
        return bPrime;
    }

    /**
     * Immediate Reward for a given BeliefState and a action
     */
    public double expectedImmediateReward(BeliefState bel, int a) {
        Vector b = bel.getPoint();
        return b.dotProduct(RforA(a));
    }

    /**
     * Function P(o|b,a)
     */
    public Vector observationProbabilities(BeliefState b, int a) {
        Vector b1 = b.getPoint();
        Vector Tb = new Vector(TforA(a).operate(b1));
        Vector Poba = new Vector(ZforA(a).transpose().operate(Tb));
        return Poba;
    }

    public RealMatrix TforA(int a) {
        return T.get(a).copy();
    }

    public RealMatrix ZforA(int a) {
        return Z.get(a).copy();
    }

    public Vector RforA(int a) {
        return R.get(a).copy();
    }

    public BeliefState getInitBeliefState() {
        return initBelief.copy();
    }

    public int numS() {
        return S.size();
    }

    public int numA() {
        return A.size();
    }

    public int numO() {
        return O.size();
    }

    public double gamma() {
        return gamma;
    }

    @Override
    public String actionName(int a) {
        return A.get(a);
    }

    @Override
    public String observationName(int o) {
        return O.get(o);
    }

    @Override
    public String stateName(int s) {
        return S.get(s);
    }

    /**
     * Get a random action
     * @return
     */
    public int getRandomAction() {
        return (Utils.random.nextInt(numA()));
    }

    /**
     * Get a random observation
     * @param bel
     * @param a
     * @return
     */
    public int getRandomObservation(BeliefState bel, int a) {
        double roulette = Utils.random.nextDouble();
        Vector vect = new Vector(ZforA(a).transpose().operate(bel.getPoint()));
        double sum = 0.0;
        int o = 0;
        for (; o < numO(); o++) {
            sum += vect.getEntry(o);
            if (roulette < sum)
                return o;
        }
        return o-1;
    }

    public ValueFunctionImp getRewardValueFunction(int a) {
        ValueFunctionImp vf = new ValueFunctionImp(numS());
        vf.push(RforA(a), a);
        return vf;
    }

    // Reward relative function

    public double getRewardMax() {
        double max_val = Double.NEGATIVE_INFINITY;
        for (int a = 0; a < numA(); a++) {
            double test_val = getRewardMax(a);
            if (test_val > max_val)
                max_val = test_val;
        }
        return max_val;
    }


    public double getRewardMin() {
        double min_val = Double.POSITIVE_INFINITY;
        for (int a = 0; a < numA(); a++) {
            double test_val = getRewardMin(a);
            if (test_val < min_val)
                min_val = test_val;
        }
        return min_val;
    }


    public double getRewardMaxMin() {
        double max_val = Double.NEGATIVE_INFINITY;
        for (int a = 0; a < numA(); a++) {
            double test_val = getRewardMin(a);
            if (test_val > max_val)
                max_val = test_val;
        }
        return max_val;
    }


    public double getRewardMin(int a) {
        return R.get(a).getMinValue();
    }


    public double getRewardMax(int a) {
        return R.get(a).getMaxValue();
    }


    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("POMDP:\n");
        sb.append("---------------------\n");
        sb.append("S: " + S + "\n");
        sb.append("A: " + A + "\n");
        sb.append("O: " + O + "\n");
        for (int a = 0; a < numA(); a++) {
            sb.append("T[" + actionName(a) + "]:\n ");
            int s = 0;
            for (; s < numS() - 1; s++) {
                sb.append(TforA(a).getRowVector(s) + "\n ");
            }
            sb.append(TforA(a).getRowVector(s) + "\n");
        }
        for (int a = 0; a < numA(); a++) {
            sb.append("Z[" + actionName(a) + "]:\n ");
            int s = 0;
            for (; s < numS() - 1; s++) {
                sb.append(ZforA(a).getRowVector(s) + "\n ");
            }
            sb.append(ZforA(a).getRowVector(s) + "\n");
        }
        for (int a = 0; a < numA(); a++) {
            sb.append("R[" + actionName(a) + "]:\n " + RforA(a) + "\n");
        }
        sb.append("Initial Belief: \n " + initBelief.getPoint() + "\n");
        sb.append("======================");
        return sb.toString();
    }

    /**
     * Factory inner class
     */
    public static class Factory {
        /**
         * parser method, parse a .POMDP file to a POMDP object
         * @param filename
         * @return
         * @throws IOException
         */
        public static POMDP parse(String filename) throws IOException {
            POMDPImp model = new POMDPImp();
            String content = "";
            File file = new File(filename);
            if (file.isFile() && file.exists()) {
                InputStreamReader
                        read = new InputStreamReader(new FileInputStream(file),
                                                     "GBK");
                BufferedReader br = new BufferedReader(read);
                String line;
                while ((line = br.readLine()) != null) {
                    content = content + line + "\n";
                }
                read.close();
            } else {
                throw new IOException("Cannot find file: " + filename);
            }


            String[] list = content.split("\n\n");
            for (int i = 0; i < list.length; i++) {
                if (list[i].startsWith("SETUP:")) {
                    String[] setList = list[i].split("\n");
                    for (int j = 1; j < setList.length; j++) {
                        if (setList[j].startsWith("discount:")) {
                            model.gamma = Double
                                    .parseDouble(setList[j].split(" ")[1]);
                        } else if (setList[j].startsWith("states:")) {
                            String[] stateList = setList[j].split(" ");
                            for (int k = 1; k < stateList.length; k++) {
                                model.S.add(stateList[k]);
                            }
                        } else if (setList[j].startsWith("actions:")) {
                            String[] actionList = setList[j].split(" ");
                            for (int k = 1; k < actionList.length; k++) {
                                model.A.add(actionList[k]);
                            }
                        } else if (setList[j].startsWith("observations:")) {
                            String[] observationList = setList[j].split(" ");
                            for (int k = 1; k < observationList.length; k++) {
                                model.O.add(observationList[k]);
                            }
                        }
                    }
                } else if (list[i].startsWith("IB:")) {
                    double[] b = new double[model.S.size()];
                    String[] BRows = list[i].split("\n");
                    String[] rowValues = BRows[1].split(" ");
                    for (int k = 0; k < rowValues.length; k++) {
                        b[k] = Double.parseDouble(rowValues[k]);
                    }
                    model.initBelief = new BeliefStateImp(new Vector(b), 0d);

                } else if (list[i].startsWith("T:")) {
                    String[] TmatrixRows = list[i].split("\n");
                    String action = TmatrixRows[0].split(" ")[1];
                    int stateNum = model.S.size();
                    double[][] t = new double[stateNum][stateNum];
                    if (TmatrixRows[1].equals("identity")) {
                        for (int j = 0; j < stateNum; j++) {
                            t[j][j] = 1;
                        }
                        model.T.put(model.A.indexOf(action),
                                    MatrixUtils.createRealMatrix(t));
                    } else {
                        for (int j = 1; j < TmatrixRows.length; j++) {
                            String[] rowValues = TmatrixRows[j].split(" ");
                            for (int k = 0; k < rowValues.length; k++) {
                                t[j - 1][k] = Double.parseDouble(rowValues[k]);
                            }
                        }
                        model.T.put(model.A.indexOf(action),
                                    MatrixUtils.createRealMatrix(t));
                    }
                } else if (list[i].startsWith("O:")) {
                    int stateNum = model.S.size();
                    int observationNum = model.O.size();
                    double[][] o = new double[stateNum][observationNum];
                    String[] OmatrixRows = list[i].split("\n");
                    String action = OmatrixRows[0].split(" ")[1];
                    if (OmatrixRows[1].equals("uniform")) {
                        for (int j = 0; j < stateNum; j++) {
                            for (int k = 0; k < observationNum; k++) {
                                o[j][k] = 1.0 / observationNum;
                            }
                        }
                        model.Z.put(model.A.indexOf(action),
                                    MatrixUtils.createRealMatrix(o));
                    } else {
                        for (int j = 1; j < OmatrixRows.length; j++) {
                            String[] rowValues = OmatrixRows[j].split(" ");
                            for (int k = 0; k < rowValues.length; k++) {
                                o[j - 1][k] = Double.parseDouble(rowValues[k]);
                            }
                        }
                        model.Z.put(model.A.indexOf(action),
                                    MatrixUtils.createRealMatrix(o));
                    }
                } else if (list[i].startsWith("R:")) {
                    int stateNum = model.S.size();
                    double[] r = new double[stateNum];
                    String[] RRows = list[i].split("\n");
                    String action = RRows[0].split(" ")[1];
                    String[] rowValues = RRows[1].split(" ");
                    for (int k = 0; k < stateNum; k++) {
                        r[k] = Double.parseDouble(rowValues[k]);
                    }
                    model.R.put(model.A.indexOf(action), new Vector(r));
                }
            }
            if (model.initBelief == null) {
                model.initBelief = BeliefStateImp.generateRandom(model.numS());
            }
            return model;
        }
    }

}

