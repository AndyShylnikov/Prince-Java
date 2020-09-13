package prince.loaders;

import prince.*;
import prince.enums.AnimationTypeEnum;
import prince.pojo.AnimationElement;
import prince.pojo.FrameDefine;
import prince.pojo.Sequence;
import prince.pojo.SwordAnimation;
import prince.utils.JsonHelper;

import java.io.File;
import java.util.*;

public class AnimationLoader extends BaseLoader {
    @Override
    public void loadItems() {
        File animationDir = new File(Constants.ANIMATION_PATH);
        goThroughFolder(animationDir);
    }

    private void goThroughFolder(File animationDir) {
        String[] list = animationDir.list();
        Map<String, Map<String, AnimationElement>> framedefs = new HashMap<>();
        Map<String, AnimationElement> swordSprites = new HashMap<>();
        for (String filePath : list) {

            String animationPack = filePath.substring(0, filePath.indexOf(".json"));
            Map<String, Object> map = JsonHelper.getMap(animationDir.getAbsolutePath() + "\\" + filePath);

            if (!animationPack.equals("sword")) {

                proceedSequences(map, animationPack);

                List framedefList = (List) map.get("framedef");
                proceedFrameDefs(framedefList, animationPack);
            } else {
                List swordAnimList = (List) map.get("swordtab" + GameScreen.getEnvironment());
                proceedSword(swordAnimList);
            }
        }
    }

    private void proceedSword(List swordAnimList) {
        for (Object o : swordAnimList) {
            Map swordAnim = (Map) o;
            SwordAnimation instance = new SwordAnimation(((int) swordAnim.get("dx")),
                    ((int) swordAnim.get("dy")), ((int) swordAnim.get("id")));
            if (swordAnim.get("dz") != null) {
                instance.setDz(((int) swordAnim.get("dz")));
            }
            storage.getSwordAnimations().add(instance);
        }
    }

    private void proceedFrameDefs(List framedefList, String animationPack) {
        List<FrameDefine> frameDefines = new ArrayList<>();
        framedefList.forEach((item) -> {
            Map defsMap = (Map) item;
            if (defsMap.get("comment") == null && !defsMap.isEmpty()) {
                String fcheckValue = (String) defsMap.get("fcheck");

                int fcheck = Integer.decode(fcheckValue);
                FrameDefine define = new FrameDefine(((int) defsMap.get("fdx")), ((int) defsMap.get("fdy")), fcheck);
                if (defsMap.get("fsword") != null) {
                    define.setfSword(((int) defsMap.get("fsword")));
                }
                frameDefines.add(define);
            }
        });
        storage.addFrameDef(animationPack, frameDefines);
    }

    private void proceedSequences(Map map, String animationPack) {
        Map<String, AnimationElement> sequences = new HashMap<>();

        Map<String, Object> sequence = (Map) map.get("sequence");

        sequence.forEach((key, item) -> {
            List<Sequence> sequenceList = new ArrayList<>();
            List values = (List) item;
            values.forEach(value -> {
                Sequence seqInstance = new Sequence();
                Map step = (Map) value;
                step.forEach((x, y) -> {
                    if (x.equals("p1")) {
                        if (y.getClass().equals(Integer.class)) {
                            seqInstance.setP1((Integer) y);
                        } else if (y.getClass().equals(String.class)) {
                            seqInstance.setAction(((String) y));
                        }
                    } else if (x.equals("cmd")) {
                        if (y.getClass().equals(Integer.class)) {
                            seqInstance.setCmdType(AnimationTypeEnum.values()[(int) y]);
                        }
                    } else if (x.equals("p2")) {
                        if (y.getClass().equals(Integer.class)) {
                            seqInstance.setP2((Integer) y);
                        }
                    }
                });
                sequenceList.add(seqInstance);
            });
            AnimationElement animationElement = new AnimationElement(key, sequenceList);
            sequences.put(key, animationElement);
        });
        storage.addSequences(animationPack, sequences);
    }
}
