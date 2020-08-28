package prince.uielems;

import java.util.HashMap;
import java.util.Map;

public class TileSlicer extends BaseTile {
    private int stage;
    private int state;
    private Map<String, Object> blood;
    private boolean audio;
    private boolean active;
    private boolean redraw;


    public TileSlicer(BaseTile tile) {
        super(tile.element, tile.modifier, tile.room, tile.level);
        this.child.get("back").put("framename", level.getLevelType().toString().toLowerCase() + "_slicer_5");
        this.child.get("front").put("framename", child.get("back").get("framename") + "_fg");
        stage = 13;
        state = 0;
        blood = new HashMap<>() {{
            put("visible", false);
            put("frameName", "slicer_blood_5");
        }};


    }

    public int getStage() {
        return stage;
    }

    public TileSlicer setStage(int stage) {
        this.stage = stage;
        return this;
    }

    public int getState() {
        return state;
    }

    public TileSlicer setState(int state) {
        this.state = state;
        return this;
    }

    public Map<String, Object> getBlood() {
        return blood;
    }

    public TileSlicer setBlood(Map<String, Object> blood) {
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
}
