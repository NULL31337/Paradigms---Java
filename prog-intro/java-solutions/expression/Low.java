package expression;

public class Low extends AbstractUnaryOperation {
    public Low (AllExpressions a){
        super(a);
    }

    @Override
    public double operate(double a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int operate(int a) {
        int answer = 1;
        while (answer != Integer.MIN_VALUE) {
            if ((answer & a) == answer) {
                return answer;
            }
            answer <<= 1;
        }
        return  0;
    }

    @Override
    public String getOperator(){
        return " low ";
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
