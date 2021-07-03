package markup;

import java.util.List;

public abstract class AbstractList extends AbstractElement implements Listable {
    public AbstractList (List<ListItem> txt) {
        super(txt);
    }

    public void toMarkdown(StringBuilder s) {
        throw new UnsupportedOperationException();
    }
}
