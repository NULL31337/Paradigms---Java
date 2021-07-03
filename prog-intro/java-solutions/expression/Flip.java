package expression;

public class Flip extends AbstractUnaryOperation {

    public Flip (AllExpressions a){
        super(a);
    }

    @Override
    public double operate(double a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int operate(int a) {
        int result = 0;
        while (a != 0) {
            result <<= 1;
            result += a & 1;
            a >>>= 1;
        }
        return  result;
    }

    @Override
    public String getOperator(){
        return " flip ";
    }

    @Override
    public int getPriority() {
        return Integer.MIN_VALUE;
    }

    @Override
    public boolean orderRequired() {
        return true;
    }
}
