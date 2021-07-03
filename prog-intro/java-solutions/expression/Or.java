package expression;

public class Or extends AbstractBinaryOperation {
    public Or (AllExpressions first, AllExpressions second){
        super(first, second);
    }

    @Override
    public double operate(double a, double b) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int operate(int a, int b) {
        return a | b;
    }

    @Override
    public String getOperator(){
        return " | ";
    }

    @Override
    public int getPriority() {
        return -3;
    }

    @Override
    public boolean orderRequired() {
        return false;
    }
}
