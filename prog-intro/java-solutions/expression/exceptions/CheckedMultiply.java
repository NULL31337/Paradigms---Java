package expression.exceptions;

import expression.Abs;
import expression.AllExpressions;
import expression.Multiply;

public class CheckedMultiply extends Multiply {
    public CheckedMultiply (AllExpressions first, AllExpressions second){
        super(first, second);
    }

    @Override
    public int operate(int a, int b) {
       return getAnswer(a, b);
    }

    public static int getAnswer(int a, int b) {
        if (a != 0 && b != 0) {
            if ((a == Integer.MIN_VALUE && b != 1) || (b == Integer.MIN_VALUE && a != 1)) {
                throw new OverflowException(a + " * " + b);
            } else if (a * b == Integer.MIN_VALUE && Integer.MIN_VALUE / b == a) {
                return a * b;
            } else if (Integer.MAX_VALUE / Abs.getAnswer(a) < Abs.getAnswer(b)) {
                throw new OverflowException(a + " * " + b);
            }
        }
        return a * b;
    }
}
