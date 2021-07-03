package expression;

import expression.operate.OperatorType;

import java.util.Objects;

public abstract class AbstractBinaryOperation<T> implements AllExpressionGeneric<T>, ToMiniStringGeneric {
    private final AllExpressionGeneric<T> first;
    private final AllExpressionGeneric<T> second;
    protected final OperatorType<T> type;
    public AbstractBinaryOperation (AllExpressionGeneric<T> first, AllExpressionGeneric<T> second, OperatorType<T> type) {
        this.first = first;
        this.second = second;
        this.type = type;
    }

    public abstract String getOperator();

    public abstract int getPriority();

    public abstract T operate(T a, T b);


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o != null && o.getClass() == this.getClass()) {
            AbstractBinaryOperation<?> aAbstractBinaryOperation = (AbstractBinaryOperation<?>)o;
            return Objects.equals(this.first, aAbstractBinaryOperation.first)
                    && Objects.equals(this.second, aAbstractBinaryOperation.second);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second, this.getClass());
    }

    @Override
    public T evaluate(T x) {
        return operate(first.evaluate(x), second.evaluate(x));
    }

    @Override
    public T evaluate(T x, T y, T z) {
        return operate(first.evaluate(x, y, z), second.evaluate(x, y, z));
    }

    @Override
    public void toString(StringBuilder stringBuilder) {
        stringBuilder.append('(');
        first.toString(stringBuilder);
        stringBuilder.append(getOperator());
        second.toString(stringBuilder);
        stringBuilder.append(')');
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        this.toString(stringBuilder);
        return stringBuilder.toString();
    }

    @Override
    public String toMiniString() {
        StringBuilder stringBuilder = new StringBuilder();
        this.toMiniString(stringBuilder);
        return stringBuilder.toString();
    }

    @Override
    public void toMiniString(StringBuilder stringBuilder) {
        if (first.getPriority() < this.getPriority()) {
            stringBuilder.append('(');
            first.toMiniString(stringBuilder);
            stringBuilder.append(')');
        } else {
            first.toMiniString(stringBuilder);
        }
        stringBuilder.append(getOperator());
        if ((this.getPriority() == second.getPriority() && (second.orderRequired() || this.orderRequired()))
                || this.getPriority() > second.getPriority()) {
            stringBuilder.append('(');
            second.toMiniString(stringBuilder);
            stringBuilder.append(')');
        } else {
            second.toMiniString(stringBuilder);
        }
    }

}
