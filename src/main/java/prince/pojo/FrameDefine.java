package prince.pojo;

public class FrameDefine {
    private int fDx;
    private int fDy;
    private int fCheck;
    private int fSword;

    public FrameDefine(int fDx, int fDy, int fCheck) {
        this.fDx = fDx;
        this.fDy = fDy;
        this.fCheck = fCheck;
    }

    public int getfDx() {
        return fDx;
    }

    public int getfDy() {
        return fDy;
    }

    public int getfCheck() {
        return fCheck;
    }

    public int getfSword() {
        return fSword;
    }

    public FrameDefine setfSword(int fSword) {
        this.fSword = fSword;
        return this;
    }
}
