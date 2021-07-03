package expression.parser;

import java.util.List;
import java.util.stream.LongStream;

/**
 * @author Georgiy Korneev
 */
public class ParserFlipLowTest extends ParserBitwiseTest {
    protected ParserFlipLowTest() {
        unary.add(op("flip", ParserFlipLowTest::flip));
        unary.add(op("low", ParserFlipLowTest::low));

        tests.addAll(List.of(
                op("flip 12345", (x, y, z) -> 9987L),
                op("flip -12345", (x, y, z) -> -470548481L ),
                op("flip -1", (x, y, z) -> -1),
                op("flip (x - y)", (x, y, z) -> flip(x - y)),
                op("x - flip -y", (x, y, z) -> x - flip(-y)),
                op("flip -x", (x, y, z) -> flip(-x)),
                op("flip(x+y)", (x, y, z) -> flip(x + y)),
                op("low 123456", (x, y, z) -> 64L),
                op("low (x - y)", (x, y, z) -> low(x - y)),
                op("x - low y", (x, y, z) -> x - low(y)),
                op("low -x", (x, y, z) -> low(-x)),
                op("low(x+y)", (x, y, z) -> low(x + y))
        ));
    }

    private static long low(final long v) {
        return Long.lowestOneBit(v);
    }

    private static long flip(final long v) {
        return LongStream.iterate(v & 0xffffffffL, n -> n != 0, n -> n >> 1)
                .map(n -> n & 1)
                .reduce(0, (a, b) -> (a << 1) + b);
    }

    public static void main(final String[] args) {
        System.out.println(flip(-12345));
        new ParserFlipLowTest().run();
    }
}
