package prince.pojo;

public class Sequence {
    private int p1;
    private int p2;
    private int cmd;
    private String action;

    public int getP1() {
        return p1;
    }

    public Sequence setP1(int p1) {
        this.p1 = p1;
        return this;
    }

    public int getP2() {
        return p2;
    }

    public Sequence setP2(int p2) {
        this.p2 = p2;
        return this;
    }

    public int getCmd() {
        return cmd;
    }

    public Sequence setCmd(int cmd) {
        this.cmd = cmd;
        return this;
    }

    public String getAction() {
        return action;
    }

    public Sequence setAction(String action) {
        this.action = action;
        return this;
    }
}
