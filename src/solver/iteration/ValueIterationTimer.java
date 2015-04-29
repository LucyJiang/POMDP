package solver.iteration;

import solver.util.Timer;

import java.util.ArrayList;

public class ValueIterationTimer extends Timer {

    public long total_lp_time;
    public ArrayList<Integer> vectorCounter;

    public ValueIterationTimer() {
        vectorCounter = new ArrayList<Integer>();
        total_lp_time = 0;
    }

    public void recordVectorCount(int vc) {
        this.vectorCounter.add(vc);
    }

    public ArrayList<Integer> getVectorCounter() {
        return vectorCounter;
    }

    public void registerLp(long iTime) {
        total_lp_time += iTime;
    }

    public String toString() {
        String ret = super.toString();
        ret += "last vector count  = ";
        Integer i = vectorCounter.get(vectorCounter.size() - 1);
        ret += i + "\n";
        return ret;
    }


}
