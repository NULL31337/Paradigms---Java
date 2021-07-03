package expression;

import expression.operate.OperatorType;

public class Mod<T> extends AbstractBinaryOperation<T> {
    public Mod (AllExpressionGeneric<T> first, AllExpressionGeneric<T> second, OperatorType<T> type){
        super(first, second, type);
    }

    @Override
    public T operate(T a, T b) {
        return type.mod(a, b);
    }

    @Override
    public String getOperator(){
        return " mod ";
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
