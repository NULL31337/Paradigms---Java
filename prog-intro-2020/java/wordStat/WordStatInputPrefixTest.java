package wordStat;

import base.Pair;

import java.util.stream.Stream;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class WordStatInputPrefixTest extends WordStatInputTest {
    public WordStatInputPrefixTest(final String className) {
        super(className);
    }

    public static void main(final String... args) {
        new WordStatInputPrefixTest("WordStatInputPrefix").run();
    }

    // Stream "magic" code. You do not expected to understand it
    protected Stream<Pair<String, Integer>> answer(final Stream<String> input) {
        return super.answer(input.flatMap(s -> s.length() >= 3 ? Stream.of(s.substring(0, 3)) : Stream.empty()));
    }

}
