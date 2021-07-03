package sum;

import java.util.List;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class SumBigIntegerSpaceTest extends SumBigIntegerTest {
    public SumBigIntegerSpaceTest(final SChecker checker) {
        super(checker);
    }

    public static void main(final String... args) {
        new SumBigIntegerSpaceTest(new SumChecker("SumBigIntegerSpace"))
                .setSpaces(List.of(" \u2000\u2001\u2002\u2003\u00A0"))
                .run();
    }
}
