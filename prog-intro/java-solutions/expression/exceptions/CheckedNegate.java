package expression.exceptions;

import expression.AllExpressions;
import expression.Negate;

public class CheckedNegate  extends Negate {
    public CheckedNegate(AllExpressions first) {
        super(first);
    }

    @Override
    public int operate(int a) {
        return getAnswer(a);
    }

    public static int getAnswer(int a) {
        if (a == Integer.MIN_VALUE) {
            throw new OverflowException("-" + a);
        }
        return -a;
    }
}
