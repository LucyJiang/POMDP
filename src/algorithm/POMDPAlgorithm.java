package algorithm;

import pomdp.Action;
import pomdp.POMDP;
import pomdp.State;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by LeoDong on 12/03/2015.
 */
public abstract class POMDPAlgorithm
        implements Algorithm<Configuration, Result> {
    protected Configuration config;
    protected POMDP model;
    protected State currentState;
    protected Result result = new Result();
    protected List<HistoryRecord> history = new LinkedList<HistoryRecord>();

    protected short stage = STAGE_INITIAL;

    public List<HistoryRecord> getHistory() {
        return history;
    }

    @Override
    public boolean input(Configuration config) {
        if (config != null) {
            //check consistant of the configuration
            if (!config.isConsistent()) {
                return false;
            }

            /*link references of config and its relative info*/
            this.config = config;
            this.model = config.getModel();
            this.currentState = this.model.getStateSet()
                                          .getState(config.getInitialStateID());

            /* put initial history */
            this.history.add(new HistoryRecord("START",
                                               config.getInitialStateID(),
                                               0,
                                               0));

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

            Action act = decide();

            move(act);

            recordHistory(act);

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
    protected abstract Action decide();


    protected void move(Action move) {
        this.currentState = move.getToState();
    }

    protected void recordHistory(Action act) {
        //process history. this part can be put in decide();
        HistoryRecord newHis = HistoryRecord.consume(this.history.get(
                this.history.size() - 1), act);
        this.history.add(newHis);
    }
}
