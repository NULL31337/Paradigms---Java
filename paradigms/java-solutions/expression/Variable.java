package expression;

import expression.operate.OperatorType;

import java.util.Objects;

public class Variable<T> implements AllExpressionGeneric<T> {
    private final String var;
    private final OperatorType<T> type;
    public Variable (String var, OperatorType<T> type) {
        this.var = var;
        this.type = type;
    }

    @Override
    public String toString(){
        return var;
    }

    @Override
    public void toString(StringBuilder stringBuilder){
        stringBuilder.append(var);
    }

    @Override
    public void toMiniString(StringBuilder stringBuilder){
        stringBuilder.append(var);
    }

    @Override
    public String toMiniString(){
        return var;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o != null && o.getClass() == this.getClass()){
            Variable<?> aVariable = (Variable<?>)o;
            return Objects.equals(this.var, aVariable.var);
        }
        return false;
    }

    @Override
    public T evaluate(T x){
        return x;
    }


    @Override
    public T evaluate(T x, T y, T z){
        if (var.equals("x")) {
            return x;
        } else if (var.equals("y")) {
            return y;
        } else if (var.equals("z")) {
            return z;
        } else{
            throw new IllegalStateException("Unexpected value: " + var);
        }

    }
    @Override
    public int getPriority() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean orderRequired() {
        return false;
    }

    @Override
    public int hashCode() {
        return var.hashCode();
    }
}
