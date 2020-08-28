package prince.pojo;

import prince.enums.DirectionEnum;
import prince.enums.GuardTypeEnum;

public class GuardLocation {
    private Room room;
    private int location;
    private int skill;
    private int color;
    private GuardTypeEnum type;
    private DirectionEnum direction;
    private boolean isActive;
    private boolean isVisible;

    public GuardLocation(Room room, int location, int skill, int color, GuardTypeEnum type, DirectionEnum direction, boolean isActive, boolean isVisible) {
        this.room = room;
        this.location = location;
        this.skill = skill;
        this.color = color;
        this.type = type;
        this.direction = direction;
        this.isActive = isActive;
        this.isVisible = isVisible;
    }

    public Room getRoom() {
        return room;
    }

    public int getLocation() {
        return location;
    }

    public int getSkill() {
        return skill;
    }

    public int getColor() {
        return color;
    }

    public GuardTypeEnum getType() {
        return type;
    }

    public DirectionEnum getDirection() {
        return direction;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isVisible() {
        return isVisible;
    }
}
