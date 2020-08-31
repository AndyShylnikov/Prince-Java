package prince.uielems;

public class TileSword extends BaseTile {
    private int tick;
    private int stage;
    private boolean redraw;
    private boolean hasObject;

    public TileSword(BaseTile tile) {
        super(tile.element, tile.modifier, tile.room, tile.level);
        tick = (int) (Math.random() * 128 + 39);
        hasObject = true;
    }

    public int getTick() {
        return tick;
    }

    public TileSword setTick(int tick) {
        this.tick = tick;
        return this;
    }

    public int getStage() {
        return stage;
    }

    public TileSword setStage(int stage) {
        this.stage = stage;
        return this;
    }

    public boolean isRedraw() {
        return redraw;
    }

    public TileSword setRedraw(boolean redraw) {
        this.redraw = redraw;
        return this;
    }

    @Override
    public boolean isHasObject() {
        return hasObject;
    }

    @Override
    public TileSword setHasObject(boolean hasObject) {
        this.hasObject = hasObject;
        return this;
    }

    @Override
    public void update() {
        if (hasObject) {
            if (stage == -1) {
                back = tileKey + "_" + element;
                tick = ((int) (Math.random() * 128)) + 39;
                redraw = true;
            }
            stage++;
            if (stage == tick) {
                stage = -1;
                redraw = true;
            }
        }
    }
}
