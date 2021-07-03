package wordStat;

import base.Pair;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static wordStat.WordStatIndexTest.test;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class WordStatLineIndexTest {
    public static void main(String[] args) {
        test("WordStatLineIndex", answer(Comparator.comparingInt(e -> 0)));
    }

    protected static Function<String[][], List<Pair<String, String>>> answer(final Comparator<? super Map.Entry<String, List<String>>> comparator) {
        return text -> IntStream.range(0, text.length).boxed()
                .flatMap(r -> IntStream.range(0, text[r].length)
                                .mapToObj(c -> Pair.of(text[r][c].toLowerCase(), (r + 1) + ":" + (c + 1))))
                .collect(Collectors.groupingBy(Pair::getFirst, LinkedHashMap::new, Collectors.mapping(Pair::getSecond, Collectors.toList())))
                .entrySet().stream()
                .sorted(comparator)
                .map(e -> Pair.of(e.getKey(), e.getValue().size() + " " + String.join(" ", e.getValue())))
                .collect(Collectors.toList());
    }
}
