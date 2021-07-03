package expression;

import java.util.Objects;

public class Variable implements AllExpressions {
    private final String var;
    public Variable (String var) {
        this.var = var;
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
            Variable aVariable = (Variable)o;
            return Objects.equals(this.var, aVariable.var);
        }
        return false;
    }

    @Override
    public int evaluate(int x){
        return x;
    }

    @Override
    public double evaluate(double x){
        return x;
    }

    @Override
    public int evaluate(int x, int y, int z){
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
