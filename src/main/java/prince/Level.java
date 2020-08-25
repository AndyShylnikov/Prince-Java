package prince;

import prince.enums.LevelTypeEnum;

public class Level {
    private int number;
    private int height;
    private int width;
    private LevelTypeEnum levelType;

    public Level(int number, int height, int width, LevelTypeEnum levelType) {
        this.number = number;
        this.height = height;
        this.width = width;
        this.levelType = levelType;
    }

    public int getNumber() {
        return number;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }


    public LevelTypeEnum getLevelType() {
        return levelType;
    }
}
