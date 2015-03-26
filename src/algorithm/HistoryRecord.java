package algorithm;

import pomdp.Action;
import pomdp.State;

/**
 *
 */
public class HistoryRecord {
    private String fromID;
    private String toID;
    private double reward;
    private double totalReward;
    //TODO belief and so on....

    public HistoryRecord(
            String from,
            String to,
            double reward,
            double totalReward) {
        this.fromID = from;
        this.toID = to;
        this.reward = reward;
        this.totalReward = totalReward;
    }

    public static HistoryRecord consume(HistoryRecord prev, Action action){
        //TODO
        return new HistoryRecord(null,null,0,0);
    }
}
