package prince.uielems;

import prince.Constants;
import prince.enums.ExitDoorStateEnum;
import prince.enums.LevelTypeEnum;

public class TileExitDoor extends BaseTile {
    private int state;
    private boolean dropped;
    private int openHeight;

    public TileExitDoor(BaseTile tile, int openHeight, int doorX) {
        super(tile.element, tile.modifier, tile.room, tile.level);
        childBack.frameName = tileKey + "_door";
        childFront.frameName = tileKey + "_door_fg";
        childFront.visible = true;

        if (tile.level.getLevelType() == LevelTypeEnum.PALACE) {
            childBack.x = 7;
        } else {
            childBack.x = doorX;
        }
        childBack.y = Constants.TILE_HEIGHT - 67;
        childBack.height = Constants.BLOCK_HEIGHT - 12;
        cropY = 0;

        state = ExitDoorStateEnum.STATE_CLOSED.ordinal();
        redraw = false;
        dropped = false;
        this.openHeight = openHeight;

    }

    @Override
    public void update() {
        if (state == ExitDoorStateEnum.STATE_RAISING.ordinal()) {
            if (childBack.height == openHeight + level.getLevelType().ordinal()) {
                state = ExitDoorStateEnum.STATE_OPEN.ordinal();
            } else {
                childBack.height--;
                cropY--;
            }
            redraw = true;
        } else if (state == ExitDoorStateEnum.STATE_DROPPING.ordinal()) {
            if (childBack.height >= Constants.BLOCK_HEIGHT - 12) {
                state = ExitDoorStateEnum.STATE_CLOSED.ordinal();
            } else {
                childBack.height += 10;
                cropY += 10;
            }
            redraw = true;
        }
    }

    @Override
    public void drop() {
        if (state == ExitDoorStateEnum.STATE_OPEN.ordinal()) {
            state = ExitDoorStateEnum.STATE_DROPPING.ordinal();
            childBack.visible = true;
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
        childFront.visible=true;
        redraw = true;
    }

    public boolean isOpen() {
        return state == ExitDoorStateEnum.STATE_OPEN.ordinal();
    }
}
