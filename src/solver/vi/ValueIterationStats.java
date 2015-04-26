package solver.vi;

import java.util.ArrayList;

import model.POMDP;
import solver.IterationStats;

public class ValueIterationStats extends IterationStats {
	
	public long total_lp_time;
	
	public ValueIterationStats(POMDP POMDP){
		super(POMDP);
		iteration_vector_count=new ArrayList<Integer>();
		total_lp_time=0;
	}
	
	public void registerLp(long iTime) {
		total_lp_time+=iTime;
	}
	
	/*public int register(long iTime,int nVects) {
		iteration_vector_count.add(new Integer(nVects));
		register(iTime);
		return(iterations);
	}*/
	
	public String toString(){
		String retval=super.toString();
		retval+=      "last vector count  = ";
		Integer i=iteration_vector_count.get(iteration_vector_count.size()-1);
		retval+=i + "\n";
		return retval;
	}
	
	public ArrayList<Integer> iteration_vector_count;
}
