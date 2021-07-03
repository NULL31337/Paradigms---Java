package markup;
import java.util.List;

public class Strikeout extends AbstractElement implements Paragraphable {
    public Strikeout (List <Paragraphable> txt) {
        super(txt);
        pushElementToMarkDown = "~";
        pushElementToBBCodeOpen = "[s]";
        pushElementToBBCodeClose = "[/s]";
    }
}