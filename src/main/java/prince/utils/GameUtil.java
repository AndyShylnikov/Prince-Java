package prince.utils;

import java.util.ArrayList;
import java.util.List;

public class GameUtil {
    public static String[] generateFrameNames(String prefix, int start, int finish, String suffix, boolean shuffle) {
        return generateFrameNames(prefix, start, finish, suffix, shuffle, 1);
    }

    public static String[] generateFrameNames(String prefix, int start, int finish, String suffix) {
        return generateFrameNames(prefix, start, finish, suffix, false);
    }

    public static String[] generateFrameNames(String prefix, int start, int finish) {
        return generateFrameNames(prefix, start, finish, "");
    }

    public static String[] generateFrameNames(String prefix, int start, int finish, String suffix, boolean shuffle, int repeatFrame) {
        List<String> frameNames = new ArrayList<>();
        if (shuffle) {
            int length = finish - start + 1;
            int max = length;
            int min = 0;
            int range = max - min + 1;
            int frame = (int) ((Math.random() * range + min) - 1);
            for (int f = 1; f <= length; f++) {
                for (int r = 1; r <= repeatFrame; r++) {
                    frameNames.add(prefix + (frame + start) + suffix);
                }
                frame = (frame + 1) % length;
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
