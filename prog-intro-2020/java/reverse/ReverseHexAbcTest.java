package reverse;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class ReverseHexAbcTest extends ReverseTest {
    public ReverseHexAbcTest(final int maxSize) {
        super("ReverseHexAbc", maxSize);
        inputToString = this::toString;
    }

    public static void main(String... args) {
        new ReverseHexAbcTest(MAX_SIZE).run();
    }

    public String toString(final int value) {
        return checker.random.nextInt(10) == 0 ? ReverseAbcTest.toAbc(value) :
                checker.random.nextInt(10) > 0 ? Integer.toString(value) :
                (checker.random.nextBoolean() ? "0x" : "0X") + Integer.toHexString(value);
    }
}
