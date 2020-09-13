package prince.utils;

import prince.Constants;

import java.io.File;
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
            for (int f = 1; f <= finish; f++) {
                for (int r = 1; r <= repeatFrame; r++) {
                    frameNames.add(prefix + f + suffix);
                }
            }
        }
        String[] names = new String[frameNames.size()];
        return frameNames.toArray(names);
    }


    public static String getSoundName(String soundName) {
        File file = new File(Constants.SOUND_PATH_TEMPLATE + soundName + ".mp3");
        return file.getAbsolutePath();
    }


    public static int convertX(float x) {
        return (int) (x * 320 / 140);
    }

    public static int convertXtoBlockX(float x) {
        return (int) ((x - 7) / 14);
    }

    public static int convertYtoBlockY(float y) {
        return (int) (y / Constants.BLOCK_HEIGHT);
    }

    public int convertBlockXtoX(int blockX) {
        return blockX * 14 + 7;
    }

    public int convertBlockYtoY(int blockY) {
        return (blockY + 1) * Constants.BLOCK_HEIGHT - 10;
    }
}
