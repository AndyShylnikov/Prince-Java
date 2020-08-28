package prince.enums;

public enum DirectionEnum {
    FACE_LEFT(-1),
    FACE_RIGHT(1);

    private final int direction;

     DirectionEnum(int direction) {
        this.direction = direction;
    }

    public int getDirection() {
        return direction;
    }

}
