package solver.iteration;

import java.util.ArrayList;

public abstract class Timer {

    private long initTime;
    private ArrayList<Long> timeRecords;
    private int iterNumber;

    private long start_time = -1;

    public void writeTimeRecords(long tr) {
        iterNumber++;
		timeRecords.add(new Long(tr));
	}

	public Timer() {
		timeRecords =new ArrayList<Long>();
		iterNumber =0;
	}

    public long getTotalTime(){
        long totalTime=0;
        for (Long l: timeRecords){
            totalTime+=l;
        }
        return totalTime;
    }

    public void writeInitTime(long time){
        this.initTime = time;
    }

    public int getIterNumber(){
        return iterNumber;
    }

    public ArrayList<Long> getTimeRecords(){
        return timeRecords;
    }
	
	public String toString(){
		String ret="Timer:\n";
		ret+=      "-----------------\n";
		ret+=      "iterNumber = " + iterNumber + "\n";
		ret+=      "initTime = " + initTime + "\n" ;
		ret+=      "iteration time  = ";
		ret+= getTotalTime()+"\n";
		return ret;
	}

}