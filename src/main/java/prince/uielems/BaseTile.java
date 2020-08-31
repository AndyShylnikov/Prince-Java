package prince.uielems;

import prince.Constants;
import prince.enums.TileTypeEnum;
import prince.pojo.Room;

import java.util.Map;

import static prince.enums.TileTypeEnum.*;

public class BaseTile extends SourceTile {
    protected int element, modifier, x, y;
    protected int width;
    protected int height;
    protected int cropY;

    protected String back;
    protected String front;

    protected TileChild childBack;
    protected TileChild childFront;

    protected boolean isMasked;
    protected boolean redraw;
    protected boolean hasObject;

    protected Room room;
    protected Level level;

    protected int roomX, roomY;

    protected String tileKey;


    public BaseTile(int element, int modifier, Room room, Level level) {
        super(element, modifier);
        this.element = element;
        this.modifier = modifier;
        this.room = room;
        this.level = level;
        tileKey = level.getLevelType().toString().toLowerCase();
        width = Constants.BLOCK_WIDTH;
        height = Constants.BLOCK_HEIGHT;

        back = tileKey + "_" + element;
        front = back + "_fg";

        childBack = new TileChild();
        childFront = new TileChild();

        childBack.visible = true;
        childFront.visible = true;
    }

    public int getX() {
        return x;
    }

    public BaseTile setX(int x) {
        this.x = x;
        return this;
    }

    public int getY() {
        return y;
    }

    public BaseTile setY(int y) {
        this.y = y;
        return this;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getCropY() {
        return cropY;
    }

    public BaseTile setCropY(int cropY) {
        this.cropY = cropY;
        return this;
    }

    public String getBack() {
        return back;
    }

    public BaseTile setBack(String back) {
        this.back = back;
        return this;
    }

    public String getFront() {
        return front;
    }

    public BaseTile setFront(String front) {
        this.front = front;
        return this;
    }

    public boolean isMasked() {
        return isMasked;
    }

    public BaseTile setMasked(boolean masked) {
        isMasked = masked;
        return this;
    }

    public boolean isShouldBeRedrawen() {
        return redraw;
    }

    public BaseTile setShouldBeRedrawen(boolean shouldBeRedrawen) {
        this.redraw = shouldBeRedrawen;
        return this;
    }

    public boolean isHasObject() {
        return hasObject;
    }

    public BaseTile setHasObject(boolean hasObject) {
        this.hasObject = hasObject;
        return this;
    }

    public Room getRoom() {
        return room;
    }

    public Level getLevel() {
        return level;
    }

    public int getRoomX() {
        return roomX;
    }

    public BaseTile setRoomX(int roomX) {
        this.roomX = roomX;
        return this;
    }

    public int getRoomY() {
        return roomY;
    }

    public BaseTile setRoomY(int roomY) {
        this.roomY = roomY;
        return this;
    }

    public void setMask(boolean masked) {
        isMasked = masked;
        redraw = true;
    }

    public boolean is_walkable() {
        return element != TILE_WALL.ordinal() &&
                element != TileTypeEnum.TILE_SPACE.ordinal() &&
                element != TileTypeEnum.TILE_TOP_BIG_PILLAR.ordinal() &&
                element != TILE_TAPESTRY_TOP.ordinal() &&
                element != TileTypeEnum.TILE_LATTICE_SUPPORT.ordinal() &&
                element != TileTypeEnum.TILE_SMALL_LATTICE.ordinal() &&
                element != TileTypeEnum.TILE_LATTICE_LEFT.ordinal() &&
                element != TileTypeEnum.TILE_LATTICE_RIGHT.ordinal();
    }

    public boolean isSpace() {
        return element == TileTypeEnum.TILE_SPACE.ordinal() ||
                element == TILE_TOP_BIG_PILLAR.ordinal() ||
                element == TILE_TAPESTRY_TOP.ordinal() ||
                element == TILE_LATTICE_SUPPORT.ordinal() ||
                element == TILE_SMALL_LATTICE.ordinal() ||
                element == TILE_LATTICE_LEFT.ordinal() ||
                element == TILE_LATTICE_RIGHT.ordinal();
    }

    public boolean isBarrier() {
        return element == TILE_WALL.ordinal() ||
                element == TILE_GATE.ordinal() ||
                element == TILE_TAPESTRY.ordinal() ||
                element == TILE_TAPESTRY_TOP.ordinal() ||
                element == TILE_MIRROR.ordinal();
    }

    public boolean isTrob() {
        return element == TILE_GATE.ordinal() ||
                element == TILE_RAISE_BUTTON.ordinal() ||
                element == TILE_DROP_BUTTON.ordinal() ||
                element == TILE_SPIKES.ordinal() ||
                element == TILE_SWORD.ordinal() ||
                element == TILE_SLICER.ordinal() ||
                element == TILE_EXIT_RIGHT.ordinal() ||
                element == TILE_POTION.ordinal() ||
                element == TILE_TORCH.ordinal();
    }

    public boolean isMob() {
        return element == TILE_LOOSE_BOARD.ordinal();
    }

    public boolean isExitDoor() {
        return element == TILE_EXIT_LEFT.ordinal() ||
                element == TILE_EXIT_RIGHT.ordinal();
    }

    public Bounds getBounds() {
        return new Bounds(x + 40, y, 4, 43);

    }

    public boolean intersects(Bounds bounds) {
        Bounds b = getBounds();
        return (b.x + b.width) > bounds.x &&
                b.x < (bounds.x + bounds.width) &&
                (b.y + b.height) > bounds.y &&
                b.y < (bounds.y + bounds.height);

    }

    public void update() {
    }

    public void raise(boolean stuck) {
    }

    public void raise() {

    }

    public void drop() {
    }

    public void push(boolean stuck, boolean sound) {
    }

    public TileChild getChildBack() {
        return childBack;
    }

    public TileChild getChildFront() {
        return childFront;
    }

    class Bounds {
        int x, y, width, height;

        public Bounds(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
    }

    public BaseTile setChildBack(TileChild childBack) {
        this.childBack = childBack;
        return this;
    }

    public BaseTile setChildFront(TileChild childFront) {
        this.childFront = childFront;
        return this;
    }
}