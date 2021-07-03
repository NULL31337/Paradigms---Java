package sum;

import java.util.Arrays;
import java.util.Random;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class SumFloatTest extends SumTest {
    public SumFloatTest(final SChecker checker) {
        super(checker);
    }

    public static void main(final String... args) {
        new SumFloatTest(new SumFloatChecker("SumFloat")).run();
    }

    @Override
    protected void test() {
        test(1, "1");
        test(6, "1", "2", "3");
        test(1, " 1");
        test(1, "1 ");
        test(1, "\u20001\u2000");
        test(12345, "\u200012345\u2000");
        test(1368, " 123 456 789 ");
        test(60, "010", "020", "030");
        test(-1, "-1");
        test(-6, "-1", "-2", "-3");
        test(-12345, "\u2000-12345\u2000");
        test(-1368, " -123 -456 -789 ");
        test(1, "+1");
        test(6, "+1", "+2", "+3");
        test(12345, "\u2000+12345\u2000");
        test(1368, " +123 +456 +789 ");
        test(0);
        test(0, " ");
        test(5, "2.5 2.5");
        test(0, "1e10 -1e10");
        test(2e10, "1.5e10 0.5E10");
        randomTest(10, 100);
        randomTest(10, 0.01);
        randomTest(10, Integer.MIN_VALUE);
        randomTest(10, Integer.MAX_VALUE);
        randomTest(10, Float.MAX_VALUE / 10);
        randomTest(100, Float.MAX_VALUE / 100);
    }

    @Override
    protected Number randomValue(final Number max, final Random random) {
        return (checker.getRandom().nextFloat() - 0.5) * 2 * max.floatValue();
    }

    @Override
    protected Number sum(final Number[] values) {
        return Arrays.stream(values).mapToDouble(Number::floatValue).sum();
    }
}
