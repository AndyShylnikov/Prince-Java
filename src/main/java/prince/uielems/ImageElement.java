package prince.uielems;

import prince.GameScreen;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ImageElement {
    private BufferedImage img;
    private int x, y, width, height;
    private boolean isMirrored;


    public ImageElement(String source) {
        try {
            img = ImageIO.read(new FileInputStream(new File(source)));
            width = img.getWidth();
            height = img.getHeight();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ImageElement(Sprite sprite) {
        img = sprite.getImage();
        width = img.getWidth();
        height = img.getHeight();
    }

    public void draw(Graphics2D g) {
        int scaleX = isMirrored ? -1 : 1;
        g.drawImage(img, x, y, width, height, null);
    }

    public ImageElement setPosition(int x, int y) {
        this.x = (int) (x * GameScreen.getHScale());
        this.y = (int) (y * GameScreen.getVScale());

        return this;
    }

    public ImageElement setMirrored(boolean value) {
        this.isMirrored = value;
        return this;
    }

    public ImageElement setDimensions(int width, int height) {
        this.width = (int) (width * GameScreen.getHScale());
        this.height = (int) (height * GameScreen.getVScale());
        return this;
    }

    public int[] getOriginalDimensions() {
        return new int[]{img.getWidth(), img.getHeight()};
    }

    public ImageElement setScale(float scale) {
        width *= scale * GameScreen.getHScale();
        height *= scale * GameScreen.getVScale();

        return this;
    }

    public int getX() {
        return (int) (x/GameScreen.getHScale());
    }

    public int getY() {
        return (int) (y/GameScreen.getVScale());
    }

    public int getWidth() {
        return (int) (width/GameScreen.getHScale());
    }

    public int getHeight() {
        return (int) (height/GameScreen.getVScale());
    }

    @Override
    public String toString() {
        return "ImageElement{" +
                ", x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                ", isMirrored=" + isMirrored +
                '}';
    }
}
