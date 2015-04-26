package algorithm;

import exception.AlgorithmException;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import pomdp.POMDP;

import java.util.LinkedList;
import java.util.List;

/**
*
*/
public abstract class POMDPAlgorithm
        implements Policy<Configuration, Result> {
    protected Configuration config;
    protected POMDP model;
    protected Result result = new Result();

    protected short stage = STAGE_INITIAL;


    protected List<HistoryRecord> H = new LinkedList<HistoryRecord>();
    protected RealVector belief;


    protected void beliefUpdate(int a, int o){
        RealVector pb = belief.copy();//previous belief
        double denominator = 0;
        for (int s=0;s<model.getS().size();s++){
            double bs=pb.getEntry(s);
            double inner = 0d;
            for (int sp=0;s<model.getS().size();sp++){
                inner += model.funT(s,a,sp)*model.funO(a,sp,o);
            }
            denominator += bs*inner;
        }

        for (int sp=0;sp<model.getS().size();sp++){

            double sum = 0;
            for (int s=0;s<model.getS().size();s++){
                sum += model.funT(s,a,sp)*pb.getEntry(s);
            }
            double numerator = model.funO(a,sp,o) * sum;

            this.belief.setEntry(sp,numerator/denominator);

        }

    }

    public List<HistoryRecord> getH() {
        return H;
    }

    @Override
    public boolean input(Configuration config) {
        if (config != null) {
            //check consistency of the configuration
            if (!config.isConsistent()) {
                return false;
            }

            /*link references of config and its relative info*/
            this.config = config;
            this.model = config.getModel();

            //initial belief
            belief = new ArrayRealVector(model.getS().size());
            belief.set(1d/model.getS().size());

            //TODO initial history


            return true;
        }
        return false;
    }

    @Override
    public void execute() {
        if (this.getStage() != STAGE_INPUT_FINISH) {
            throw new AlgorithmException("Provide input First!");
        }
        while (/*condition to stop, can use something in config*/ ""
                .equals("")) {



//            move(act);
//
//            recordHistory(act);

        }

        this.result = null; //TODO process result
        this.stage = STAGE_EXECUTE_FINISH;

    }

    @Override
    public Result getResult() {
        if (this.getStage() != STAGE_EXECUTE_FINISH) {
            throw new AlgorithmException("Execute First!");
        }
        return this.result;
    }

    @Override
    public short getStage() {
        return this.stage;
    }

    @Override
    /**
     * Multi-Thread could be only used when 'time' is not important
     */
    public void run() {
        execute();
    }

    /**
     * Decide which action to conduct, without any modifies on model,
     * configuration and result
     *
     * @return the Action to conduct
     */
//    protected abstract Action decide();
//
//
//    protected void move(Action move) {
//        this.currentState = move.getToStates();
//    }
//
//    protected void recordHistory(Action act) {
//        //process history. this part can be put in decide();
//        HistoryRecord newHis = HistoryRecord.consume(this.history.get(
//                this.history.size() - 1), act);
//        this.history.add(newHis);
//    }
}
