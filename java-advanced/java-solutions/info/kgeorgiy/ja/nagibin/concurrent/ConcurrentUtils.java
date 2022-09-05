package info.kgeorgiy.ja.nagibin.concurrent;

public class ConcurrentUtils {
    /**
     * Checks if the number of threads is correct
     *
     * @param threads number of threads
     * @exception IllegalArgumentException if number of threads lower or equal 0
     */
    static void checkThreadCount(int threads) {
        if (threads <= 0) {
            throw new IllegalArgumentException("The number of threads must be greater than 0");
        }
    }
}
