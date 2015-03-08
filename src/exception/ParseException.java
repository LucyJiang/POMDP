package exception;

/**
 * Created by LeoDong on 07/03/2015.
 */
public class ParseException extends RuntimeException {
    public ParseException(int line, String str, String type) {
        super("[Line "+line+"]: \""+str+"\" "+type);
    }
}
