package wordStat;

import java.util.Map;

import static wordStat.WordStatIndexTest.proc;
import static wordStat.WordStatIndexTest.test;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class WordStatCountFirstIndexTest {
    public static void main(final String... args) {
        test("WordStatCountFirstIndex", proc(Math::min, Map.Entry.comparingByValue()));
    }
}
