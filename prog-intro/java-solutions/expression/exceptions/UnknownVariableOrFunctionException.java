package expression.exceptions;

import expression.parser.ParseException;

public class UnknownVariableOrFunctionException extends ParseException {
    public UnknownVariableOrFunctionException(String message) {
        super("Unknown variable or function: " + message);
    }
}
