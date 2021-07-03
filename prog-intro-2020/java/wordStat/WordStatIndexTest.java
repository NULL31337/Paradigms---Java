package wordStat;

import base.Pair;
import base.Randomized;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class WordStatIndexTest {
    protected final WordStatIndexChecker checker;
    protected final Function<String[][], List<Pair<String, String>>> processor;

    public WordStatIndexTest(final String className, final Function<String[][], List<Pair<String, String>>> processor) {
        checker = new WordStatIndexChecker(className);
        this.processor = processor;
    }

    public static void main(final String... args) {
        test("WordStatIndex", WordStatIndexTest::answer);
    }

    protected static void test(final String className, final Function<String[][], List<Pair<String, String>>> processor) {
        new WordStatIndexTest(className, processor).run();
    }

    // Stream "magic" code. You do not expected to understand it
    private static List<Pair<String, String>> answer(final String[][] text) {
        final List<String> all = Arrays.stream(text)
                .flatMap(Arrays::stream)
                .map(String::toLowerCase)
                .collect(Collectors.toList());
        return IntStream.range(0, all.size()).boxed()
                .collect(Collectors.groupingBy(all::get, LinkedHashMap::new, Collectors.toList())).entrySet().stream()
                .map(e -> Pair.of(e.getKey(), e.getValue().size() + " " + e.getValue().stream().map(i -> i + 1 + "").collect(Collectors.joining(" "))))
                .collect(Collectors.toList());
    }

    protected void run() {
        test();
        checker.printStatus();
    }

    private void test() {
        testPP(
                "To be, or not to be, that is the question:");
        testPP(
                "Monday's child is fair of face.",
                "Tuesday's child is full of grace.");
        testPP(
                "Шалтай-Болтай",
                "Сидел на стене.",
                "Шалтай-Болтай",
                "Свалился во сне."
        );

        randomTest(3, 10, 10, 3, Randomized.ENGLISH, WordStatChecker.SIMPLE_DELIMITERS);
        randomTest(10, 3, 5, 5, Randomized.RUSSIAN, WordStatChecker.SIMPLE_DELIMITERS);
        randomTest(3, 10, 10, 3, Randomized.GREEK, WordStatChecker.SIMPLE_DELIMITERS);
        randomTest(3, 10, 10, 3, WordStatChecker.DASH, WordStatChecker.SIMPLE_DELIMITERS);
        randomTest(3, 10, 10, 3, Randomized.ENGLISH, WordStatChecker.ADVANCED_DELIMITERS);
        randomTest(10, 3, 5, 5, Randomized.RUSSIAN, WordStatChecker.ADVANCED_DELIMITERS);
        randomTest(3, 10, 10, 3, Randomized.GREEK, WordStatChecker.ADVANCED_DELIMITERS);
        randomTest(3, 10, 10, 3, WordStatChecker.DASH, WordStatChecker.ADVANCED_DELIMITERS);
        randomTest(100, 1000, 1000, 1000, WordStatChecker.ALL, WordStatChecker.ADVANCED_DELIMITERS);
    }

    private void randomTest(final int wordLength, final int totalWords, final int wordsPerLine, final int lines, final String chars, final String delimiters) {
        final String[] words = checker.generateWords(wordLength, totalWords, chars);
        final String[][] text = checker.generateTest(lines, words, wordsPerLine);
        checker.testPP(checker.input(text, delimiters), processor.apply(text));
    }

    public void testPP(String... lines) {
        checker.testPP(lines, processor.apply(Arrays.stream(lines).map(s -> s.split("[ ,.:]+")).toArray(String[][]::new)));
    }

    // Stream "magic" code. You do not expected to understand it
    protected static Function<String[][], List<Pair<String, String>>> proc(final BinaryOperator<Integer> op, final Comparator<? super Map.Entry<String, Integer>> comparator) {
        return text -> {
            final Map<String, Integer> totals = Arrays.stream(text)
                    .flatMap(Arrays::stream)
                    .map(String::toLowerCase)
                    .collect(Collectors.toMap(Function.identity(), k -> 1, Integer::sum, LinkedHashMap::new));

            final Map<String, String> firsts = Arrays.stream(text)
                    .flatMap(line -> IntStream.range(0, line.length).boxed()
                            .collect(Collectors.toMap(i -> line[i].toLowerCase(), i -> i + 1, op, HashMap::new))
                            .entrySet().stream())
                    .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.mapping(e -> e.getValue().toString(), Collectors.joining(" "))));
            return totals.entrySet().stream()
                    .sorted(comparator)
                    .map(e -> Pair.of(e.getKey(), e.getValue() + " " + firsts.get(e.getKey())))
                    .collect(Collectors.toList());
        };
    }
}
