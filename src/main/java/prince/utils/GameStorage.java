package prince.utils;

import prince.Level;

import java.util.HashMap;
import java.util.Map;

public class GameStorage {
    private static GameStorage instance;
    private Map<Integer, Level> levels;

    private GameStorage() {
        levels = new HashMap<>();
    }

    public synchronized static GameStorage getInstance() {
        if (instance == null) {
            instance = new GameStorage();
        }
        return instance;
    }

    public void addLevel(Level level) {
        levels.put(level.getNumber(), level);
    }

    public Level getLevel(int number) {
        return levels.get(number);
    }
}
