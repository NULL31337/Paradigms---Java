package expression.exceptions;

import expression.Add;
import expression.AllExpressions;

public class CheckedAdd extends Add {
    public CheckedAdd (AllExpressions first, AllExpressions second){
        super(first, second);
    }

    @Override
    public int operate(int a, int b) {
        return getAnswer(a, b);
    }

    public static int getAnswer(int a, int b) {
        if (b < 0 && Integer.MIN_VALUE - b > a) {
            throw new OverflowException(a + " + " + b);
        } else if (b > 0 && Integer.MAX_VALUE - b < a) {
            throw new OverflowException(a + " + " + b);
        }
        return a + b;
    }
}
