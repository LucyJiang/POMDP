package solver.util;

import java.util.ArrayList;

/**
 * Timer for general iteration solver
 */
public class Timer {

    /**
     * initial time
     */
    protected long initTime;
    protected ArrayList<Long> timeRecords;
    protected int iterNumber;

    /**
     * a temporary time record to get the time period
     */
    protected long start_time = -1;

    public Timer() {
        timeRecords = new ArrayList<Long>();
        iterNumber = 0;
    }

    /**
     * start the timer
     */
    public void start() {
        this.start_time = System.currentTimeMillis();
    }

    /**
     * stop the timer
     */
    public void stop() {
        this.start_time = -1;
    }

    public long getInitTime() {
        return initTime;
    }

    /**
     * record initial time
     */
    public long recordInitTime() {
        if (this.start_time == -1) {
            throw new RuntimeException("timer hasn't start");
        }
        this.initTime = System.currentTimeMillis() - this.start_time;
        return this.initTime;
    }

    /**
     * record iteration time
     * @return
     */
    public long recordIterTime() {
        if (this.start_time == -1) {
            throw new RuntimeException("timer hasn't start");
        }
        long t = System.currentTimeMillis() - this.start_time;
        this.iterNumber++;
        timeRecords.add(t);
        return t;
    }

    /**
     * get total time consumed
     * @return
     */
    public long getTotalTime() {
        long totalTime = 0;
        for (Long l : timeRecords) {
            totalTime += l;
        }
        return totalTime;
    }

    public int getIterNumber() {
        return iterNumber;
    }

    public ArrayList<Long> getTimeRecords() {
        return timeRecords;
    }

}