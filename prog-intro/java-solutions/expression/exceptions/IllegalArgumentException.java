package expression.exceptions;

import expression.parser.ParseException;

public class IllegalArgumentException extends ParseException {
    public IllegalArgumentException() {
        super("Illegal argument");
    }

    public IllegalArgumentException(String input) {
        super(input);
    }
}
