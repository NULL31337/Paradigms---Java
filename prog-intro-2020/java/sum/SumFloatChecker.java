package sum;

import base.Asserts;
import base.MainChecker;

import java.util.Collections;
import java.util.List;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class SumFloatChecker extends MainChecker implements SChecker {
    public SumFloatChecker(final String className) {
        super(className);
    }

    @Override
    public void test(final Number result, final String... input) {
        final List<String> out = run(input);
        Asserts.assertEquals("Single line expected", 1, out.size());
        final double actual = Float.parseFloat(out.get(0));
        Asserts.assertEquals("Sum", result.doubleValue(), actual, 1e-5);
        checkEquals(Collections.emptyList(), Collections.emptyList());
    }
}
