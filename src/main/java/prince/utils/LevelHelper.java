package prince.utils;

import prince.Constants;
import prince.Level;
import prince.enums.LevelTypeEnum;

import java.util.Map;

public class LevelHelper {
    public static GameStorage storage = GameStorage.getInstance();

    public static void loadLevels() {

        for (int i = 1; i <= Constants.LEVELS_COUNT; i++) {
            Map<String, Object> levelsMap = JsonHelper.getMap(String.format(Constants.LEVELS_JSON_PATH_PATTERN, i));
            System.out.println(levelsMap);

            int levelNumber = (int) levelsMap.get("number");
            int width = (int) levelsMap.get("width");
            int height = (int) levelsMap.get("height");
            LevelTypeEnum levelType = LevelTypeEnum.values()[(int) levelsMap.get("type")];

            Level level = new Level(levelNumber, width, height, levelType);
            storage.addLevel(level);
        }
    }
}
