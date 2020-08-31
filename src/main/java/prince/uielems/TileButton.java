package prince.uielems;

import prince.enums.TileTypeEnum;

public class TileButton extends BaseTile {
    private int stageMax;
    private int pushes;
    private int stage;
    private boolean active;
    private boolean redraw;
    private boolean stuck;

    public TileButton(BaseTile tile) {
        super(tile.element, tile.modifier, tile.room, tile.level);
        stageMax = tile.modifier == TileTypeEnum.TILE_RAISE_BUTTON.ordinal() ? 3 : 5;
    }

    public int getStageMax() {
        return stageMax;
    }

    public TileButton setStageMax(int stageMax) {
        this.stageMax = stageMax;
        return this;
    }

    public int getPushes() {
        return pushes;
    }

    public TileButton setPushes(int pushes) {
        this.pushes = pushes;
        return this;
    }

    public int getStage() {
        return stage;
    }

    public TileButton setStage(int stage) {
        this.stage = stage;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public TileButton setActive(boolean active) {
        this.active = active;
        return this;
    }

    public boolean isRedraw() {
        return redraw;
    }

    public TileButton setRedraw(boolean redraw) {
        this.redraw = redraw;
        return this;
    }

    public boolean isStuck() {
        return stuck;
    }

    public TileButton setStuck(boolean stuck) {
        this.stuck = stuck;
        return this;
    }

    @Override
    public void update() {
        if (active) {
            if (stage == stageMax) {
                if (!stuck) {
                    front = tileKey + "_" + element + "_fg";
                    back = tileKey + "_" + element;
                    active = false;
                    redraw = true;
                }
            }
        }
    }

    @Override
    public void push(boolean stuck, boolean sound) {
        if (!active) {
            active = true;
            redraw = true;
            if (!isMasked) {
                front = "";
            }
            back += "down";
            if (sound) {
//PlaySound("button-open") TODO
            }
        }
        if (!stuck) {
            this.stuck = stuck;
        }
//        onPushed TODO
        pushes++;
        stage = 0;
    }
}
