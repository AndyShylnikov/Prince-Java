package prince.uielems;

public class TileChild {
    int x, y, height;
    boolean visible;
    String frameName;
    String[] frames;

    public int getX() {
        return x;
    }

    public TileChild setX(int x) {
        this.x = x;
        return this;
    }

    public int getY() {
        return y;
    }

    public TileChild setY(int y) {
        this.y = y;
        return this;
    }

    public int getHeight() {
        return height;
    }

    public TileChild setHeight(int height) {
        this.height = height;
        return this;
    }

    public boolean isVisible() {
        return visible;
    }

    public TileChild setVisible(boolean visible) {
        this.visible = visible;
        return this;
    }

    public String getFrameName() {
        return frameName;
    }

    public TileChild setFrameName(String frameName) {
        this.frameName = frameName;
        return this;
    }

    public String[] getFrames() {
        return frames;
    }

    public TileChild setFrames(String[] frames) {
        this.frames = frames;
        return this;
    }
}
