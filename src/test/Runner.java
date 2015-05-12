package test;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import model.POMDP;
import model.POMDPImp;
import util.Utils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Gateway of the whole system
 */
public class Runner {
    public static void main(String[] args) {
        System.setProperty("java.library.path","/usr/local/lib/jni");
        try {
            Field fieldSysPath = ClassLoader.class.getDeclaredField( "sys_paths" );
            fieldSysPath.setAccessible( true );
            fieldSysPath.set( null, null );
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        /**
         * Build Command from user input
         */
        Command jct = new Command();
        JCommander jc = new JCommander(jct);
        try {
            jc.parse(args);
            if(jct.help){
                jc.usage();
                return;
            }
        } catch (ParameterException e) {
            System.out.println("Error: " + e.getMessage());
            jc.usage();
            return;
        }

        System.out.println(jct);

        /**
         * conduct full test for user input
         */
        try {
            POMDP pomdp = POMDPImp.Factory.parse(jct.modelFile);
            List<TestResult> tr = Tester
                    .FullTestSet(pomdp, jct.loopTime, jct.iterTime);
            Utils.writeTestToFile(pomdp, tr, jct.resultFile, "TEST Result");
            Utils.generateChart(tr, "");
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
    }

    /**
     * Command used by CLI
     */
    public static class Command {

        @Parameter(names = {"-m",
                            "--model-file"}, description = "relative path and name of the POMDP model file.")
        public String modelFile = "test.POMDP";

        @Parameter(names = {"-i",
                            "--iteration-number"}, description = "number of iterations for the algorithms.", validateWith = PositiveInteger.class)
        public Integer iterTime = 10;

        @Parameter(names = {"-l",
                            "--loop-number"}, description = "number of loops you want the algorithms to run.", validateWith = PositiveInteger.class)
        public Integer loopTime = 1;

        @Parameter(names = {"-r",
                            "--result-file"}, description = "relative path and name of the generated test result.")
        public String resultFile = "test_result";

        @Parameter(names = {"-h","--help"}, help = true,  description = "Show Usage")
        private boolean help;

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer(
                    "Command{");
            sb.append("modelFile='").append(modelFile).append('\'');
            sb.append(", iterTime=").append(iterTime);
            sb.append(", loopTime=").append(loopTime);
            sb.append(", resultFile='").append(resultFile).append('\'');
            sb.append(", help=").append(help);
            sb.append('}');
            return sb.toString();
        }

    }


}
