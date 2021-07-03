package expression.exceptions;

public class LessThanZeroExeption extends EvaluateExeption{
    public LessThanZeroExeption(String message) {
        super("less than zero: " + message);
    }

    public LessThanZeroExeption() {
        this("");
    }
}
