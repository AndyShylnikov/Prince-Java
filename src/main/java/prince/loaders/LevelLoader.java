package prince.loaders;


import prince.Constants;
import prince.GameScreen;
import prince.enums.*;
import prince.pojo.GuardLocation;
import prince.pojo.LevelEvent;
import prince.pojo.PrinceLocation;
import prince.pojo.Room;
import prince.uielems.*;
import prince.utils.GameUtil;
import prince.utils.JsonHelper;
import prince.utils.Randomizator;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class LevelLoader extends BaseLoader {
    File levelFolder;
    Level level;

    private static SourceTile createSourceTilesList(Map<String, Integer> a) {
        return new SourceTile(a.get("element"), a.get("modifier"));
    }

    @Override
    public void loadItems() {
        levelFolder = new File(Constants.LEVELS_FOLDER_PATH);
        goThroughFolder(levelFolder);

    }

    private void goThroughFolder(File levelFolder) {

        if (levelFolder != null) {
            for (File file : Objects.requireNonNull(levelFolder.listFiles())) {

                parseJson(file);


            }
        }
        System.out.println();
    }

    private void parseJson(File file) {
        Map<String, Object> levelMap = JsonHelper.getMap(file.getAbsolutePath());

        int levelNum = (int) levelMap.get("number");
        System.out.println("Parsing level = " + levelNum);
        int levelHeight = (int) levelMap.get("height");
        int levelWidth = (int) levelMap.get("width");
        LevelTypeEnum levelType = LevelTypeEnum.values()[((int) levelMap.get("type"))];

        level = new Level(levelNum, levelHeight, levelWidth, levelType);
        parseRooms(((List<Map<String, Object>>) levelMap.get("rooms")));
        parseEvents((List<Map<String, Integer>>) levelMap.get("events"));
        parsePrinceLocation((Map<String, Integer>) levelMap.get("prince"));
        parseGuards((List<Map<String, Object>>) levelMap.get("guards"));
        goThroughRooms();
        storage.addLevel(level);
    }

    private void parseGuards(List<Map<String, Object>> guards) {
        for (Map<String, Object> guardMap : guards) {
            int skill = (int) guardMap.get("skill");
            int location = (int) guardMap.get("location");
            int color = (int) guardMap.get("colors");
            int direction = (int) guardMap.get("direction");
            DirectionEnum directionType = direction < 0 ? DirectionEnum.FACE_LEFT : DirectionEnum.FACE_RIGHT;

            GuardTypeEnum type = GuardTypeEnum.valueOf(((String) guardMap.get("type")).toUpperCase());
            Room room = level.getRooms().get(((int) guardMap.get("room")));
            boolean isActive = guardMap.get("active") == null || ((boolean) guardMap.get("active"));
            boolean isVisible = guardMap.get("visible") == null || ((boolean) guardMap.get("visible"));

            GuardLocation guard = new GuardLocation(room, location, skill, color, type, directionType, isActive, isVisible);
            level.getGuardLocationList().add(guard);
        }
    }

    private void parsePrinceLocation(Map<String, Integer> princeMap) {
        int direction = princeMap.get("direction");
        DirectionEnum directionType = direction < 0 ? DirectionEnum.FACE_LEFT : DirectionEnum.FACE_RIGHT;
        level.setPrinceLocation(new PrinceLocation(princeMap.get("location"), level.getRooms().get(princeMap.get("room")), directionType));
    }

    private void parseEvents(List<Map<String, Integer>> events) {
        for (Map<String, Integer> eventMap : events) {
            if (eventMap != null) {
                Room room = level.getRooms().get(eventMap.get("room"));
                int number = eventMap.get("number");
                Integer location = eventMap.get("location");
                level.getLevelEventsList().add(new LevelEvent(number, room, location, eventMap.get("next")));
            }
        }
    }

    private void goThroughRooms() {
        int[][] roomLayout = level.getRoomLayout();
        for (int y = 0; y < level.getHeight(); y++) {
            for (int x = 0; x < level.getWidth(); x++) {
                int roomId = roomLayout[y][x];
                if (roomId >= 0) {
                    Room currentRoom = level.getRooms().get(roomId);
                    makeRoomLinks(currentRoom, x, y);
                    checkLeftRoom(currentRoom);
                    buildRoomWithTiles(currentRoom);
                    checkRightRoom(currentRoom);
                    checkUpRoom(currentRoom);

                }
            }
        }
        for (int i = 0; i < level.getRooms().size(); i++) {
            if (level.getRooms().get(i) != null) {
                for (int j = 0; j < Constants.ROOM_WIDTH_ITEMS * Constants.ROOM_HEIGHT_ITEMS - 1; j++) {
                    SourceTile tile = level.getRooms().get(i).getTiles().get(j);
                    try {
                        tile.getClass().getField("isSpace");
                    } catch (NoSuchFieldException e) {
                        tile = buildTile(j % 10, j / 10, level.getRooms().get(i));
                        addTile(j % 10, j / 10, (BaseTile) tile);
                    }
                }
            }
        }

    }

    private void checkUpRoom(Room currentRoom) {
        if (currentRoom.getUpRoom() == null) {
            for (int x = 0; x <= 9; x++) {
                BaseTile tile = new BaseTile(TileTypeEnum.TILE_FLOOR.ordinal(), 0, currentRoom, level);
                addTile(x, -1, tile);
            }
        }
    }

    private void addTile(int x, int y, BaseTile tile) {
        Room room = tile.getRoom();
        tile.setX(room.getX() * Constants.ROOM_WIDTH + x * Constants.BLOCK_WIDTH).
                setY(room.getY() * Constants.ROOM_HEIGHT + x * Constants.BLOCK_HEIGHT - 13);
        if (x >= 0 && y >= 0) {
            room.getTiles().remove(y * 10 + x);
            room.getTiles().add(y * 10 + x, tile);
            tile.setRoomX(x).setRoomY(y);

        } else if (y == -1) {
            room.getUpList().add(tile);
        } else if (x == -1) {
            room.getLeftList().add(tile);
        } else if (x == -2) {
            tile.setX(room.getX() * Constants.ROOM_WIDTH + 9 * Constants.BLOCK_WIDTH);
            room.getRightList().add(tile);
        }
    }

    private void checkRightRoom(Room currentRoom) {
        if (currentRoom.getRightRoom() == null) {
            for (int y = 2; y >= 0; y--) {
                int rightTile = y * 10 + 9;
                BaseTile tile;
                if (currentRoom.getTiles().get(rightTile).getElement() == TileTypeEnum.TILE_WALL.ordinal()) {
                    tile = new BaseTile(TileTypeEnum.TILE_WALL.ordinal(), 0, currentRoom, level);
                    tile.setBack(level.getLevelType().toString().toLowerCase() + "_wall_0");
                    tile.setFront("");
                } else {
                    tile = new BaseTile(TileTypeEnum.TILE_SPACE.ordinal(), 0, currentRoom, level);
                }
                addTile(-2, y, tile);

            }
        }

    }

    private void buildRoomWithTiles(Room currentRoom) {
        System.out.println("Room id = " + currentRoom.getRoomId());

        for (int y = 2; y >= 0; y--) {
            for (int x = 0; x <= 9; x++) {
                BaseTile tile = buildTile(x, y, currentRoom);
                addTile(x, y, tile);
            }
        }
    }

    private void checkLeftRoom(Room currentRoom) {
        if (currentRoom.getLeftRoom() == null) {
            for (int y = 2; y >= 0; y--) {
                BaseTile tile = new BaseTile(TileTypeEnum.TILE_WALL.ordinal(), 0, currentRoom, level);
                tile.setBack(level.getLevelType().toString().toLowerCase() + "_wall_0");
                tile.setFront("");
                addTile(-1, y, tile);
            }
        }
    }

    private void makeRoomLinks(Room currentRoom, int x, int y) {
        currentRoom.setUpRoom(getRoomFromLayout(x, y - 1)).
                setDownRoom(getRoomFromLayout(x, y + 1)).
                setLeftRoom(getRoomFromLayout(x - 1, y)).
                setRightRoom(getRoomFromLayout(x + 1, y));
    }

    private Room getRoomFromLayout(int x, int y) {
        if (x >= 0 && y >= 0 && x < level.getWidth() && y < level.getHeight()) {
            int roomId = level.getRoomLayout()[y][x];
            if (roomId >= 0) {
                return level.getRooms().get(roomId);
            }
        }
        return null;
    }

    private void parseRooms(List<Map<String, Object>> roomsList) {
        for (int y = 0; y < level.getHeight(); y++) {
            for (int x = 0; x < level.getWidth(); x++) {
                int roomIndex = y * level.getWidth() + x;

                Map<String, Object> roomMap = roomsList.get(roomIndex);
                int roomId = ((int) roomMap.get("id"));

                level.getRoomLayout()[y][x] = roomId;
                if (roomId >= 0) {
                    List<SourceTile> sourceTiles = generateSourceTilesList((List<Map<String, Integer>>) roomMap.get("tiles"));
                    Room room = new Room(roomId, level, sourceTiles);
                    room.setX(x).setY(y);
                    if (level.getLevelType() == LevelTypeEnum.PALACE) {
                        room.setWallPattern(generateWallPattern(roomId));
                    }
                    level.getRooms().put(roomId, room);
                }
            }
        }
    }

    private List<SourceTile> generateSourceTilesList(List<Map<String, Integer>> tiles) {
        return tiles.stream().map(LevelLoader::createSourceTilesList).collect(Collectors.toList());
    }

    private int[][][] generateWallPattern(int roomId) {
        int[][][] wallPattern = new int[3][4][11];
        Randomizator randomizator = Randomizator.getInstance();
        randomizator.setSeed(roomId);
        randomizator.getRandom(1);
        for (int row = 0; row <= 2; row++) {
            for (int subRow = 0; subRow <= 3; subRow++) {
                int colorBase = subRow % 2 == 0 ? 4 : 0;
                int previousColor = -1;
                int color = 0;
                for (int colo = 0; colo <= 10; colo++) {
                    do {
                        color = colorBase + randomizator.getRandom(3);
                    } while (color == previousColor);
                    wallPattern[row][subRow][colo] = color;
                    previousColor = color;
                }
            }
        }
        return wallPattern;
    }

    private BaseTile buildTile(int x, int y, Room room) {
        int tileNumber = y * 10 + x;
        SourceTile t = room.getTiles().get(tileNumber);
        BaseTile tile = new BaseTile(t.getElement(), t.getModifier(), room, level);
        int tileSeed = 0;
        TileTypeEnum element = TileTypeEnum.values()[t.getElement()];
        if (element == TileTypeEnum.TILE_WALL) {
            tileSeed = tileNumber + room.getRoomId();
            String wallType = getTileAt(x - 1, y, room).getElement() == TileTypeEnum.TILE_WALL.ordinal() ? "W" : "S";
            wallType += "W";
            wallType += getTileAt(x + 1, y, room).getElement() == TileTypeEnum.TILE_WALL.ordinal() ? "W" : "S";
            tile.setFront(wallType + "_" + tileSeed);
            if (wallType.substring(1, 2).equals("S")) {
                tile.setBack(level.getLevelType().toString().toLowerCase() + "_wall_" + t.getModifier());
            }
        } else if (element == TileTypeEnum.TILE_FLOOR || element == TileTypeEnum.TILE_SPACE) {
            tile.getChildBack().setFrameName(level.getLevelType().toString().toLowerCase() + "_" + t.getElement() + "_" + t.getModifier());
        } else if (element == TileTypeEnum.TILE_STUCK_BUTTON) {
            tile.setBack(level.getLevelType().toString().toLowerCase() + "_1");
            tile.setFront(tile.getBack() + "_fg");
        } else if (element == TileTypeEnum.TILE_RAISE_BUTTON || element == TileTypeEnum.TILE_DROP_BUTTON) {
            tile = new TileButton(tile);
        } else if (element == TileTypeEnum.TILE_SWORD) {
            tile = new TileSword(tile);
        } else if (element == TileTypeEnum.TILE_GATE) {
            tile = new TileGate(tile);
        } else if (element == TileTypeEnum.TILE_SPIKES) {
            tile = new TileSpikes(tile);
        } else if (element == TileTypeEnum.TILE_SLICER) {
            tile = new TileSlicer(tile);
        } else if (element == TileTypeEnum.TILE_LOOSE_BOARD) {
            tile = new TileLooseBoard(tile);
        } else if (element == TileTypeEnum.TILE_EXIT_RIGHT) {
            tile = GameScreen.getEnvironment().equals("dos") ? new TileExitDoor(tile, 9, 8) : new TileExitDoor(tile, 8, 10);
        } else if (element == TileTypeEnum.TILE_TORCH || element == TileTypeEnum.TILE_TORCH_WITH_DEBRIS) {
            tile.getChildBack().setFrames(GameUtil.generateFrameNames("fire_", 1, 9, "", true, 1));
            if (level.getLevelType() == LevelTypeEnum.PALACE && GameScreen.getEnvironment().equals("mac")) {
                tile.getChildBack().setX(41).setY(16);
            } else {
                tile.getChildBack().setX(40).setY(18);

            }

        } else if (element == TileTypeEnum.TILE_POTION) {
            String[] colors = {"red", "red", "red", "green", "green", "blue", "blue"};
            String potion = tile.getFront() + "_1";
            if (GameScreen.getEnvironment().equals("mac")) {
                if (t.getModifier() == 2) {
                    potion = tile.getFront() + "_2";
                }
            } else {
                if (t.getModifier() > 1 && t.getModifier() < 5) {
                    potion = tile.getFront() + "_2";
                }
            }
            tile.setFront(potion);
            int px, py;
            if (GameScreen.getEnvironment().equals("mac")) {
                if (t.getModifier() == PotionEnum.POTION_LIFE.ordinal()) {
                    tile.getChildFront().setFrames(GameUtil.generateFrameNames("bubble_", 1, 6, "_" + colors[t.getModifier()], false, 2));
                }
                px = 20;
                py = 52;
            } else {
                tile.getChildFront().setFrames(GameUtil.generateFrameNames("bubble_", 1, 7, "_" + colors[t.getModifier()], true, 1));
                px = 25;
                py = 53;
                if (t.getModifier() > PotionEnum.POTION_HEALTH.ordinal() && t.getModifier() < PotionEnum.POTION_POISON.ordinal()) {
                    py -= 4;
                }
            }
            tile.getChildFront().setX(px).setY(py);
            tile.setHasObject(true);
        } else if (element == TileTypeEnum.TILE_TAPESTRY) {
            if (level.getLevelType() == LevelTypeEnum.PALACE && t.getModifier() > 0) {
                tile.setBack(level.getLevelType().toString().toLowerCase() + "_" + t.getElement() + "_" + t.getModifier());
                tile.setFront(tile.getBack() + "_fg");
            }
        } else if (element == TileTypeEnum.TILE_TAPESTRY_TOP) {
            if (level.getLevelType() == LevelTypeEnum.PALACE && t.getModifier() > 0) {
                tile.setBack(level.getLevelType().toString().toLowerCase() + "_" + t.getElement() + "_" + t.getModifier());
                tile.setFront(tile.getBack() + "_fg");
            }
            if (getTileAt(x - 1, y, room).getElement() == TileTypeEnum.TILE_LATTICE_SUPPORT.ordinal()) {
                tile.getChildBack().setFrameName(level.getLevelType().toString().toLowerCase() + "_" + TileTypeEnum.TILE_LATTICE_SUPPORT + "_fg");
            }
        } else if (element == TileTypeEnum.TILE_BALCONY_RIGHT) {
            tile.getChildBack().setFrameName(level.getLevelType().toString().toLowerCase() + "_balcony").setY(-4);
        }
        return tile;
    }

    private SourceTile getTileAt(int x, int y, Room currentRoom) {
        Room room = null;
        if (x < 0) {
            room = getRoomFromLayout(currentRoom.getX() - 1, currentRoom.getY());
            x += 10;
        }
        if (x > 9) {
            room = getRoomFromLayout(currentRoom.getX() + 1, currentRoom.getY());
            x -= 10;
        }
        if (y < 0) {
            room = getRoomFromLayout(currentRoom.getX(), currentRoom.getY() - 1);
            y += 3;
        }
        if (y > 2) {
            room = getRoomFromLayout(currentRoom.getX(), currentRoom.getY() + 1);
            y -= 3;
        }
        if (room == null)
            return new BaseTile(TileTypeEnum.TILE_WALL.ordinal(), 0, currentRoom, level);

        return room.getTiles().get(x + y * 10);
    }
}