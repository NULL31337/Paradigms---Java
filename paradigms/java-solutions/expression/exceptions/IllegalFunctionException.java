package expression.exceptions;

import expression.parser.ParseException;

public class IllegalFunctionException extends ParseException {
    public IllegalFunctionException(String message) {
        super("Illegal function: " + message);
    }
}
