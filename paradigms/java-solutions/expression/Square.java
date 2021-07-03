package expression;

import expression.operate.OperatorType;

public class Square<T> extends AbstractUnaryOperation<T> {
    public Square(AllExpressionGeneric<T> a, OperatorType<T> type){
        super(a, type);
    }

    @Override
    public T operate(T a) {
        return type.multiply(a, a);
    }

    @Override
    public String getOperator(){
        return " square ";
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
