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
        this.x = x * GameScreen.getScale();
        this.y = y * GameScreen.getScale();

        return this;
    }

    public ImageElement setMirrored(boolean value) {
        this.isMirrored = value;
        return this;
    }

    public ImageElement setDimensions(int width, int height) {
        this.width = width * GameScreen.getScale();
        this.height = height * GameScreen.getScale();
        return this;
    }

    public int[] getOriginalDimensions() {
        return new int[]{img.getWidth(), img.getHeight()};
    }

    public ImageElement setScale(float scale) {
        width *= scale * GameScreen.getScale();
        height *= scale * GameScreen.getScale();

        return this;
    }

    public int getX() {
        return x/GameScreen.getScale();
    }

    public int getY() {
        return y/GameScreen.getScale();
    }

    public int getWidth() {
        return width/GameScreen.getScale();
    }

    public int getHeight() {
        return height/GameScreen.getScale();
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
