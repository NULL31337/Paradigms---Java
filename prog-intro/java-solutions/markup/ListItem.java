package markup;

import java.util.List;

public class ListItem extends AbstractElement implements MarkdownAndBBCode {
    public ListItem (List<Listable> txt) {
        super(txt);
    }

    @Override
    public void toBBCode(StringBuilder s) {
        for (BBCode now : txt) {
            s.append("[*]");
            now.toBBCode(s);
        }
    }

    @Override
    public void toMarkdown(StringBuilder s) {
        throw new UnsupportedOperationException();
    }
}
