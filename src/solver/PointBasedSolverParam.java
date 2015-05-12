package solver;

/**
 * Parameters for PBVI solver
 */
public class PointBasedSolverParam {
    /**
     * Backup policies
     */
    public static final int BACKUP_SYNC_FULL = 1;
    public static final int BACKUP_ASYNC_FULL = 3;

    /**
     * Expand policies
     */
    public static final int EXPAND_GREEDY_ERROR_REDUCTION = 1;
    public static final int EXPAND_EXPLORATORY_ACTION = 2;
    public static final int EXPAND_RANDOM_EXPLORE = 3;


    private int backupMethod;
    private int expandMethod;
    private int backupHorizon;
    private int maxTotalPoints;

    /**
     * Constructor
     * @param backupMethod
     * @param expandMethod
     * @param backupHorizon
     * @param maxTotalPoints
     */
    public PointBasedSolverParam(
            int backupMethod, int expandMethod,
            int backupHorizon, int maxTotalPoints) {
        super();
        this.backupMethod = backupMethod;
        this.expandMethod = expandMethod;
        this.backupHorizon = backupHorizon;
        this.maxTotalPoints = maxTotalPoints;
    }

    /**
     * Constructor with default maxTotalPoints Integer.MAX_VALUE
     * @param backupMethod
     * @param expandMethod
     * @param backupHorizon
     */
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
