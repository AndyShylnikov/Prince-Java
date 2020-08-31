package prince.uielems;

import prince.Constants;
import prince.enums.ExitDoorStateEnum;
import prince.enums.LevelTypeEnum;

import java.util.Map;

public class TileExitDoor extends BaseTile {
    private int state;
    private boolean dropped;
    private int openHeight;

    public TileExitDoor(BaseTile tile, int openHeight, int doorX) {
        super(tile.element, tile.modifier, tile.room, tile.level);
        child.get("back").put("frameName", tile.level.getLevelType().toString().toLowerCase() + "_door");
        child.get("front").put("frameName", tile.level.getLevelType().toString().toLowerCase() + "_door_fg");
        child.get("front").put("visible", true);
        if (tile.level.getLevelType() == LevelTypeEnum.PALACE) {
            child.get("back").put("x", 7);
        } else {
            child.get("back").put("x", doorX);
        }
        child.get("back").put("y", Constants.TILE_HEIGHT - 67);
        child.get("back").put("height", Constants.BLOCK_HEIGHT - 12);
        cropY = 0;

        state = ExitDoorStateEnum.STATE_CLOSED.ordinal();
        redraw = false;
        dropped = false;
        this.openHeight = openHeight;

    }

    @Override
    public void update() {
        Map<String, Object> door = child.get("back");
        if (state == ExitDoorStateEnum.STATE_RAISING.ordinal()) {
            if ((int) door.get("height") == openHeight + level.getLevelType().ordinal()) {
                state = ExitDoorStateEnum.STATE_OPEN.ordinal();
            } else {
                door.put("height", ((int) door.get("height")) - 1);
                cropY--;
            }
            redraw = true;
        } else if (state == ExitDoorStateEnum.STATE_DROPPING.ordinal()) {
            if (((int) door.get("height")) >= Constants.BLOCK_HEIGHT - 12) {
                state = ExitDoorStateEnum.STATE_CLOSED.ordinal();
            } else {
                door.put("height", ((int) door.get("height")) + 10);
                cropY += 10;
            }
            redraw = true;
        }
    }

    @Override
    public void drop() {
        if (state == ExitDoorStateEnum.STATE_OPEN.ordinal()) {
            state = ExitDoorStateEnum.STATE_DROPPING.ordinal();
            child.get("back").put("visible", true);
            dropped = true;
        }
    }

    @Override
    public void raise(boolean stuck) {
        if (state == ExitDoorStateEnum.STATE_CLOSED.ordinal()) {
            state = ExitDoorStateEnum.STATE_RAISING.ordinal();
//            PlaySound("exit-door-open"); TODO: make sound playing
        }
    }

    public void mask() {
        child.get("front").put("visible", true);
        redraw = true;
    }

    public boolean isOpen() {
        return state == ExitDoorStateEnum.STATE_OPEN.ordinal();
    }
}
