package pomdp;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class POMDP {

    private List<String> S = new ArrayList<String>(); //NS
    private List<String> A = new ArrayList<String>(); //NA
    private List<String> O = new ArrayList<String>(); //NO
    private int sign = 1;

    private double discount = 0.99;

    private HashMap<String,RealMatrix> T = new HashMap<String,RealMatrix>(); //NA: NS * NS
    private HashMap<String,RealMatrix> Z = new HashMap<String,RealMatrix>(); //NA: NS * NO
    private HashMap<String,RealMatrix> R = new HashMap<String,RealMatrix>(); //NA: NS * NS

    public RealMatrix TforA(String a){
        return T.get(a);
    }
    public RealMatrix TforA(int a){
        return T.get(A.get(a));
    }

    public RealMatrix ZforA(String a){
        return Z.get(a);
    }

    public RealMatrix ZforA(int a){
        return Z.get(A.get(a));
    }

    public RealMatrix RforA(String a){
        return R.get(a);
    }

    public RealMatrix RforA(int a){
        return R.get(A.get(a));
    }

    public HashMap<String, RealMatrix> getR() {
        return R;
    }

    public List<String> getS() {
        return S;
    }

    public List<String> getA() {
        return A;
    }

    public List<String> getO() {
        return O;
    }

    public int getSign() {
        return sign;
    }

    public double getDiscount() {
        return discount;
    }

    public HashMap<String, RealMatrix> getT() {
        return T;
    }

    public HashMap<String, RealMatrix> getZ() {
        return Z;
    }

    public double funT(int s, int a, int sp){
        return this.TforA(a).getEntry(s,sp);
    }

    public double funO(int a, int sp, int o){
        return this.ZforA(a).getEntry(sp,o);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("POMDP{");
        sb.append("S=").append(S);
        sb.append(", A=").append(A);
        sb.append(", O=").append(O);
        sb.append(", discount=").append(discount);
        sb.append("\n T=\n").append(T);
        sb.append("\n Z=\n").append(Z);
        sb.append("\n R=\n").append(R);
        sb.append("\n}");
        return sb.toString();
    }

    public static class Factory{
        public static POMDP case1(){
            POMDP model = new POMDP();
            model.sign=1;
            model.discount=0.95;

            model.S.add("s1");
            model.S.add("s2");
            model.S.add("s3");

            model.A.add("a1");
            model.A.add("a2");

            model.O.add("o1");
            model.O.add("o2");

            double[][] t1 = {{0.1,0.2,0.7},{0.2,0.6,0.2},{0.5,0.1,0.4}};
            double[][] t2 = {{0.4,0.2,0.4},{0.7,0.1,0.2},{0.2,0.4,0.4}};
            model.T.put("a1", MatrixUtils.createRealMatrix(t1));
            model.T.put("a2", MatrixUtils.createRealMatrix(t2));

            double[][] o1 = {{0.1,0.9},{0.2,0.8},{0.5,0.5}};
            double[][] o2 = {{0.4,0.6},{0.7,0.3},{0.2,0.8}};
            model.Z.put("o1", MatrixUtils.createRealMatrix(o1));
            model.Z.put("o2", MatrixUtils.createRealMatrix(o2));

            double[][] r1 = {{5,-5,0},{10,3,-8},{-10,3,3}};
            double[][] r2 = {{-6,3,2},{3,-6,3},{1,0,-9}};
            model.R.put("r1", MatrixUtils.createRealMatrix(r1));
            model.R.put("r2", MatrixUtils.createRealMatrix(r2));
            return model;
        }
    }
}
