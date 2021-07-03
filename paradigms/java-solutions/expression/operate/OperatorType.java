package expression.operate;

public interface OperatorType<T> {
    T add(T a, T b);
    T divide(T a, T b);
    T multiply(T a, T b);
    T negate(T a);
    T subtract(T a, T b);
    T convert(int a);
    T convert(String a);
    T abs(T a);
    T mod (T a, T b);
}
