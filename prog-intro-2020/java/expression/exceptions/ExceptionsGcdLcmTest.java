package expression.exceptions;

import java.math.BigInteger;
import java.util.List;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class ExceptionsGcdLcmTest extends ExceptionsAbsSqrtTest {
    protected ExceptionsGcdLcmTest() {
        levels.add(0, list(
                op("gcd", ExceptionsGcdLcmTest::gcd),
                op("lcm", ExceptionsGcdLcmTest::lcm)
        ));

        tests.addAll(List.of(
                op("6 - 10 gcd-3", (x, y, z) -> 1),
                op("6 - 10 gcd-2", (x, y, z) -> 2),
                op("20 gcd 30", (x, y, z) -> 10),
                op("40 gcd 30 gcd 5", (x, y, z) -> 5),
                op("120 gcd 16 * 5", (x, y, z) -> 40),
                op("x gcd (y * z)", (x, y, z) -> gcd(x, y * z)),
                op("2 gcd x + 1", (x, y, z) -> gcd(2, x + 1)),
                op("-1 gcd (3 gcd x)", (x, y, z) -> gcd(-1, x)),
                op("8 lcm 6", (x, y, z) -> 24),
                op("x lcm y", (x, y, z) -> lcm(x, y)),
                op("5lcm y", (x, y, z) -> lcm(5, y))
        ));
        parsingTest.addAll(List.of(
                parseExample("lcm"),
                parseExample("lcm1"),
                parseExample("lcm 1"),
                parseExample("1 lcm"),
                parseExample("5lcm5"),
                parseExample("1 llc 1"),
                parseExample("1 * lcm 2"),
                parseExample("1 lcm * 3")
        ));
    }

    private static long gcd(final long a, final long b) {
        return BigInteger.valueOf(a).gcd(BigInteger.valueOf(b)).intValue();
    }

    private static long lcm(final long a, final long b) {
        if (a == 0 && b == 0) {
            return 0;
        }
        return a * b / gcd(a, b);
    }

    public static void main(final String[] args) {
        new ExceptionsGcdLcmTest().run();
    }
}
