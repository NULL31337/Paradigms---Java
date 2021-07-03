package markup;

import java.util.List;

public class OrderedList extends AbstractList {
    public OrderedList(List<ListItem> txt) {
        super(txt);
        pushElementToBBCodeOpen = "[list=1]";
        pushElementToBBCodeClose = "[/list]";
    }
}
