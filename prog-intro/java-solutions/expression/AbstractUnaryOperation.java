package expression;

import java.util.Objects;

public abstract class AbstractUnaryOperation implements AllExpressions {
    public AllExpressions first;

    AbstractUnaryOperation (AllExpressions first) {
        this.first = first;
    }

    public abstract String getOperator();

    public abstract int getPriority();

    public abstract double operate(double a);

    public abstract int operate(int a);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o != null && o.getClass() == this.getClass()) {
            AbstractUnaryOperation aAbstractUnaryOperation = (AbstractUnaryOperation)o;
            return Objects.equals(this.first, aAbstractUnaryOperation.first);
        }
        return false;
    }

    @Override
    public double evaluate(double x){
        return operate(first.evaluate(x));
    }

    @Override
    public int evaluate(int x){
        return operate(first.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
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
