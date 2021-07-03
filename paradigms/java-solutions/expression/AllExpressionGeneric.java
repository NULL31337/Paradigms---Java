package expression;

public interface AllExpressionGeneric<T> extends ExpressionGeneric<T>, TripleExpressionGeneric<T> {
    void toString(StringBuilder stringBuilder);

    void toMiniString(StringBuilder stringBuilder);

    int getPriority();

    boolean orderRequired();
}
