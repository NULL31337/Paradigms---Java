package expression;

public class Negate extends AbstractUnaryOperation {
    public Negate(AllExpressions a){
        super(a);
    }

    @Override
    public double operate(double a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int operate(int a) {
        return -a;
    }

    @Override
    public String getOperator(){
        return " -";
    }

    @Override
    public int getPriority() {
        return Integer.MIN_VALUE;
    }

    @Override
    public boolean orderRequired() {
        return true;
    }
}
