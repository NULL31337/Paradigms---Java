package expression.exceptions;

import expression.AllExpressions;
import expression.Divide;

public class CheckedDivide extends Divide {
    public CheckedDivide (AllExpressions first, AllExpressions second) {
        super(first, second);
    }

    @Override
    public int operate(int left, int right) {
        return getAnswer(left, right);
    }

    public static int getAnswer(int a, int b) {
        if (b == 0) {
            throw new DivisionByZeroException(a + " / " + b);
        }
        if (a == Integer.MIN_VALUE && b == -1) {
            throw new OverflowException(a + " / " + b);
        }
        return a / b;
    }
}
