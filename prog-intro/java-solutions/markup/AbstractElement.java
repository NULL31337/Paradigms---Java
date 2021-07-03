package markup;

import java.util.List;

public abstract class AbstractElement implements MarkdownAndBBCode {
    protected List<? extends MarkdownAndBBCode> txt;
    protected String pushElementToBBCodeOpen;
    protected String pushElementToBBCodeClose;

    public AbstractElement (List<? extends MarkdownAndBBCode> txt) {
        this.txt = txt;
    }

    public void toBBCode(StringBuilder s) {
        s.append(pushElementToBBCodeOpen);
        for (BBCode now : txt) {
            now.toBBCode(s);
        }
        s.append(pushElementToBBCodeClose);
    }

    protected String pushElementToMarkDown;

    public void toMarkdown(StringBuilder s) {
        s.append(pushElementToMarkDown);
        for (Markdown now : txt) {
            now.toMarkdown(s);
        }
        s.append(pushElementToMarkDown);
    }
}
