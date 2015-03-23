package runner;

import pomdp.Action;
import pomdp.POMDP;
import pomdp.State;

/**
 * Created by LeoDong on 07/03/2015.
 */
public class Runner {

    public static void main(String[] args) {
        POMDP m = POMDP.Factory.createFromFile("data/test1.pomdp");
        System.out.println(m);

    }

}
