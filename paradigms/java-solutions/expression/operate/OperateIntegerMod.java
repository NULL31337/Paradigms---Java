package expression.operate;


import expression.exceptions.DivisionByZeroException;

public class OperateIntegerMod implements OperatorType<Integer> {
    private final int[] r;
    private final int mod;
    // Pre: mod 0: == Integer
    public OperateIntegerMod(int mod) {
        this.mod = mod;
        r = new int[mod];
        if (mod > 2) {
            r[1] = 1;
            for (int i = 2; i < mod; ++i) {
                r[i] = (mod - (mod / i) * r[mod % i] % mod) % mod;
            }
        }
    }
    @Override
    public Integer add(Integer a, Integer b) {
        return convert(a + b);
    }

    @Override
    public Integer divide(Integer a, Integer b) {
        if (b == 0) {
            throw new DivisionByZeroException();
        }
        if (mod == 0) {
            return a / b;
        }
        return multiply(a, r[b]);
    }

    @Override
    public Integer multiply(Integer a, Integer b) {
        return convert(a * b);
    }

    @Override
    public Integer negate(Integer a) {
        return -a;
    }

    @Override
    public Integer subtract(Integer a, Integer b) {
        return convert(a - b);
    }

    @Override
    public Integer convert(int a) {
        if (mod == 0) {
            return  a;
        }
        return ((a % mod) + mod) % mod;
    }

    @Override
    public Integer convert(String a) {
        return convert(Integer.parseInt(a));
    }

    @Override
    public Integer abs(Integer a) {
        return convert(Math.abs(a));
    }

    @Override
    public Integer mod(Integer a, Integer b) {
        return convert(a % b);
    }
}
