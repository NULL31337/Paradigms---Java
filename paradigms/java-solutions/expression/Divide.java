package expression;

import expression.operate.OperatorType;

public class Divide<T> extends AbstractBinaryOperation<T> {
    public Divide (AllExpressionGeneric<T> first, AllExpressionGeneric<T> second, OperatorType<T> type){
        super(first, second, type);
    }

    @Override
    public T operate(T a, T b) { return type.divide(a, b); }

    @Override
    public String getOperator(){
        return " / ";
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public boolean orderRequired() {
        return true;
    }
}
