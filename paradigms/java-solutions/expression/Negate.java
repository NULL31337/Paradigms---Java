package expression;

import expression.operate.OperatorType;

public class Negate<T> extends AbstractUnaryOperation<T> {
    public Negate(AllExpressionGeneric<T> a, OperatorType<T> type){
        super(a, type);
    }

    @Override
    public T operate(T a) {
        return type.negate(a);
    }

    @Override
    public String getOperator(){
        return " -";
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
