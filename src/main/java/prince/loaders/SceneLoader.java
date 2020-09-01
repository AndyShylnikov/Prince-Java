package prince.loaders;

import prince.Constants;
import prince.enums.DirectionEnum;
import prince.enums.SceneActionEnum;
import prince.pojo.SceneSequenceItem;
import prince.uielems.Scene;
import prince.utils.JsonHelper;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class SceneLoader extends BaseLoader {

    @Override
    public void loadItems() {
        File sceneFolder = new File(Constants.SCENES_FOLDER_PATH);
        goThroughFolder(sceneFolder);
    }

    private void goThroughFolder(File sceneFolder) {

        if (sceneFolder != null) {
            for (File file : Objects.requireNonNull(sceneFolder.listFiles())) {
                parseJson(file);
            }
            System.out.println();
        }
    }

    private void parseJson(File file) {
        int sceneNum = Integer.parseInt(file.getName().replace("scene", "").replace(".json", ""));
        System.out.println("Creating " + sceneNum + " scene");
        Map<String, Object> sceneMap = JsonHelper.getMap(file.getAbsolutePath());
        List<SceneSequenceItem> sequenceItems = ((List<Map>) sceneMap.get("program")).stream().
                map(this::createSequenceItem).collect(Collectors.toList());
        storage.addScene(new Scene(sceneNum, sequenceItems));
    }

    private SceneSequenceItem createSequenceItem(Map<String, Object> sceneSequence) {
        SceneActionEnum actionType = SceneActionEnum.valueOf((String) sceneSequence.get("i"));

        SceneSequenceItem item = new SceneSequenceItem(actionType);
        switch (actionType) {
            case ADD_ACTOR:
                int actorId = (int) sceneSequence.get("p1");
                String actorName = (String) sceneSequence.get("p2");
                int x = (int) sceneSequence.get("p3");
                int y = (int) sceneSequence.get("p4");
                int direction = (int) sceneSequence.get("p5");
                item.setActorId(actorId).
                        setActionName(actorName).setX(x).setY(y).
                        setDirection(direction <= 0 ? DirectionEnum.FACE_LEFT : DirectionEnum.FACE_RIGHT);
                break;
            case START:
            case END:
            case EFFECT:
//                    These actions have no additional params
                break;
            case WAIT:
                int seconds = (int) sceneSequence.get("p1");
                item.setSeconds(seconds);
                break;
            case ACTION:
                actorId = (int) sceneSequence.get("p1");
                String actionName = (String) sceneSequence.get("p2");
                item.setActorId(actorId).setActorName(actionName);
                break;
            case PLAY_SONG:
                String songName = (String) sceneSequence.get("p1");
                item.setSongName(songName);
                break;
            case REM_ACTOR:
                actorId = (int) sceneSequence.get("p1");
                item.setActorId(actorId);
                break;
            case ADD_OBJECT:
                x = (int) sceneSequence.get("p3");
                y = (int) sceneSequence.get("p4");
                int state = (int) sceneSequence.get("p2");
                item.setX(x).setY(y).setState(state);
                break;
            case PLAY_SOUND:
                String soundName = (String) sceneSequence.get("p1");
                item.setSoundName(soundName);
                break;
            case START_OBJECT:
                int objectId = (int) sceneSequence.get("p1");
                item.setObjectId(objectId);
                break;

        }
        return item;

    }
}
