package prince.pojo;

public class LevelEvent {
    private int number;
    private Room room;
    private int location;
    private int next;

    public LevelEvent(int number, Room room, int location, int next) {
        this.number = number;
        this.room = room;
        this.location = location;
        this.next = next;
    }

    public int getNumber() {
        return number;
    }

    public Room getRoom() {
        return room;
    }

    public int getLocation() {
        return location;
    }

    public int getNext() {
        return next;
    }

}
