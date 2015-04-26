package algorithm;

/**
*
*/
public class HistoryRecord {
    private String S; //t
    private String O; //t
    private String A; //t-1


    public HistoryRecord(String s, String o, String a) {
        S = s;
        O = o;
        A = a;
    }

    public static HistoryRecord consume(HistoryRecord prev){
        //TODO
//      return new HistoryRecord(null,null,0,0);
        return null;
    }
}
