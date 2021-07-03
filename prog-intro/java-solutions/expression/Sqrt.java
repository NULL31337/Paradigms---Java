package expression;

import expression.exceptions.LessThanZeroExeption;

public class Sqrt extends AbstractUnaryOperation{
    public Sqrt(AllExpressions first) {
        super(first);
    }

    @Override
    public  String getOperator() {
        return " sqrt ";
    }

    @Override
    public int getPriority(){
        return Integer.MIN_VALUE;
    }

    @Override
    public double operate(double a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int operate(int a){
        return getAnswer(a);
    }

    public static int getAnswer(int a){
        if (a < 0) {
            throw new LessThanZeroExeption("sqrt " + a);
        }
        return (int)Math.sqrt(a);
    }

    @Override
    public boolean orderRequired() {
        return true;
    }
}
