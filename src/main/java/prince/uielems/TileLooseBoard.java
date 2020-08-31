package prince.uielems;

import prince.enums.LooseStateEnum;
import prince.enums.TileTypeEnum;
import prince.utils.GameUtil;

public class TileLooseBoard extends BaseTile {
    private static final int FALL_VELOCITY = 3;
    private String[] frames;
    private int stage;
    private LooseStateEnum state;
    private int vacc;
    private int yTo;
    private boolean fall;
    private boolean redraw;


    public TileLooseBoard(BaseTile tile) {

        super(tile.element, tile.modifier, tile.room, tile.level);
        frames = GameUtil.generateFrameNames("_loose_", 1, 8, "", false, 1);
        state = LooseStateEnum.STATE_INACTIVE;

    }

    @Override
    public void update() {
        if (state == LooseStateEnum.STATE_SHAKING) {
            if (stage == frames.length + 3) {
                state = LooseStateEnum.STATE_FALLING;
                stage = 0;
                back = level.getLevelType().toString().toLowerCase() + "_falling";
            } else if (stage >= frames.length) {
                stage++;
            } else if (stage == 3 && !fall) {
                back = level.getLevelType().toString().toLowerCase() + "_" + TileTypeEnum.TILE_LOOSE_BOARD;
                state = LooseStateEnum.STATE_INACTIVE;
            } else {
                back = level.getLevelType().toString().toLowerCase() + frames[stage];
                stage++;
                if (stage == 1 || stage == 3 || stage == 7) {
//                    PlaySound("loose-floor-" + rnd(3).toStr())
                }
            }
            redraw = true;
        } else if (state == LooseStateEnum.STATE_FALLING) {
            int v = FALL_VELOCITY * stage;
            y += v;
            stage++;
            vacc += v;
            if (vacc > yTo) {
                state = LooseStateEnum.STATE_CRASHED;
                vacc = 0;
                yTo = 0;
//                PlaySound("loose-crash", true, 75)

            }
            redraw = true;
        }
    }

    public void shake(boolean fall) {
        if (state == LooseStateEnum.STATE_INACTIVE) {
            state = LooseStateEnum.STATE_SHAKING;
            stage = 0;
        }
        this.fall = fall && modifier == 0;
    }
}
