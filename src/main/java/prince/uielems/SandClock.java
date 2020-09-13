package prince.uielems;

import prince.GameScreen;
import prince.utils.GameStorage;
import prince.utils.GameUtil;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SandClock extends AnimatedItem {
    private GameStorage storage = GameStorage.getInstance();
    private List<Sprite> sandAnimations;
    private int currentSandFrame;
    private boolean isActive;
    private int step;

    private int sandWidth, sandHeight;

    public SandClock(int x, int y) {
        super(x, y, 0, 0, null);
        this.animations = Stream.of(GameUtil.generateFrameNames("clock0", 1, 7, "", false)).
                map(name -> storage.getSprite(name)).collect(Collectors.toList());
        width = animations.get(0).getWidth();
        height = animations.get(0).getHeight();
        sandAnimations = Stream.of(GameUtil.generateFrameNames("clocksand0", 1, 3, "", false)).
                map(name -> storage.getSprite(name)).collect(Collectors.toList());
        sandWidth = sandAnimations.get(0).getWidth();
        sandHeight = sandAnimations.get(0).getHeight();
    }

    public boolean isActive() {
        return isActive;
    }

    public SandClock setActive(boolean active) {
        isActive = active;
        return this;
    }

    public void update() {
        if (isActive) {
            currentSandFrame = (currentSandFrame + 1) % 3;
            step++;
            if (step == 40) {
                currentFrame = (currentFrame + 1) % 7;
                step = 0;
            }
        }
    }

    public void draw(Graphics2D g) {
        if (isActive) {
            g.drawImage(animations.get(currentFrame).getImage(),
                    (int) (x * GameScreen.getHScale()),
                    (int) (y * GameScreen.getVScale()),
                    (int) (width * GameScreen.getHScale()),
                    (int) (height * GameScreen.getVScale()),
                    null);
            g.drawImage(sandAnimations.get(currentSandFrame).getImage(),
                    (int) ((x+8) * GameScreen.getHScale()),
                    (int) ((y+16) * GameScreen.getVScale()),
                    (int) (sandWidth * GameScreen.getHScale()),
                    (int) (sandHeight * GameScreen.getVScale()),
                    null);
        }
    }


}
