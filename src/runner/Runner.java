package runner;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import pomdp.POMDP;

/**
 *
 */
public class Runner {

    public static void main(String[] args) {
        RealVector a = new ArrayRealVector(3);
        a.set(1d/3);
        System.out.print(a);

//        POMDP m = POMDP.Factory.case1();
//        System.out.println(m);

    }

}
