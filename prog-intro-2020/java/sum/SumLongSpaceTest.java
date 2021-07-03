package sum;

import java.util.List;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class SumLongSpaceTest extends SumLongTest {
    public SumLongSpaceTest(final SChecker checker) {
        super(checker);
    }

    public static void main(final String... args) {
        new SumLongSpaceTest(new SumChecker("SumLongSpace"))
                .setSpaces(List.of(" \u2000\u2001\u2002\u2003\u00A0"))
                .run();
    }
}
