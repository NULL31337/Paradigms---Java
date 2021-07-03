package expression.operate;

public class OperateDouble implements OperatorType<Double> {
    @Override
    public Double add(Double a, Double b) {
        return a + b;
    }

    @Override
    public Double divide(Double a, Double b) {
        return a / b;
    }

    @Override
    public Double multiply(Double a, Double b) {
        return a * b;
    }

    @Override
    public Double negate(Double a) {
        return -a;
    }

    @Override
    public Double subtract(Double a, Double b) {
        return a - b;
    }

    @Override
    public Double convert(int a) {
        return (double)a;
    }

    @Override
    public Double convert(String a) {
        return Double.parseDouble(a);
    }

    @Override
    public Double abs(Double a) {
        return Math.abs(a);
    }

    @Override
    public Double mod(Double a, Double b) {
        return a % b;
    }
}
