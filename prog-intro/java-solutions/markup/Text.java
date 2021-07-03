package markup;

public class Text  implements Paragraphable {
    private final String txt;
    public Text(String txt) {
        this.txt = txt;
    }
    @Override
    public void toMarkdown(StringBuilder s) {
        s.append(txt);
    }
    @Override
    public void toBBCode(StringBuilder s) {
        s.append(txt);
    }
}