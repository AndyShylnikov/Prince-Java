package prince.pojo;

import prince.uielems.Level;
import prince.uielems.SourceTile;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private int roomId;
    private Level level;
    private Room upRoom;
    private Room downRoom;
    private Room leftRoom;
    private Room rightRoom;
    private int x;
    private int y;

    private List upList;
    private List leftList;
    private List rightList;

    private boolean hideUp;
    private boolean hideLeft;
    private int leftZ;

    private int[][][] wallPattern;

    private List<SourceTile> tiles;

    public Room(int roomId, Level level, List<SourceTile> tilesSource) {
        this.roomId = roomId;
        this.level = level;
        upList = new ArrayList();
        leftList = new ArrayList();
        rightList = new ArrayList();

        hideUp = false;
        hideLeft = false;
        leftZ = 5;
        wallPattern = new int[3][4][11];
        tiles = tilesSource;
    }

    public int getRoomId() {
        return roomId;
    }

    public Level getLevel() {
        return level;
    }

    public Room getUpRoom() {
        return upRoom;
    }

    public Room getDownRoom() {
        return downRoom;
    }

    public Room getLeftRoom() {
        return leftRoom;
    }

    public Room getRightRoom() {
        return rightRoom;
    }

    public Room setUpRoom(Room upRoom) {
        this.upRoom = upRoom;
        return this;
    }

    public Room setDownRoom(Room downRoom) {
        this.downRoom = downRoom;
        return this;
    }

    public Room setLeftRoom(Room leftRoom) {
        this.leftRoom = leftRoom;
        return this;
    }

    public Room setRightRoom(Room rightRoom) {
        this.rightRoom = rightRoom;
        return this;
    }

    public int getX() {
        return x;
    }

    public Room setX(int x) {
        this.x = x;
        return this;
    }

    public int getY() {
        return y;
    }

    public Room setY(int y) {
        this.y = y;
        return this;
    }

    public boolean isHideUp() {
        return hideUp;
    }

    public Room setHideUp(boolean hideUp) {
        this.hideUp = hideUp;
        return this;
    }

    public boolean isHideLeft() {
        return hideLeft;
    }

    public Room setHideLeft(boolean hideLeft) {
        this.hideLeft = hideLeft;
        return this;
    }

    public int getLeftZ() {
        return leftZ;
    }

    public Room setLeftZ(int leftZ) {
        this.leftZ = leftZ;
        return this;
    }

    public List getUpList() {
        return upList;
    }

    public List getLeftList() {
        return leftList;
    }

    public List getRightList() {
        return rightList;
    }

    public int[][][] getWallPattern() {
        return wallPattern;
    }

    public Room setWallPattern(int[][][] wallPattern) {
        this.wallPattern = wallPattern;
        return this;
    }

    public List<SourceTile> getTiles() {
        return tiles;
    }
}
