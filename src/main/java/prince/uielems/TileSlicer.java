package prince.uielems;

import prince.enums.SlicerStateEnum;

public class TileSlicer extends BaseTile {
    private int stage;
    private SlicerStateEnum state;
    private SlicerBlood blood;
    private boolean audio;
    private boolean active;
    private boolean redraw;


    public TileSlicer(BaseTile tile) {
        super(tile.element, tile.modifier, tile.room, tile.level);
        this.child.get("back").put("framename", level.getLevelType().toString().toLowerCase() + "_slicer_5");
        this.child.get("front").put("framename", child.get("back").get("framename") + "_fg");
        stage = 13;
        state = SlicerStateEnum.STATE_WAITING;
        blood = new SlicerBlood(false, "slicer_blood_5");
    }

    public int getStage() {
        return stage;
    }

    public TileSlicer setStage(int stage) {
        this.stage = stage;
        return this;
    }

    public SlicerStateEnum getState() {
        return state;
    }

    public TileSlicer setState(SlicerStateEnum state) {
        this.state = state;
        return this;
    }

    public SlicerBlood getBlood() {
        return blood;
    }

    public TileSlicer setBlood(SlicerBlood blood) {
        this.blood = blood;
        return this;
    }

    public boolean isAudio() {
        return audio;
    }

    public TileSlicer setAudio(boolean audio) {
        this.audio = audio;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public TileSlicer setActive(boolean active) {
        this.active = active;
        return this;
    }

    public boolean isRedraw() {
        return redraw;
    }

    public TileSlicer setRedraw(boolean redraw) {
        this.redraw = redraw;
        return this;
    }

    @Override
    public void update() {
        if (active) {
            stage++;
            if (stage >= 15) {
                stage = 0;
                active = false;
            } else if (stage <= 5) {
                child.get("back").put("frameName", level.getLevelType().toString().toLowerCase() + "_slicer_" + stage);
                child.get("front").put("frameName", level.getLevelType().toString().toLowerCase() + "_slicer_" + stage + "_fg");
                if (blood.visible) {
                    blood.frameName = "slicer_blood_" + stage;
                }
                if (stage == 3) {
                    state = SlicerStateEnum.STATE_SLICE;
                    if (audio) {
//    PlaySound("slicer", true)
                    }
                } else {
                    state = SlicerStateEnum.STATE_MOVING;
                }
                redraw = true;
            } else
                state = SlicerStateEnum.STATE_WAITING;
        }

    }

    @Override
    public Bounds getBounds() {
        return new Bounds(x + 15, y + 10, 63, 5);
    }

    public void start() {
        active = true;
    }

    class SlicerBlood {
        boolean visible;
        String frameName;

        public SlicerBlood(boolean visible, String frameName) {
            this.visible = visible;
            this.frameName = frameName;
        }
    }
}
