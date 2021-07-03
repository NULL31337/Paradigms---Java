package expression;

import expression.operate.OperatorType;

public class Multiply<T> extends AbstractBinaryOperation<T> {
    public Multiply (AllExpressionGeneric<T> first, AllExpressionGeneric<T> second, OperatorType<T> type){
        super(first, second, type);
    }

    @Override
    public T operate(T a, T b) {
        return type.multiply(a, b);
    }

    @Override
    public String getOperator(){
        return " * ";
    }

    @Override
    public int getPriority(){
        return 1;
    }

    @Override
    public boolean orderRequired() {
        return false;
    }
}
