package markup;
import java.util.List;

public class Strong extends AbstractElement implements Paragraphable {
    public Strong (List <Paragraphable> txt) {
        super(txt);
        pushElementToMarkDown = "__";
        pushElementToBBCodeOpen = "[b]";
        pushElementToBBCodeClose = "[/b]";
    }
}