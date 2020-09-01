package prince.pojo;

import prince.enums.DirectionEnum;
import prince.enums.SceneActionEnum;

public class SceneSequenceItem {
    private SceneActionEnum actionType;
    private int actorId, x, y, seconds, state, objectId;
    private String actorName;
    private String actionName;
    private String soundName;
    private String songName;
    private DirectionEnum direction;


    public SceneSequenceItem(SceneActionEnum actionType) {
        this.actionType = actionType;
    }

    public SceneActionEnum getActionType() {
        return actionType;
    }

    public int getActorId() {
        return actorId;
    }

    public SceneSequenceItem setActorId(int actorId) {
        this.actorId = actorId;
        return this;
    }

    public int getX() {
        return x;
    }

    public SceneSequenceItem setX(int x) {
        this.x = x;
        return this;
    }

    public int getY() {
        return y;
    }

    public SceneSequenceItem setY(int y) {
        this.y = y;
        return this;
    }

    public int getSeconds() {
        return seconds;
    }

    public SceneSequenceItem setSeconds(int seconds) {
        this.seconds = seconds;
        return this;
    }

    public String getActorName() {
        return actorName;
    }

    public SceneSequenceItem setActorName(String actorName) {
        this.actorName = actorName;
        return this;
    }

    public String getActionName() {
        return actionName;
    }

    public SceneSequenceItem setActionName(String actionName) {
        this.actionName = actionName;
        return this;
    }

    public String getSoundName() {
        return soundName;
    }

    public SceneSequenceItem setSoundName(String soundName) {
        this.soundName = soundName;
        return this;
    }

    public String getSongName() {
        return songName;
    }

    public SceneSequenceItem setSongName(String songName) {
        this.songName = songName;
        return this;
    }

    public DirectionEnum getDirection() {
        return direction;
    }

    public SceneSequenceItem setDirection(DirectionEnum direction) {
        this.direction = direction;
        return this;
    }

    public int getState() {
        return state;
    }

    public SceneSequenceItem setState(int state) {
        this.state = state;
        return this;
    }

    public int getObjectId() {
        return objectId;
    }

    public SceneSequenceItem setObjectId(int objectId) {
        this.objectId = objectId;
        return this;
    }
}
