package prince.uielems;

import prince.GameScreen;
import prince.uielems.Sprite;

import java.awt.*;
import java.util.List;

public class AnimatedItem {
    protected int x, y, width, height, currentFrame;
    protected List<Sprite> animations;

    public AnimatedItem(int x, int y, int width, int height, List<Sprite> animations) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.currentFrame = 0;
        this.animations = animations;
    }

    public void draw(Graphics2D g) {
        g.drawImage(animations.get(currentFrame).getImage(),
                (int) (x* GameScreen.getHScale()),
                (int) (y*GameScreen.getVScale()),
                (int) (width*GameScreen.getHScale()),
                (int) (height*GameScreen.getVScale()),
                null);
    }

    public void update() {
        currentFrame++;
        if (currentFrame >= animations.size())
            currentFrame = 0;
    }

}
