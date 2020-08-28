package prince.pojo;

import prince.enums.DirectionEnum;

public class PrinceLocation {
    private int location;
    private Room room;
    private DirectionEnum direction;

    public PrinceLocation(int location, Room room, DirectionEnum direction) {
        this.location = location;
        this.room = room;
        this.direction = direction;
    }

    public int getLocation() {
        return location;
    }

    public Room getRoom() {
        return room;
    }

    public DirectionEnum getDirection() {
        return direction;
    }
}
