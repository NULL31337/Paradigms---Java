package expression.operate;

import java.math.BigInteger;

public class OperateBigInteger implements OperatorType<BigInteger> {
    @Override
    public BigInteger add(BigInteger a, BigInteger b) {
        return a.add(b);
    }

    @Override
    public BigInteger divide(BigInteger a, BigInteger b) {
        return a.divide(b);
    }

    @Override
    public BigInteger multiply(BigInteger a, BigInteger b) {
        return a.multiply(b);
    }

    @Override
    public BigInteger negate(BigInteger a) {
        return a.negate();
    }

    @Override
    public BigInteger subtract(BigInteger a, BigInteger b) {
        return a.subtract(b);
    }

    @Override
    public BigInteger convert(int a) {
        return BigInteger.valueOf(a);
    }

    @Override
    public BigInteger convert(String a) {
        return new BigInteger(a);
    }

    @Override
    public BigInteger abs(BigInteger a) {
        return a.abs();
    }

    @Override
    public BigInteger mod(BigInteger a, BigInteger b) {
        return a.mod(b);
    }
}
