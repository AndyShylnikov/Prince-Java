package prince.uielems;

public class TileSpikes extends BaseTile {
    private int state;
    private int stage;
    private boolean redraw;
    private boolean mortal;

    public TileSpikes(BaseTile tile) {
        super(tile.element, tile.modifier, tile.room, tile.level);
        stage = 0;
        mortal = tile.modifier < 5;
        if (tile.modifier > 2 && tile.modifier < 6)
            modifier = 5;
        else if (tile.modifier == 6)
            modifier = 4;
        else if (tile.modifier > 6)
            modifier = 9 - tile.modifier;

        this.child.get("back").put("framename", level.getLevelType().toString().toLowerCase() + "_" + element + "_" + modifier);
        this.child.get("front").put("framename", child.get("back").get("framename") + "_fg");


    }

    public int getState() {
        return state;
    }

    public TileSpikes setState(int state) {
        this.state = state;
        return this;
    }

    public int getStage() {
        return stage;
    }

    public TileSpikes setStage(int stage) {
        this.stage = stage;
        return this;
    }

    public boolean isRedraw() {
        return redraw;
    }

    public TileSpikes setRedraw(boolean redraw) {
        this.redraw = redraw;
        return this;
    }

    public boolean isMortal() {
        return mortal;
    }

    public TileSpikes setMortal(boolean mortal) {
        this.mortal = mortal;
        return this;
    }
}
