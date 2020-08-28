package prince.utils;

import java.util.ArrayList;
import java.util.List;

public class GameUtil {
    public static String[] generateFrameNames(String prefix, int start, int finish, String suffix, boolean shuffle, int repeatFrame) {
        List<String> frameNames = new ArrayList<>();
        if (shuffle) {
            int length = finish - start + 1;
            int frame = (int) (Math.random() * length - 1);
            for (int i = 1; i <= length; i++) {
                for (int j = 0; j <= repeatFrame; j++) {
                    frameNames.add(prefix + (frame + start) + suffix);
                }
                frame = frame % length;
            }
        } else {
            for (int i = start; i <= finish; i++) {
                for (int j = 1; j <= repeatFrame; j++) {
                    frameNames.add(prefix + i + suffix);
                }
            }
        }
        String[] strings = new String[frameNames.size()];
        return frameNames.toArray(strings);
    }

}
