package expression;

public class Xor extends AbstractBinaryOperation {
    public Xor (AllExpressions first, AllExpressions second){
        super(first, second);
    }

    @Override
    public double operate(double a, double b) {
        throw new IllegalArgumentException();
    }

    @Override
    public int operate(int a, int b) {
        return a ^ b;
    }

    @Override
    public String getOperator(){
        return " ^ ";
    }

    @Override
    public int getPriority() {
        return -2;
    }

    @Override
    public boolean orderRequired() {
        return false;
    }
}
