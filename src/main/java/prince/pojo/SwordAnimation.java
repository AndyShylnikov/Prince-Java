package prince.pojo;

public class SwordAnimation {
    private int dx, dy, dz, id;

    public SwordAnimation(int dx, int dy, int id) {
        this.dx = dx;
        this.dy = dy;
        this.id = id;
    }

    public SwordAnimation setDz(int dz) {
        this.dz = dz;
        return this;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public int getDz() {
        return dz;
    }

    public int getId() {
        return id;
    }
}
