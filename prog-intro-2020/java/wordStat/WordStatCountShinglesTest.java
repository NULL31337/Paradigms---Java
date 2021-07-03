package wordStat;

import base.Pair;

import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class WordStatCountShinglesTest extends WordStatCountTest {
    public WordStatCountShinglesTest(final String className) {
        super(className);
    }

    public static void main(final String... args) {
        new WordStatCountShinglesTest("WordStatCountShingles").run();
    }

    // Stream "magic" code. You do not expected to understand it
    protected Stream<Pair<String, Integer>> answer(final Stream<String> input) {
        return super.answer(input.flatMap(s -> IntStream.rangeClosed(0, s.length() - 3)
                .mapToObj(i -> s.substring(i, i + 3))));
    }
}
