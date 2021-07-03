package markup;

import java.util.Map;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class BBCodeListTest extends ListTest {
    private static final Map<String, String> BB_CODE = Map.ofEntries(
            Map.entry("<em>", "[i]"),
            Map.entry("</em>", "[/i]"),
            Map.entry("<strong>", "[b]"),
            Map.entry("</strong>", "[/b]"),
            Map.entry("<s>", "[s]"),
            Map.entry("</s>", "[/s]"),
            Map.entry("<ul>", "[list]"),
            Map.entry("</ul>", "[/list]"),
            Map.entry("<ol>", "[list=1]"),
            Map.entry("</ol>", "[/list]"),
            Map.entry("<li>", "[*]"),
            Map.entry("</li>", "")
    );

    @Override
    protected void test(final Paragraph paragraph, final String expected) {
        test(paragraph::toBBCode, expected, BB_CODE);
    }

    protected void test(UnorderedList list, final String expected) {
        test(list::toBBCode, expected, BB_CODE);
    }

    protected void test(OrderedList list, final String expected) {
        test(list::toBBCode, expected, BB_CODE);
    }

    public static void main(String[] args) {
        new BBCodeListTest().run();
    }
}
