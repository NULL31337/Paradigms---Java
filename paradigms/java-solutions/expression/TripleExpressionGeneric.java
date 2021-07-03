package expression;


/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface TripleExpressionGeneric<T> extends ToMiniStringGeneric {
    T evaluate(T x, T y, T z);
}