package expression;

import java.util.Objects;

public abstract class AbstractBinaryOperation implements AllExpressions {
    private final AllExpressions first;
    private final AllExpressions second;

    AbstractBinaryOperation (AllExpressions first, AllExpressions second) {
        this.first = first;
        this.second = second;
    }

    public abstract String getOperator();

    public abstract int getPriority();

    public abstract double operate(double a, double b);

    public abstract int operate(int a, int b);


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o != null && o.getClass() == this.getClass()) {
            AbstractBinaryOperation aAbstractBinaryOperation = (AbstractBinaryOperation)o;
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
    public double evaluate(double x){
        return operate(first.evaluate(x), second.evaluate(x));
    }

    @Override
    public int evaluate(int x){
        return operate(first.evaluate(x), second.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
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
