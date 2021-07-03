package expression;

import expression.operate.OperatorType;

import java.util.Objects;

public class Const<T> implements AllExpressionGeneric<T> {
    private final T number;
    public Const (String number, OperatorType<T> type) {
        this.number = type.convert(number);
    }

    @Override
    public T evaluate(T x, T y, T z) {
        return number;
    }

    @Override
    public T evaluate(T x) {
        return number;
    }

    @Override
    public String toString() {
            return number.toString();
    }

    @Override
    public void toString(StringBuilder stringBuilder){
        stringBuilder.append(number.toString());
    }

    @Override
    public void toMiniString(StringBuilder stringBuilder) {
        stringBuilder.append(number.toString());
    }

    @Override
    public String toMiniString() {
        return number.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Const<?> aConst = (Const<?>) o;
        return Objects.equals(number, aConst.number);
    }

    @Override
    public int hashCode() {
        return number.hashCode();
    }

    @Override
    public int getPriority() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean orderRequired() {
        return false;
    }
}
