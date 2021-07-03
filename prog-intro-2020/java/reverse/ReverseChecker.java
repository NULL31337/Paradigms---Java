package reverse;

import base.MainStdChecker;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class ReverseChecker extends MainStdChecker {
    public ReverseChecker(final String className) {
        super(className);
    }

    public void test(final String[][] input, final String[][] output) {
        final List<String> expected = toLines(output, " ");
        final List<String> actual = toLines(input, randomString(" ", 1, 10));
        checkEquals(expected, runStd(actual));
    }

    private List<String> toLines(final String[][] data, final String delimiter) {
        if (data.length == 0) {
            return Collections.singletonList("");
        }
        return Arrays.stream(data)
                .map(row -> String.join(delimiter, row))
                .collect(Collectors.toList());
    }

    public int[][] random(final int[] profile) {
        final int col = random.nextInt(Arrays.stream(profile).max().orElse(0));
        final int row = random.nextInt(profile.length);
        final int m = random.nextInt(5) - 2;
        final int[][] ints = Arrays.stream(profile).mapToObj(random::ints).map(IntStream::toArray).toArray(int[][]::new);
        Arrays.stream(ints).filter(r -> col < r.length).forEach(r -> r[col] = Math.abs(r[col]) / 2 * m);
        ints[row] = Arrays.stream(ints[row]).map(Math::abs).map(v -> v / 2 * m).toArray();
        return ints;
    }
}
