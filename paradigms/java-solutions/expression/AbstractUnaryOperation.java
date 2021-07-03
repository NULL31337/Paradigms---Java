package expression;

import expression.operate.OperatorType;

import java.util.Objects;

public abstract class AbstractUnaryOperation<T> implements AllExpressionGeneric<T>, ToMiniStringGeneric {
    public AllExpressionGeneric<T> first;
    protected final OperatorType<T> type;
    public AbstractUnaryOperation (AllExpressionGeneric<T> first, OperatorType<T> type) {
        this.first = first;
        this.type = type;
    }

    public abstract String getOperator();

    public abstract int getPriority();

    public abstract T operate(T a);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o != null && o.getClass() == this.getClass()) {
            AbstractUnaryOperation<?> aAbstractUnaryOperation = (AbstractUnaryOperation<?>)o;
            return Objects.equals(this.first, aAbstractUnaryOperation.first);
        }
        return false;
    }

    @Override
    public T evaluate(T x){
        return operate(first.evaluate(x));
    }

    @Override
    public T evaluate(T x, T y, T z) {
        return operate(first.evaluate(x, y, z));
    }

    @Override
    public void toString(StringBuilder stringBuilder) {
        stringBuilder.append(this.getOperator());
        first.toString(stringBuilder);
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
        stringBuilder.append(this.getOperator());
        first.toMiniString(stringBuilder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, this.getClass());
    }
}
