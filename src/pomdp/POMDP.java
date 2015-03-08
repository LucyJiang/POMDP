package pomdp;

import exception.InconsistantException;
import exception.ParseException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class POMDP {
	
	private StateSet stateSet;
    private ActionSet actionSet;
    private double discountFactor;

    private static final int OBSERVATION_PRECISION = 4;//0.xxx
	
	private POMDP(StateSet ss, ActionSet as, double discountFactor){
		this.stateSet = ss;
		this.actionSet = as;
        this.discountFactor =discountFactor;
	}

	public static POMDP generateRandomly(/*constraints*/){
		
		return null;
	}


	public static POMDP importFromFile(String filename){
		File file = new File(filename);
        BufferedReader reader = null;
        StateSet stateSet  = new StateSet();
        ActionSet actionSet = new ActionSet();
        double discountFactor = 0;
        TreeMap<Integer,String> statesString = new TreeMap<Integer,String>();
        TreeMap<Integer,String> actionsString = new TreeMap<Integer,String>();
        TreeMap<Integer,String> dfString = new TreeMap<Integer,String>();
        try{
            reader = new BufferedReader(new FileReader(file));
            String tmpString = null;
            int line =1;
            while((tmpString = reader.readLine())!=null){
                tmpString = tmpString.trim();
                if(!tmpString.equals("")){
                    switch (tmpString.charAt(0)){
                        case '#':
                            break;
                        case '!':
                            statesString.put(line, tmpString);
                            break;
                        case '@':
                            actionsString.put(line, tmpString);
                            break;
                        case '%':
                            dfString.put(line, tmpString);
                            break;
                        default:
                            throw new ParseException(line,tmpString,"Unknown Syntax");
                    }
                }
                line++;
            }
        }catch (IOException ioe){
            ioe.printStackTrace();
        }finally {
            if (reader != null){
                try{
                    reader.close();
                }catch (IOException ioe){

                }
            }
        }
        /* States */
        List<State> needRandomObservation = new LinkedList<State>();
        int totalObservation = (int) Math.pow(10, OBSERVATION_PRECISION);
        for (Map.Entry<Integer,String> m : statesString.entrySet()){
            String cmd = m.getValue();
            Integer line = m.getKey();
            String[] cmds = cmd.split("\\s+");
            if (cmds.length < 2 || cmds.length >3){
                throw new ParseException(line,cmd,"Syntax Error");

            }else if(cmds.length == 2){
                State s = new State(cmds[1],0);
                stateSet.addState(cmds[1],s);
                needRandomObservation.add(s);
            }else{
                //length == 3
                try {
                    if(cmds[2].length()-cmds[2].lastIndexOf('.')>
                       OBSERVATION_PRECISION){
                        throw new ParseException(line,cmd,"Illegal Observation Precision, which should be "+
                                                          OBSERVATION_PRECISION);
                    }
                    double ob = Double.parseDouble(cmds[2]);
                    State s = new State(cmds[1],ob);
                    stateSet.addState(cmds[1],s);

                    totalObservation = totalObservation - (int)(ob * Math.pow(10,
                                                                              OBSERVATION_PRECISION));
                    if(totalObservation<0){
                        throw new InconsistantException("The total Observation should not be more than 1");
                    }

                }catch (NumberFormatException nfe){
                    throw new ParseException(line,cmd,"Illegal Observation");
                }catch (InconsistantException ie) {
                    throw new ParseException(line,
                                             cmd,
                                             "Inconsistant:" + ie.getMessage());
                }
            }
        }
        int sizeOfNeedRandom = needRandomObservation.size();
        for(int i = 0;i<sizeOfNeedRandom;i++){
            State current = needRandomObservation.get(i);
            if(i==sizeOfNeedRandom-1){
                current.setObservation(totalObservation/(Math.pow(10.0,
                                                                  OBSERVATION_PRECISION)));
                totalObservation = totalObservation - (int)(current.getObservation()*(Math.pow(10.0,
                                                                                               OBSERVATION_PRECISION)));
            }else{
                double randvalue = Math.floor(Math.random()*totalObservation);
                current.setObservation(randvalue/Math.pow(10.0,
                                                          OBSERVATION_PRECISION));
                totalObservation = totalObservation-(int)randvalue;
            }
        }
        if(totalObservation!= 0){
            throw new InconsistantException("The total Obeservation should be 1.");
        }

        /* Actions */
        for (Map.Entry<Integer,String> m : actionsString.entrySet()){
            String cmd = m.getValue();
            Integer line = m.getKey();
            String[] cmds = cmd.split("\\s+");
            if (cmds.length != 5 ){
                throw new ParseException(line,cmd,"Syntax Error");

            }else{
                //length == 4
                try {
                    double reward = Double.parseDouble(cmds[4]);
                    State from = stateSet.getState(cmds[2]);
                    if(from==null){
                        throw new ParseException(line,cmd,"Unknow from State:"+cmds[2]);
                    }
                    State to = stateSet.getState(cmds[3]);
                    if(to==null){
                        throw new ParseException(line,cmd,"Unknow to State:"+cmds[3]);
                    }
                    Action a = new Action(cmds[1],from, to, reward);
                    actionSet.addAction(a.getId(), a.consistent());

                }catch (NumberFormatException nfe){
                    throw new ParseException(line,cmd,"Illegal Observation");
                }catch (InconsistantException ie) {
                    throw new ParseException(line,
                                             cmd,
                                             "Inconsistant:" + ie.getMessage());
                }
            }
        }

        /* Discount Factor */
        if(dfString.size()!=1){
            throw new InconsistantException("Too many discount factor declarations on lines:"+dfString.keySet());
        }else{
            //length == 1
            for (Map.Entry<Integer,String> m : dfString.entrySet()){
                String cmd = m.getValue();
                Integer line = m.getKey();
                String[] cmds = cmd.split("\\s+");
                if (cmds.length != 2 ){
                    throw new ParseException(line,cmd,"Syntax Error");

                }else{
                    //length == 2
                    try {
                        double value = Double.parseDouble(cmds[1]);
                        if(value<=0||value>=1){
                            throw new ParseException(line,cmd,"Discount Factor should between 0 and 1");
                        }
                        discountFactor = value;

                    }catch (NumberFormatException nfe){
                        throw new ParseException(line,cmd,"Illegal Discount Factor");
                    }
                }
            }
        }
        return new POMDP(stateSet,actionSet,discountFactor);
	}

    //TODO LucyJiang finish this function.
//	public static POMDP phraseString(String str){
//        // add states
//        StateSet states = new StateSet();
//        states.addState("1", new State("1",0.2));
//        states.addState("2", new State("2",0.5));
//        System.out.println(states.getState("1"));
//        System.out.println(states.getState("2"));
//        new Action("a1",states.getState("1"), states.getState("2"),20).consistent();
//        // add actions
//		return new POMDP(states,0.2);
//	}

    public double getDiscountFactor() {
        return discountFactor;
    }

    public StateSet getStateSet(){
		return this.stateSet;
	}

    public ActionSet getActionSet(){
        return this.actionSet;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (State s : getStateSet().values()){
            sb.append(s).append("\n");
        }
        for (Action a : getActionSet().values()){
            sb.append(a).append("\n");
        }
        sb.append("[%] "+getDiscountFactor());
        return sb.toString();
    }
}
