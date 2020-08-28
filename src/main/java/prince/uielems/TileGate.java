package prince.uielems;

public class TileGate extends BaseTile {
    private int state;
    private int stage = 0;

    private boolean isAudio = false;
    private boolean redraw = false;
    private boolean stuck = false;

    public TileGate(BaseTile tile) {
        super(tile.element, tile.modifier, tile.room, tile.level);
        this.cropY = -tile.modifier * 46;

        this.child.get("back").put("framename", level.getLevelType().toString().toLowerCase() + "_gate");
        this.child.get("back").put("x", 0);
        this.child.get("back").put("y", 0);

        this.child.get("front").put("framename", level.getLevelType().toString().toLowerCase() + "_gate_fg");
        this.child.get("front").put("x", 32);
        this.child.get("front").put("y", 16);
        this.child.get("front").put("visible", false);
        this.state = tile.modifier;
    }

    public TileGate setState(int state) {
        this.state = state;
        return this;
    }

    public TileGate setStage(int stage) {
        this.stage = stage;
        return this;
    }

    public TileGate setAudio(boolean audio) {
        isAudio = audio;
        return this;
    }

    public TileGate setRedraw(boolean redraw) {
        this.redraw = redraw;
        return this;
    }

    public TileGate setStuck(boolean stuck) {
        this.stuck = stuck;
        return this;
    }

    public int getState() {
        return state;
    }

    public int getStage() {
        return stage;
    }

    public boolean isAudio() {
        return isAudio;
    }

    public boolean isRedraw() {
        return redraw;
    }

    public boolean isStuck() {
        return stuck;
    }
}
