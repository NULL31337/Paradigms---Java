package expression.exceptions;

public class EvaluateExeption extends RuntimeException {
    private final String message;
    public EvaluateExeption (String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
