package sum;

import java.util.List;
import java.util.Random;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface SChecker {
    void test(Number result, String... input);

    void printStatus();

    Random getRandom();

    String randomString(final String chars);

    <T> T randomItem(final List<T> items);
}
