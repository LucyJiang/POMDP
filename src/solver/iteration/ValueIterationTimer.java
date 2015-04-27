package solver.iteration;

import java.util.ArrayList;

public class ValueIterationTimer extends Timer {
	
	public long total_lp_time;
	
	public ValueIterationTimer(){
		iteration_vector_count=new ArrayList<Integer>();
		total_lp_time=0;
	}
	
	public void registerLp(long iTime) {
		total_lp_time+=iTime;
	}
	
	/*public int writeTimeRecords(long iTime,int nVects) {
		iteration_vector_count.add(new Integer(nVects));
		writeTimeRecords(iTime);
		return(iterCounter);
	}*/
	
	public String toString(){
		String ret=super.toString();
		ret+=      "last vector count  = ";
		Integer i=iteration_vector_count.get(iteration_vector_count.size()-1);
		ret+=i + "\n";
		return ret;
	}
	
	public ArrayList<Integer> iteration_vector_count;
}
