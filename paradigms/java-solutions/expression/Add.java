package expression;

import expression.operate.OperatorType;

public class Add<T> extends AbstractBinaryOperation<T> {
    public Add (AllExpressionGeneric<T> first, AllExpressionGeneric<T> second, OperatorType<T> type){
        super(first, second, type);
    }

    @Override
    public String getOperator() {
        return " + ";
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public T operate(T a, T b) {
        return type.add(a, b);
    }

    @Override
    public boolean orderRequired() {
        return false;
    }
}
