package reverse;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class ReverseHexTest extends ReverseTest {
    public ReverseHexTest(final int maxSize) {
        super("ReverseHex", maxSize);
        inputToString = outputToString = Integer::toHexString;
    }

    public static void main(String... args) {
        new ReverseHexTest(MAX_SIZE).run();
    }
}
