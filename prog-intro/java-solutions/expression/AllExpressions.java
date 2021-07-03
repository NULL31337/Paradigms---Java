package expression;

public interface AllExpressions extends DoubleExpression, TripleExpression, Expression {
    void toString(StringBuilder stringBuilder);

    void toMiniString(StringBuilder stringBuilder);

    int getPriority();

    boolean orderRequired();
}
