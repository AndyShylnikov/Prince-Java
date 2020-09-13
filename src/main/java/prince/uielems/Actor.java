package prince.uielems;

import prince.Constants;
import prince.GameScreen;
import prince.enums.AnimationTypeEnum;
import prince.enums.DirectionEnum;
import prince.pojo.*;
import prince.utils.GameStorage;
import prince.utils.GameUtil;

import java.awt.*;
import java.util.Map;

public class Actor {
    protected GameStorage storage = GameStorage.getInstance();
    protected String charName;
    protected int frame;
    protected String frameName;

    protected boolean haveSword;
    protected boolean alive;
    protected boolean isWeightless;
    protected boolean swordDrawn;
    protected int charXVel;
    protected int charYVel;
    protected DirectionEnum face;

    protected boolean moveLeft = false;
    protected Actor opponent;
    protected Room room;
    protected int blockX;
    protected int blockY;
    protected int charX;
    protected int charY;
    protected int x;
    protected int y;
    protected int z;

    protected int saveX;
    protected int saveY;

    protected int baseX;
    protected int baseY;

    protected float charFdx;
    protected float charFdy;
    protected boolean charFcheck;
    protected float charFfoot;
    protected boolean charFood;
    protected boolean charFthin;
    protected boolean charSword;
    protected String charAction;
    protected int seqPointer;
    protected boolean leapOfFaith;
    private Map<String, AnimationElement> animations;
    private int actionCode;
    private int health;
    private Object sword;
    private int swordFrame;
    private int swordDy;
    private int swordDx;
    private int swordDz;
    private boolean flee;
    private boolean droppedOut;
    private boolean visible;
    private Level level;


    public Actor(String name, DirectionEnum face, int x, int y) {
        charName = name;
        frame = 16;
        frameName = name + "-" + frame;
        haveSword = true;
        alive = true;
        this.face = face;
        charX = x;
        charY = y;
        z = 20;
        charAction = "stand";
        animations = storage.getSequences(name);
    }

    public void draw(Graphics2D g) {
        Sprite sprite = storage.getSprite(frameName);
        g.drawImage(sprite.getImage(), (int) (x * GameScreen.getHScale()),
                (int) (y * GameScreen.getVScale()),
                ((int) (sprite.getWidth() * GameScreen.getHScale())),
                ((int) (sprite.getHeight() * GameScreen.getVScale())),

                null);
    }

    public void draw(Graphics2D g, int x, int y) {
        Sprite sprite = storage.getSprite(frameName);

        int width = (int) (sprite.getWidth() * GameScreen.getHScale());
        width = faceL() ? width : -width;
        x = faceL() ? x : x + Math.abs(width) ;
        g.drawImage(sprite.getImage(), x, y,
                width,
                ((int) (sprite.getHeight() * GameScreen.getVScale())),
                null);
    }

    public void update() {
        processCommand();
        updatePosition();
    }

    private void processCommand() {
        boolean isCommand = true;
        while (isCommand) {
            AnimationElement animation = animations.get(charAction);
            if (animation == null)
                break;
            Sequence sequence = animation.getSequences().get(seqPointer);
            AnimationTypeEnum command = sequence.getCmdType();
            switch (command) {
                case CMD_FRAME:
                    frame = sequence.getP1();
                    updateFrame();
                    isCommand = false;
                    break;
                case CMD_NEXTLEVEL:
                    break;
                case CMD_TAP:
                    switch (sequence.getP1()) {
                        case 0:
//                            TODO: alert guard
                            break;
                        case 1:
//                            PlaySound("footstep", true)
                            break;
                        case 2:
//                            PlaySound("smack-wall")
                            break;
                    }
                    break;
                case CMD_EFFECT:
                    break;
                case CMD_JARD:
                    break;
                case CMD_JARU:
                    break;
                case CMD_DIE:
                    health = 0;
                    alive = false;
                    swordDrawn = false;
                    break;
                case CMD_IFWTLESS:
                    break;
                case CMD_SETFALL:
                    charXVel = face == DirectionEnum.FACE_LEFT ? sequence.getP1() * -1 : sequence.getP1();
                    charYVel = sequence.getP2();
                    break;
                case CMD_ACT:
                    actionCode = sequence.getP1();
                    break;
                case CMD_CHY:
                    charY += sequence.getP1();
                    break;
                case CMD_CHX:
                    charX += face == DirectionEnum.FACE_LEFT ? -sequence.getP1() : sequence.getP1();
                    break;
                case CMD_DOWN:
                    blockY++;
                    break;
                case CMD_UP:
                    blockY--;
                    break;
                case CMD_ABOUTFACE:
                    face = face == DirectionEnum.FACE_LEFT ? DirectionEnum.FACE_RIGHT : DirectionEnum.FACE_LEFT;
                    break;
                case CMD_GOTO:
                    charAction = sequence.getAction();
                    seqPointer = sequence.getP2() - 1;
                    break;
            }
            seqPointer++;
        }

    }


    public void updatePosition() {
        if (room == null && sword != null)
            return;
        frameName = charName + "-" + frame;
        updateBlockXY();
        float tempX = face == DirectionEnum.FACE_LEFT ? charX - charFdx : charX + charFdx;
        if ((charFood && face == DirectionEnum.FACE_LEFT) || (!charFood && face == DirectionEnum.FACE_RIGHT))
            tempX += 0.5f;
        x = baseX + GameUtil.convertX(tempX);
        y = (int) (baseY + charY + charFdy);

    }

    public void updateFrame() {
        FrameDefine frameDefine = storage.getFrameDef(charName).get(frame);
        charFdx = frameDefine.getfDx();
        charFdy = frameDefine.getfDy();
        int fCheck = frameDefine.getfCheck();
        charFfoot = fCheck & 0x1F;
        charFood = (fCheck & 0x80) == 0x80;
        charFcheck = (fCheck & 0x40) == 0x40;
        charFthin = (fCheck & 0x20) == 0x20;
        charSword = frameDefine.getfSword() > 0;
        if (charSword) {
            SwordAnimation stab = storage.getSwordAnimations().get(frameDefine.getfSword() - 1);
            swordFrame = stab.getId();
            swordDx = stab.getDx();
            swordDy = stab.getDy();
            swordDz = stab.getDz() > 0 ? stab.getDz() : -1;
        }

    }

    public void updateBlockXY() {
        float footX = (face == DirectionEnum.FACE_LEFT ? charX - charFdx + charFfoot : charX - charFdx - charFfoot);
        float footY = charY + charFdy;

        blockX = GameUtil.convertXtoBlockX(footX);
        blockY = GameUtil.convertYtoBlockY(footY - 3);
        if (blockX < 0 && charX < 0) {
            charX += 140;
            baseX -= 320;
            blockX = 9;
            room = room.getLeftRoom();
            if (charName.equals("kid") && flee && opponent != null && !canSeeOpponent()) {
                if (opponent.blockX > 3 || blockY != opponent.blockY) {

                    flee = false;
                    droppedOut = false;
                    opponent = null;
                }
            }
        } else if (blockX > 9 && room != null) {
            charX -= 140;
            baseX += 320;
            blockX = 0;
            room = room.getRightRoom();
            if (charName.equals("kid") && flee && opponent != null && !canSeeOpponent()) {
                if (opponent.blockX < 6 || blockY != opponent.blockY) {
                    flee = false;
                    droppedOut = false;
                    opponent = null;
                }
            }
        }
    }


    public void updateVelocity() {
        charX += charXVel;
        charY += charYVel;
    }

    public void updateAcceleration() {
        if (actionCode == 4)// freefall{
            if (isWeightless) {
                charYVel += Constants.GRAVITY_WEIGHTLESS;
                if (charYVel > Constants.TOP_SPEED_WEIGHTLESS) {
                    charYVel += Constants.TOP_SPEED_WEIGHTLESS;
                } else
                    charYVel += Constants.GRAVITY_NORMAL;
                if (charYVel > Constants.TOP_SPEED_NORMAL) {
                    charYVel = Constants.TOP_SPEED_NORMAL;
                }
            }
    }


    public void checkFloor() {
        if (charAction.equals("climbdown") || charAction.equals("climbup") || room == null || !alive || !visible)
            return;


    }

    public void checkSlicer() {
    }

    public void checkSpikes() {
    }

    public void trySpikes() {
    }

    public void movingTo() {
    }

    public void bumpFall() {
    }

    public String action(String action) {
        if (!action.equals("")) {
            charAction = action;
            seqPointer = 0;
        }
        return charAction;
    }

    public boolean frameId(int fromId, int toId) {
        return toId == -1 ? frame == fromId : (frame >= fromId && frame <= toId);
    }

    public boolean frameId(int fromId) {
        return frameId(fromId, -1);
    }

    public boolean faceL() {
        return !faceR();
    }

    public boolean faceR() {
        return face == DirectionEnum.FACE_RIGHT;
    }

    public void distanceToFloor() {
    }

    public void distanceToEdge() {
    }

    public void checkFight() {
    }

    public void checkFightBarrier() {
    }

    public void bumpFighter() {
    }

    public void engarde() {
    }

    public void turnEngarde() {
    }

    public void sheathe() {
    }

    public void retreat() {
    }

    public void advance() {
    }

    public void strike() {
    }

    public void stabbed() {
    }

    public void opponentDistance() {
    }

    public void updateSwordPosition() {
    }

    public boolean canSeeOpponent() {
        return false;
    }


    public void canReachOpponent() {
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return storage.getSprite(frameName).getWidth();
    }

    public int getHeight() {
        return storage.getSprite(frameName).getHeight();
    }

    public String getFrameName() {
        return frameName;
    }
}