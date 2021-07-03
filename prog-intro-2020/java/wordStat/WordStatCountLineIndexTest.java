package wordStat;

import java.util.Comparator;

import static wordStat.WordStatIndexTest.test;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class WordStatCountLineIndexTest {
    public static void main(String[] args) {
        test("WordStatCountLineIndex", WordStatLineIndexTest.answer(Comparator.comparing(e -> e.getValue().size())));
    }
}
