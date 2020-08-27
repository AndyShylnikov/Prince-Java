package prince.utils;

import prince.pojo.AnimationElement;
import prince.pojo.FrameDefine;
import prince.Level;
import prince.pojo.SwordAnimation;
import prince.uielems.Sprite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameStorage {
    private static GameStorage instance;
    private Map<Integer, Level> levels;
    private Map<String, Sprite> sprites;
    private Map<String, Map<String, AnimationElement>> sequencesMap = new HashMap<>();
    private Map<String, List<FrameDefine>> frameDefs = new HashMap();
    private List<SwordAnimation> swordAnimations = new ArrayList<>();

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


    public void addSequences(String name, Map<String, AnimationElement> map) {
        sequencesMap.put(name, map);

    }

    public Map<String, AnimationElement> getSequences(String name) {
        return sequencesMap.get(name);
    }

    public List<FrameDefine> getFrameDef(String name) {
        return frameDefs.get(name);
    }

    public void addFrameDef(String name, List<FrameDefine> frameDefines) {
        frameDefs.put(name, frameDefines);
    }
    public List<SwordAnimation> getSwordAnimations(){
        return swordAnimations;
    }


}
