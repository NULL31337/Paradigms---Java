package expression.exceptions;

public class DivisionByZeroException extends EvaluateExeption {
    public DivisionByZeroException(String message) {
        super("division by zero: " + message);
    }

    public DivisionByZeroException() {
        this("");
    }
}
