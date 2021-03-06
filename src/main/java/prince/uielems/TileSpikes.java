package prince.uielems;

import prince.enums.SpikeStateEnum;
import prince.enums.TileTypeEnum;

import static prince.enums.SpikeStateEnum.*;

public class TileSpikes extends BaseTile {
    private SpikeStateEnum state;
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

        childBack.frameName = tileKey + "_" + element + "_" + modifier;
        childFront.frameName = childBack.frameName + "_fg";

    }

    public SpikeStateEnum getState() {
        return state;
    }

    public TileSpikes setState(SpikeStateEnum state) {
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

    @Override
    public void update() {
        if (modifier != 0) {
            return;
        }
        if (state == SpikeStateEnum.STATE_RAISING) {
            stage++;
            childBack.frameName = tileKey + TileTypeEnum.TILE_SPIKES + stage;
            childFront.frameName = childBack.frameName + "_fg";
            if (stage == 5) {
                state = STATE_FULL_OUT;
                stage = 0;
            }
            redraw = true;
        } else if (state == STATE_FULL_OUT) {
            stage++;
            if (stage > 15) {
                state = STATE_DROPPING;
                stage = 5;
            }
            redraw = true;
        } else if (state == STATE_DROPPING) {
            stage--;
            if (stage == 3) {
                stage--;
            }
            childBack.frameName = tileKey + TileTypeEnum.TILE_SPIKES + stage;
            childFront.frameName = childBack.frameName + "_fg";

            if (stage == 0) {
                state = STATE_INACTIVE;
            }
            redraw = true;
        }
    }

    @Override
    public void raise() {
        if (state == STATE_INACTIVE) {
            state = STATE_RAISING;
//    PlaySound("spikes")

        } else if (state == STATE_FULL_OUT) {
            stage = 0;
        }
    }
}
