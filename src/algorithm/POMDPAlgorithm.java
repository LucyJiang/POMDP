package algorithm;

import pomdp.Action;
import pomdp.POMDP;
import pomdp.State;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by LeoDong on 12/03/2015.
 */
public abstract class POMDPAlgorithm implements Algorithm<Configuration, Result> {
    protected Configuration config;
    protected POMDP model;
    protected Result result;
    protected State currentState;
    protected List<HistoryRecord> history;
    protected short stage = STAGE_INITIAL;

    public List<HistoryRecord> getHistory() {
        return history;
    }

    @Override
    public boolean input(Configuration config) {
        if (config != null) {
            if (!config.isConsistant()) {
                return false;
            }
            this.history = new LinkedList<HistoryRecord>();
            this.result = new Result();

            this.config = config;
            this.model = config.getModel();
            this.currentState = this.model.getStateSet()
                    .getState(config.getInitialStateID());

            //put initial history
            this.history.add(new HistoryRecord("START",
                    config.getInitialStateID(),
                    0,
                    0));

            return true;
        }
        return false;
    }

    @Override
    public void forward() {
        if (this.getStage() != STAGE_INPUT_FINISH) {
            throw new AlgorithmException("Input First!");
        }
        while (/*condition to stop, can use something in config*/ "".equals("")) {
            Action act = this.step();

            //process history. this part can be put in step();
            HistoryRecord newHis = HistoryRecord.consume(this.history.get(
                    this.history.size() - 1), act);
            this.history.add(newHis);
        }

        this.result = null; //TODO process result
        this.stage = STAGE_EXECUTE_FINISH;

    }

    ;

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
    public void run() {

    }

    ;

    protected abstract Action step();
}
