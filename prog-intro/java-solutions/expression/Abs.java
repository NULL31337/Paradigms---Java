package expression;

import expression.exceptions.OverflowException;

public class Abs extends AbstractUnaryOperation {
    public Abs (AllExpressions first) {
       super(first);
    }

    @Override
    public  String getOperator() {
        return " abs ";
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
    public int operate(int a) {
        return getAnswer(a);
    }

    public static int getAnswer(int a) {
        if (a == Integer.MIN_VALUE) {
            throw new OverflowException("abs " + a);
        }
        return a < 0 ? -a : a;
    }

    @Override
    public boolean orderRequired() {
        return true;
    }
}
