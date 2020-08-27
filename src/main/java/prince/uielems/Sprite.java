package prince.uielems;

import java.awt.image.BufferedImage;

public class Sprite {
    private int width;
    private int height;
    private BufferedImage image;
    private String name;

    public Sprite(int width, int height, BufferedImage image, String name) {
        this.width = width;
        this.height = height;
        this.image = image;
        this.name = name;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public BufferedImage getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Sprite{" +
                "width=" + width +
                ", height=" + height +
                ", image=" + image +
                '}';
    }
}
