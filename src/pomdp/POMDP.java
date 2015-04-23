package pomdp;

import exception.InconsistentException;
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
    private int numberOfAction;

    private static final int OBSERVATION_PRECISION = 3;//0.xxx
	
	private POMDP(StateSet ss, ActionSet as, double discountFactor){
		this.stateSet = ss;
		this.actionSet = as;
        this.discountFactor =discountFactor;
	}

    public double getDiscountFactor() {
        return discountFactor;
    }

    public StateSet getStateSet(){
        return this.stateSet;
    }

    public ActionSet getActionSet(){
        return this.actionSet;
    }

    public State getState(String id) {
        return stateSet.getState(id);
    }

    public boolean addState(String id, State state) {
        return stateSet.addState(id, state);
    }

    public boolean containsState(String id) {
        return stateSet.contains(id);
    }

    public Collection<State> states() {
        return stateSet.states();
    }

    public Set<String> statesIds() {
        return stateSet.ids();
    }

    public Set<Map.Entry<String, State>> statePairs() {
        return stateSet.pairs();
    }

    public boolean addAction(String id, Action action) {
        return actionSet.addAction(id, action);
    }

    public boolean containsAction(String id) {
        return actionSet.contains(id);
    }

    public Collection<Action> actions() {
        return actionSet.actions();
    }

    public Set<Map.Entry<String, Action>> actionPairs() {
        return actionSet.pairs();
    }

    public Set<String> actionIds() {
        return actionSet.ids();
    }

    @Override
    public String toString() {
        return "=====STATE SET=====\n"+
               stateSet+"\n"+
               "=====ACTION SET====\n"+
               actionSet+"\n"+
               "========DF=========\n"+
               "[%] DF\t: "+discountFactor;
    }

    public static class Factory{
        public static POMDP generateRandomly(int noStates){
            //TODO
            return null;
        }
        public static POMDP createFromFile(String filename){
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
            int restObservation = (int) Math.pow(10, OBSERVATION_PRECISION);
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
                        if(cmds[2].length()-cmds[2].lastIndexOf('.')-1>
                           OBSERVATION_PRECISION){
                            throw new ParseException(line,cmd,"Illegal Observation Precision, which should be "+
                                                              OBSERVATION_PRECISION);
                        }
                        double ob = Double.parseDouble(cmds[2]);
                        State s = new State(cmds[1],ob);
                        stateSet.addState(cmds[1],s);

                        restObservation = restObservation - (int)(ob * Math.pow(10,
                                                                                  OBSERVATION_PRECISION));
                        if(restObservation<0){
                            throw new InconsistentException("The total Observation should not be more than 1");
                        }

                    }catch (NumberFormatException nfe){
                        throw new ParseException(line,cmd,"Illegal Observation");
                    }catch (InconsistentException ie) {
                        throw new ParseException(line,
                                                 cmd,
                                                 "Inconsistent:" + ie.getMessage());
                    }
                }
            }
            int sizeOfNeedRandom = needRandomObservation.size();
            for(int i = 0;i<sizeOfNeedRandom;i++){
                State current = needRandomObservation.get(i);
                if(i==sizeOfNeedRandom-1){
                    current.setObservation(restObservation/(Math.pow(10.0,
                                                                      OBSERVATION_PRECISION)));
                    restObservation = 0;
                }else{
                    double randvalue = Math.floor(Math.random()*restObservation);
                    current.setObservation(randvalue/Math.pow(10.0,
                                                              OBSERVATION_PRECISION));
                    restObservation = restObservation-(int)randvalue;
                }
            }
            if(restObservation!= 0){
                throw new InconsistentException("The total Observation should be 1.");
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
                            throw new ParseException(line,cmd,"Unknown from State:"+cmds[2]);
                        }
                        State to = stateSet.getState(cmds[3]);
                        if(to==null){
                            throw new ParseException(line,cmd,"Unknown to State:"+cmds[3]);
                        }
                        Action a = new Action(cmds[1],from, to, reward);
                        actionSet.addAction(a.getId(), a);

                    }catch (NumberFormatException nfe){
                        throw new ParseException(line,cmd,"Illegal Observation");
                    }catch (InconsistentException ie) {
                        throw new ParseException(line,
                                                 cmd,
                                                 "Inconsistant:" + ie.getMessage());
                    }
                }
            }

            /* Discount Factor */
            if(dfString.size()!=1){
                throw new InconsistentException("Too many discount factor declarations on lines:"+dfString.keySet());
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

        //TODO
        //	public static POMDP createFromString(String str){
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

    }


}
