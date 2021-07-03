package markup;

import java.util.List;

public class UnorderedList extends AbstractList {
    public UnorderedList(List<ListItem> txt) {
        super(txt);
        pushElementToBBCodeOpen = "[list]";
        pushElementToBBCodeClose = "[/list]";
    }
}
