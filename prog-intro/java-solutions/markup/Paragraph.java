package markup;
import java.util.List;

public class Paragraph extends AbstractElement implements Listable {
    public Paragraph (List <Paragraphable> txt) {
        super(txt);
        pushElementToBBCodeClose = "";
        pushElementToBBCodeOpen = "";
    }

    @Override
    public void toMarkdown(StringBuilder s) {
        for (Markdown now : txt){
            now.toMarkdown(s);
        }
    }
}