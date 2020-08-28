package prince.uielems;

public class SourceTile {
    protected int element, modifier;

    public SourceTile(int element, int modifier) {
        this.element = element;
        this.modifier = modifier;
    }

    public int getElement() {
        return element;
    }

    public int getModifier() {
        return modifier;
    }
}
