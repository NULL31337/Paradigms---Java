package markup;
import java.util.List;

public class Emphasis extends AbstractElement implements Paragraphable {
    public Emphasis (List <Paragraphable> txt) {
        super(txt);
        pushElementToMarkDown = "*";
        pushElementToBBCodeOpen = "[i]";
        pushElementToBBCodeClose = "[/i]";
    }
}