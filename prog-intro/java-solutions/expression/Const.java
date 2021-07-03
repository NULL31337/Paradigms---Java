package expression;

import java.util.Objects;

public class Const implements AllExpressions {
    private final Number number;
    public Const (Number number) {
        this.number = number;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return this.number.intValue();
    }

    @Override
    public int evaluate(int x) {
        return this.number.intValue();
    }

    @Override
    public double evaluate(double x) {
        return this.number.doubleValue();
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
        Const aConst = (Const) o;
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
