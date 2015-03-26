package exception;

/**
 *
 */
public class ParseException extends RuntimeException {
    public ParseException(int line, String str, String type) {
        super("[Line "+line+"]: \""+str+"\" "+type);
    }
}
