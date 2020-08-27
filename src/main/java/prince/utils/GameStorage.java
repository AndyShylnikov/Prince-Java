package prince.utils;

import prince.Level;
import prince.uielems.Sprite;

import java.util.HashMap;
import java.util.Map;

public class GameStorage {
    private static GameStorage instance;
    private Map<Integer, Level> levels;
    private Map<String, Sprite> sprites;

    private GameStorage() {
        levels = new HashMap<>();
        sprites = new HashMap<>();

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

    public void addSprite(Sprite sprite) {
        sprites.put(sprite.getName(), sprite);
    }

    public Sprite getSprite(String name) {
        return sprites.get(name);
    }
}
