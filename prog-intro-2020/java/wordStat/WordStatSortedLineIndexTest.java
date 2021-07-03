package wordStat;

import java.util.Map;

import static wordStat.WordStatIndexTest.test;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class WordStatSortedLineIndexTest {
    public static void main(String[] args) {
        test("WordStatSortedLineIndex", WordStatLineIndexTest.answer(Map.Entry.comparingByKey()));
    }
}
