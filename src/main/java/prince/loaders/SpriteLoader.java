package prince.loaders;

import prince.Constants;
import prince.uielems.Sprite;
import prince.utils.GameStorage;
import prince.utils.JsonHelper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class SpriteLoader extends BaseLoader {
    File spriteFolder;

    @Override
    public void loadItems() {

        spriteFolder = new File(Constants.SPRITES_FOLDER_PATH);
        goThroughFolder(spriteFolder);
    }

    private void goThroughFolder(File asset) {
        if (spriteFolder != null) {
            for (File file : Objects.requireNonNull(asset.listFiles())) {

                if (file.isDirectory()) {
                    goThroughFolder(file);
                } else if (file.getName().contains(".json") && file.getName().contains("-" + Constants.SPRITE_TYPE)) {
                    parseJson(file);
                }
            }
        }
    }

    private void parseJson(File jsonFile) {
        if (jsonFile.getName().startsWith("guard-")) {
            for (File guardFile : jsonFile.getParentFile().listFiles()) {
                String guardFileName = guardFile.getName();
                if (guardFileName.contains(".png") && guardFileName.startsWith("guard") && guardFileName.contains("-" + Constants.SPRITE_TYPE)) {
                    loadSprite(jsonFile, guardFile);
                }
            }

        } else {
            String pngPath = jsonFile.getAbsolutePath().replace(".json", ".png");
            loadSprite(jsonFile, new File(pngPath));
        }
    }

    private void loadSprite(File jsonFile, File pngFile) {
        Map<String, Object> frames = (Map<String, Object>) JsonHelper.getMap(jsonFile.getAbsolutePath()).get("frames");
        try {
            final BufferedImage image = ImageIO.read(pngFile);
            frames.forEach((key, value) -> {
                Map<String, Integer> frameMap = (Map<String, Integer>) ((Map<String, Object>) value).get("frame");

                int x = frameMap.get("x");
                int y = frameMap.get("y");
                int width = frameMap.get("w");
                int height = frameMap.get("h");


                if (key.startsWith("guard-")) {
                    String substring = pngFile.getName().substring(0, pngFile.getName().indexOf("-" + Constants.SPRITE_TYPE));
                    key = key.replace("guard-", substring + "-");
                }
                Sprite sprite = new Sprite(width, height, image.getSubimage(x, y, width, height), key);
                storage.addSprite(sprite);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}


