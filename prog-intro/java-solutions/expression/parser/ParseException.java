package expression.parser;

public class ParseException extends RuntimeException {
    public ParseException(final String message) {
        super(message);
    }
}