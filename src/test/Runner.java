package test;

import model.POMDP;
import model.POMDPImp;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RefineryUtilities;

import java.util.ArrayList;

/**
 * Created by LeoDong on 28/04/2015.
 */
public class Runner {
    public static void main(String[] args) {

        POMDP pomdp= POMDPImp.Factory.parse("test.POMDP");
        System.out.println(pomdp);

        Tester tester = new Tester(pomdp);

        double epsi=1e-6*(1-pomdp.gamma())/(2*pomdp.gamma());
        tester.limitMaxIterationNumber(1);
        tester.limitValueConvergence(epsi);
        ArrayList<TestResult> testResults = new ArrayList<TestResult>();

        System.out.println("Test [GreedyPBVI]:");
        testResults.add(tester.TestGreedyPBVI());
        System.out.println("Test [ExploratoryPBVI]:");
        testResults.add(tester.TestExploratoryPBVI());
        System.out.println("Test [PerseusPBVI]:");
        testResults.add(tester.TestPerseusPBVI());
        System.out.println("Test [IncrementalPruning]:");
        testResults.add(tester.TestIncrementalPruning(epsi));
        System.out.println("Test [QMDP]:");
        testResults.add(tester.TestQMDP());

        System.out.println("\nTest Results:\n");
        for (TestResult r : testResults){
            System.out.println(r+"\n");
        }

        DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
        for (TestResult r : testResults){
            String name = r.getTestName();
            dataset.addValue(r.getInitTime(),name,"init");
            for (int i = 0; i< r.getIterTime().size();i++){
                dataset.addValue(r.getIterTime().get(i),name,"iter"+i);
            }
        }
        LineChart_IterationTimeComparision.createForDataSet(dataset);

    }
}
