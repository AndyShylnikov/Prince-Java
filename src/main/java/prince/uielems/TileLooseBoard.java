package prince.uielems;

import prince.utils.GameUtil;

public class TileLooseBoard extends BaseTile {
    private String[] frames;
    public TileLooseBoard(BaseTile tile) {
        super(tile.element, tile.modifier, tile.room, tile.level);
        frames = GameUtil.generateFrameNames("_loose_", 1, 8, "", false, 1);
    }
}
