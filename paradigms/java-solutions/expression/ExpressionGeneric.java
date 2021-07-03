package expression;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public strictfp interface ExpressionGeneric<T> extends ToMiniStringGeneric {
    T evaluate(T x);
}