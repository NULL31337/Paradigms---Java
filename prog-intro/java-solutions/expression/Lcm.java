package expression;

import expression.exceptions.CheckedDivide;
import expression.exceptions.CheckedMultiply;

public class Lcm extends AbstractBinaryOperation {
    public Lcm (AllExpressions first, AllExpressions second) {
        super(first, second);
    }

    @Override
    public double operate(double a, double b) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int operate(int a, int b) {
        return getAnswer(a, b);
    }

    public static int getAnswer(int a, int b) {
        if (a == 0 || b == 0) {
            return 0;
        }
        return CheckedMultiply.getAnswer(CheckedDivide.getAnswer(a, Gcd.getAnswer(a, b)), b);
    }

    @Override
    public String getOperator(){
        return " lcm ";
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
