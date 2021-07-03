package expression;

public class Multiply extends AbstractBinaryOperation {
    public Multiply (AllExpressions first, AllExpressions second){
        super(first, second);
    }

    @Override
    public double operate(double a, double b) {
        return a * b;
    }

    @Override
    public int operate(int a, int b) {
        return a * b;
    }

    @Override
    public String getOperator(){
        return " * ";
    }

    @Override
    public int getPriority(){
        return 1;
    }

    @Override
    public boolean orderRequired() {
        return false;
    }
}
