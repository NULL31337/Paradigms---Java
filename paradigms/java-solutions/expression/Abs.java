package expression;

import expression.operate.OperatorType;

public class Abs<T> extends AbstractUnaryOperation<T> {
    public Abs(AllExpressionGeneric<T> a, OperatorType<T> type){
        super(a, type);
    }

    @Override
    public T operate(T a) {
        return type.abs(a);
    }

    @Override
    public String getOperator(){
        return " abs ";
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
