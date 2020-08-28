package prince.uielems;

import prince.enums.LevelTypeEnum;
import prince.pojo.GuardLocation;
import prince.pojo.LevelEvent;
import prince.pojo.PrinceLocation;
import prince.pojo.Room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Level {
    private int number;
    private int height;
    private int width;
    private LevelTypeEnum levelType;

    private int[][] roomLayout;
    private Map<Integer, Room> rooms;

    private PrinceLocation princeLocation;
    private List<LevelEvent> levelEventsList;
    private List<GuardLocation> guardLocationList;

    public Level(int number, int height, int width, LevelTypeEnum levelType) {
        this.number = number;
        this.height = height;
        this.width = width;
        this.levelType = levelType;

        roomLayout = new int[height][width];
        rooms = new HashMap<>();
        levelEventsList = new ArrayList<>();
        guardLocationList = new ArrayList<>();
    }

    public int getNumber() {
        return number;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }


    public LevelTypeEnum getLevelType() {
        return levelType;
    }

    public int[][] getRoomLayout() {
        return roomLayout;
    }

    public Map<Integer, Room> getRooms() {
        return rooms;
    }

    public PrinceLocation getPrinceLocation() {
        return princeLocation;
    }

    public List<LevelEvent> getLevelEventsList() {
        return levelEventsList;
    }

    public Level setPrinceLocation(PrinceLocation princeLocation) {
        this.princeLocation = princeLocation;
        return this;
    }

    public List<GuardLocation> getGuardLocationList() {
        return guardLocationList;
    }
}
