package prince.uielems;

import prince.pojo.SceneSequenceItem;

import java.util.List;

public class Scene {
    private int sceneNumber;
    private List<SceneSequenceItem> sequenceList;

    public Scene(int sceneNumber, List<SceneSequenceItem> sequenceList) {
        this.sceneNumber = sceneNumber;
        this.sequenceList = sequenceList;
    }

    public int getSceneNumber() {
        return sceneNumber;
    }

    public List<SceneSequenceItem> getSequenceList() {
        return sequenceList;
    }
}
