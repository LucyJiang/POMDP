package solver.iteration;

import java.util.ArrayList;

public class Timer {

    protected long initTime;
    protected ArrayList<Long> timeRecords;
    protected int iterNumber;

    protected long start_time = -1;

    public void start() {
        this.start_time = System.currentTimeMillis();
    }

    public void stop(){
        this.start_time = -1;
    }

    public long getInitTime() {
        return initTime;
    }

    public long recordInitTime(){
        if(this.start_time==-1){
            throw new RuntimeException("timer hasn't start");
        }
        this.initTime = System.currentTimeMillis() - this.start_time;
        return this.initTime;
    }

    public long recordIterTime(){
        if(this.start_time==-1){
            throw new RuntimeException("timer hasn't start");
        }
        long t = System.currentTimeMillis() - this.start_time;
        this.iterNumber++;
        timeRecords.add(t);
        return t;
    }


    public Timer() {
        timeRecords = new ArrayList<Long>();
        iterNumber = 0;
    }

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