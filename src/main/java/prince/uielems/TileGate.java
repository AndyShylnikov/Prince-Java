package prince.uielems;

import prince.enums.GateStateEnum;

public class TileGate extends BaseTile {
    private static final int WAIT_CYCLES = 55;
    private int state;
    private int stage = 0;
    private boolean isAudio = false;
    private boolean redraw = false;
    private boolean stuck = false;

    public TileGate(BaseTile tile) {
        super(tile.element, tile.modifier, tile.room, tile.level);
        this.cropY = -tile.modifier * 46;
        childBack.frameName = tileKey + "_gate";
        childBack.x=0;
        childBack.y=0;

        childFront.frameName = tileKey + "_gate_fg";
        childBack.x=32;
        childBack.y=16;
        childBack.visible=false;

        this.state = tile.modifier;
    }

    public int getState() {
        return state;
    }

    public TileGate setState(int state) {
        this.state = state;
        return this;
    }

    public int getStage() {
        return stage;
    }

    public TileGate setStage(int stage) {
        this.stage = stage;
        return this;
    }

    public boolean isAudio() {
        return isAudio;
    }

    public TileGate setAudio(boolean audio) {
        isAudio = audio;
        return this;
    }

    public boolean isRedraw() {
        return redraw;
    }

    public TileGate setRedraw(boolean redraw) {
        this.redraw = redraw;
        return this;
    }

    public boolean isStuck() {
        return stuck;
    }

    public TileGate setStuck(boolean stuck) {
        this.stuck = stuck;
        return this;
    }

    @Override
    public void update() {
        if (state == GateStateEnum.STATE_RAISING.ordinal()) {
            if (cropY == -47) {
                state = GateStateEnum.STATE_WAITING.ordinal();
                stage = 0;
                if (isAudio) {
//                    PlaySound("gate-end", true);

                }
            } else {
                cropY--;
                if (cropY % 2 == 0) {
                    if (isAudio) {
//    PlaySound("gate-open", true)
                    }
                }
            }
            redraw = true;
        } else if (state == GateStateEnum.STATE_WAITING.ordinal() && !stuck) {
            stage++;
            if (stage == WAIT_CYCLES) {
                state = GateStateEnum.STATE_DROPPING.ordinal();
                stage = 0;
            }
            redraw = true;
        } else if (state == GateStateEnum.STATE_DROPPING.ordinal()) {
            if (stage == 0) {
                cropY++;
                if (cropY >= 0) {
                    cropY = 0;
                    state = GateStateEnum.STATE_CLOSED.ordinal();
                    if (isAudio) {
//                        PlaySound("gate-end", true) TODO
                    }
                }
                stage++;
                if (isAudio) {
//                    PlaySound("gate-close", true) TODO

                }
            } else {
                stage = (stage + 1) % 4;
            }
            redraw = true;
        } else if (state == GateStateEnum.STATE_FAST_DROPPING.ordinal()) {
            cropY += 10;
            if (cropY >= 0) {
                cropY = 0;
                state = GateStateEnum.STATE_CLOSED.ordinal();
            }
            redraw = true;
        }
    }

    @Override
    public void raise(boolean stuck) {
        stage = 0;
        this.stuck = stuck;
        if (state != GateStateEnum.STATE_WAITING.ordinal()) {
            state = GateStateEnum.STATE_WAITING.ordinal();
        }
    }

    @Override
    public void drop() {
        if (state != GateStateEnum.STATE_CLOSED.ordinal()) {
            state = GateStateEnum.STATE_FAST_DROPPING.ordinal();
            if (isAudio) {
//                PlaySound("gate-fast-close");
            }
        }
    }

    @Override
    public Bounds getBounds() {
        return new Bounds(x + 40, y, 4, 63 + cropY);
    }

    public boolean canCross(int height) {
        return Math.abs(cropY) > height;
    }
}
