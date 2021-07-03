package reverse;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class ReverseHexDecTest extends ReverseTest {
    public ReverseHexDecTest(final int maxSize) {
        super("ReverseHexDec", maxSize);
        inputToString = value -> checker.random.nextBoolean()
                ? Integer.toString(value)
                : (checker.random.nextBoolean() ? "0x" : "0X") + Integer.toHexString(value);
    }

    public static void main(String... args) {
        new ReverseHexDecTest(MAX_SIZE).run();
    }
}
