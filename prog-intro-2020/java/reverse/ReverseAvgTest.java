package reverse;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class ReverseAvgTest extends ReverseTest {
    public ReverseAvgTest(final int maxSize) {
        super("ReverseAvg", maxSize);
    }

    public static void main(String... args) {
        new ReverseAvgTest(MAX_SIZE).run();
    }

    @Override
    protected int[][] transform(final int[][] ints) {
        final long[] rt = Arrays.stream(ints)
                .map(Arrays::stream)
                .mapToLong(s -> s.summaryStatistics().getSum())
                .toArray();
        final int[] rc = Arrays.stream(ints).mapToInt(r -> r.length).toArray();
        final int length = Arrays.stream(ints).mapToInt(r -> r.length).max().orElse(0);
        final long[] ct = new long[length];
        final int[] cc = new int[length];
        Arrays.stream(ints).forEach(row -> IntStream.range(0, row.length).forEach(i -> ct[i] += row[i]));
        Arrays.stream(ints).forEach(row -> IntStream.range(0, row.length).forEach(i -> cc[i]++));
        return IntStream.range(0, ints.length)
                .mapToObj(r -> IntStream.range(0, ints[r].length).map(c -> (int) ((rt[r] + ct[c] - ints[r][c]) / (rc[r] + cc[c] - 1))).toArray())
                .toArray(int[][]::new);
    }
}
