package solver;

import java.util.ArrayList;

import model.POMDP;

public abstract class IterationStats {

	protected POMDP POMDP;
	
	public int register(long iTime) {
		iteration_time.add(new Long(iTime));
		iterations++;
		return(iterations);
	}

	public long init_time;
	public ArrayList<Long> iteration_time;
	public int iterations;

	public IterationStats(POMDP POMDP) {
		this.POMDP = POMDP;
		iteration_time=new ArrayList<Long>();
		iterations=0;
	}
	
	public String toString(){
		String retval="ITERATION STATS\n";
		retval+=      "-----------------\n";
		retval+=      "iterations = " + iterations + "\n";
		retval+=      "init time = " + init_time + "\n" ;
		retval+=      "iteration time  = ";
		long sum=0;
		for (Long l:iteration_time){
			sum+=l.longValue(); 
		}
		retval+=sum+"\n";
		return retval;
		
	}

}