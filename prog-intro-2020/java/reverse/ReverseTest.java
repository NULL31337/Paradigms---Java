package reverse;

import java.util.Arrays;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class ReverseTest {
    public static final int MAX_SIZE = 10_000;
    public static final int[] DIVISORS = {100, 10, 1};
    protected final ReverseChecker checker;
    private final int maxSize;
    protected IntFunction<String> inputToString = Integer::toString;
    protected IntFunction<String> outputToString = inputToString;

    public ReverseTest(final int maxSize) {
        this("Reverse", maxSize);
    }

    protected ReverseTest(final String className, final int maxSize) {
        checker = new ReverseChecker(className);
        this.maxSize = maxSize;
    }

    public static void main(String... args) {
        new ReverseTest(MAX_SIZE).run();
    }

    protected void run() {
        test(new int[][]{
                {1}
        });
        test(new int[][]{
                {1, 2},
                {3}
        });
        test(new int[][]{
                {1, 2, 3},
                {4, 5},
                {6}
        });
        test(new int[][]{
                {1, 2, 3},
                {},
                {4, 5},
                {6}
        });
        test(new int[][]{
                {1, 2, 3},
                {-4, -5},
                {6}
        });
        test(new int[][]{
                {1, -2, 3},
                {},
                {4, -5},
                {6}
        });
        test(new int[][]{
                {1, -2, 3},
                {},
                {-4, -5},
                {6}
        });
        test(new int[][]{
                {},
        });
        test(new int[][]{
                {},
                {},
                {},
        });
        testRandom(tweakProfile(constProfile(10, 10), new int[][]{}));
        testRandom(tweakProfile(constProfile(100, 100), new int[][]{}));
        testRandom(randomProfile(100, maxSize));
        testRandom(randomProfile(maxSize / 10, maxSize));
        testRandom(randomProfile(maxSize, maxSize));
        for (int d : DIVISORS) {
            final int size = maxSize / d;
            testRandom(tweakProfile(constProfile(size, 0), new int[][]{new int[]{size, 0}}));
            testRandom(tweakProfile(randomProfile(size, maxSize), new int[][]{new int[]{size, 0}}));
            testRandom(tweakProfile(constProfile(size, 0), new int[][]{new int[]{size / 2, size / 2 - 1}}));
            testRandom(tweakProfile(constProfile(size, 1), new int[][]{new int[]{size / 3, size / 3, size * 2 / 3}}));
        }
        checker.printStatus();
    }

    protected int[] randomProfile(final int length, final int values) {
        final int[] profile = new int[length];
        for (int i = 0; i < values; i++) {
            profile[checker.randomInt(0, length - 1)]++;
        }
        return profile;
    }

    protected void testRandom(final int[] profile) {
        test(checker.random(profile));
    }

    public static int[] constProfile(final int length, final int value) {
        final int[] profile = new int[length];
        Arrays.fill(profile, value);
        return profile;
    }

    public static int[] tweakProfile(final int[] profile, final int[][] mods) {
        for (int[] mod : mods) {
            Arrays.stream(mod).skip(1).forEach(i -> profile[i] = mod[0]);
        }
        return profile;
    }

    protected void test(final int[][] ints) {
        checker.test(toString(ints, inputToString), toString(transform(ints), outputToString));
    }

    private String[][] toString(final int[][] ints, final IntFunction<String> intToString) {
        return Arrays.stream(ints)
                .map(row -> Arrays.stream(row).mapToObj(intToString).toArray(String[]::new))
                .toArray(String[][]::new);
    }

    protected int[][] transform(final int[][] ints) {
        return IntStream.range(1, ints.length + 1)
                .mapToObj(i -> ints[ints.length - i])
                .map(is -> IntStream.range(1, is.length + 1).map(i -> is[is.length - i]).toArray())
                .toArray(int[][]::new);
    }
}
