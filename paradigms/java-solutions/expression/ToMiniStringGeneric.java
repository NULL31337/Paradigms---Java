package expression;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface ToMiniStringGeneric {
    default String toMiniString() {
        return toString();
    }
}