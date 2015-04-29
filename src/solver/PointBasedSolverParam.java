package solver;

public class PointBasedSolverParam {
    public static final int BACKUP_SYNC_FULL = 1;
    public static final int BACKUP_ASYNC_FULL = 3;
    public static final int EXPAND_GREEDY_ERROR_REDUCTION = 1;
    public static final int EXPAND_EXPLORATORY_ACTION = 2;
    public static final int EXPAND_RANDOM_EXPLORE = 3;
    private int backupMethod;
    private int expandMethod;
    private int backupHorizon;
    private int maxTotalPoints;

    public PointBasedSolverParam(
            int backupMethod, int expandMethod,
            int backupHorizon, int maxTotalPoints) {
        super();
        this.backupMethod = backupMethod;
        this.expandMethod = expandMethod;
        this.backupHorizon = backupHorizon;
        this.maxTotalPoints = maxTotalPoints;
    }

    public PointBasedSolverParam(
            int backupMethod,
            int expandMethod,
            int backupHorizon) {
        this(backupMethod, expandMethod, backupHorizon, Integer.MAX_VALUE);
    }


    public int getBackupMethod() {
        return backupMethod;
    }

    public int getExpandMethod() {
        return expandMethod;
    }


    public int getBackupHorizon() {
        return backupHorizon;
    }


    public int getMaxTotalPoints() {
        return maxTotalPoints;
    }


}
