package expression;


public class Gcd extends AbstractBinaryOperation {
    public Gcd (AllExpressions first, AllExpressions second) {
        super(first, second);
    }

    public static int getAnswer(int a, int b) {
        while (b != 0) {
            a %= b;
            int tmp = a;
            a = b;
            b = tmp;
        }
        return Abs.getAnswer(a);
    }

    @Override
    public double operate(double a, double b) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int operate(int a, int b) {
        return getAnswer(a, b);
    }

    @Override
    public String getOperator(){
        return " gcd ";
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
