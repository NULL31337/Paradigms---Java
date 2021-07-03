package expression.operate;


import expression.exceptions.DivisionByZeroException;
import expression.exceptions.OverflowException;

public class OperateCheckedInt extends OperateIntegerMod {

    public OperateCheckedInt() {
        super(0);
    }

    @Override
    public Integer add(Integer a, Integer b) {
        if (b < 0 && Integer.MIN_VALUE - b > a) {
            throw new OverflowException(a + " + " + b);
        } else if (b > 0 && Integer.MAX_VALUE - b < a) {
            throw new OverflowException(a + " + " + b);
        }
        return super.add(a, b);
    }

    @Override
    public Integer divide(Integer a, Integer b) {
        if (a == Integer.MIN_VALUE && b == -1) {
            throw new OverflowException(a + " / " + b);
        }
        return super.divide(a, b);
    }

    @Override
    public Integer multiply(Integer a, Integer b) {
        if (a != 0 && b != 0) {
            if ((a == Integer.MIN_VALUE && b != 1) || (b == Integer.MIN_VALUE && a != 1)) {
                throw new OverflowException(a + " * " + b);
            } else if (a * b == Integer.MIN_VALUE && Integer.MIN_VALUE / b == a) {
                return a * b;
            } else if (Integer.MAX_VALUE / abs(a) < abs(b)) {
                throw new OverflowException(a + " * " + b);
            }
        }
        return super.multiply(a, b);
    }

    @Override
    public Integer negate(Integer a) {
        if (a == Integer.MIN_VALUE) {
            throw new OverflowException("-" + a);
        }
        return super.negate(a);
    }

    @Override
    public Integer subtract(Integer a, Integer b) {
        if (b < 0 && Integer.MAX_VALUE + b < a) {
            throw new OverflowException(a + " - " + b);
        } else if (b > 0 && Integer.MIN_VALUE + b > a) {
            throw new OverflowException(a + " - " + b);
        }
        return super.subtract(a, b);
    }


    @Override
    public Integer abs(Integer a) {
        if (a == Integer.MIN_VALUE) {
            throw new OverflowException("abs " + a);
        }
        return super.abs(a);
    }
}
