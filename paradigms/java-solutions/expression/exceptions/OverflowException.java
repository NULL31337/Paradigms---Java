package expression.exceptions;

public class OverflowException extends EvaluateExeption {
    public OverflowException(String message) {
        super("overflow: " + message);
    }

    public OverflowException() {
        this("");
    }
}
