package expression;

import expression.operate.OperatorType;

public class Subtract<T> extends AbstractBinaryOperation<T> {
    public Subtract (AllExpressionGeneric<T> first, AllExpressionGeneric<T> second, OperatorType<T> type){
        super(first, second, type);
    }

    @Override
    public T operate(T a, T b) {
        return type.subtract(a, b);
    }

    @Override
    public String getOperator(){
        return " - ";
    }

    @Override
    public int getPriority(){
        return 0;
    }

    @Override
    public boolean orderRequired() {
        return true;
    }
}
