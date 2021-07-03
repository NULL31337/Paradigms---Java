package reverse;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class ReverseAbcTest extends ReverseTest {
    public ReverseAbcTest(final int maxSize) {
        super("ReverseAbc", maxSize);
        inputToString = outputToString = ReverseAbcTest::toAbc;
    }

    public static void main(String... args) {
        new ReverseAbcTest(MAX_SIZE).run();
    }

    public static String toAbc(final int value) {
        final char[] chars = Integer.toString(value).toCharArray();
        for (int i = value < 0 ? 1 : 0; i < chars.length; i++) {
            chars[i] += 49;
        }
        return new String(chars);
    }
}
