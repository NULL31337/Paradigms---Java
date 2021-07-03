package expression.operate;


public class OperateByte implements OperatorType<Byte> {
    @Override
    public Byte add(Byte a, Byte b) {
        return (byte)(a + b);
    }

    @Override
    public Byte divide(Byte a, Byte b) {
        return (byte)(a / b);
    }

    @Override
    public Byte multiply(Byte a, Byte b) {
        return (byte)(a * b);
    }

    @Override
    public Byte negate(Byte a) {
        return (byte)(-a);
    }

    @Override
    public Byte subtract(Byte a, Byte b) {
        return (byte)(a - b);
    }

    @Override
    public Byte convert(int a) {
        return (byte)a;
    }

    @Override
    public Byte convert(String a) {
        return Byte.parseByte(a);
    }

    @Override
    public Byte abs(Byte a) {
        return (byte)(Math.abs(a));
    }

    @Override
    public Byte mod(Byte a, Byte b) {
        return (byte)(a % b);
    }
}
