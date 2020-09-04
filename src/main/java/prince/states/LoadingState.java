package prince.states;

import prince.GameScreen;
import prince.GameStateManager;
import prince.loaders.AnimationLoader;
import prince.loaders.LevelLoader;
import prince.loaders.SceneLoader;
import prince.loaders.SpriteLoader;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import static prince.Constants.FONT_PATH;

public class LoadingState extends GameState {
    private long startTime;
    private Font font;
    private boolean isLoaded = false;

    public LoadingState(GameStateManager manager) {
        super(manager);
    }

    @Override
    public void init() {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File(FONT_PATH)).deriveFont(20f * GameScreen.getScale());
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        startTime = System.currentTimeMillis();
        new SpriteLoader().loadItems();
        new AnimationLoader().loadItems();
        new LevelLoader().loadItems();
        new SceneLoader().loadItems();
        isLoaded = true;

    }


    @Override
    public void draw(Graphics2D g) {
        if (font != null) {
            g.setFont(font);
            String loadingStr = "Loading...";
            int textWidth = g.getFontMetrics().stringWidth(loadingStr);
            int x = (GameScreen.WIDTH * GameScreen.getScale() - textWidth) / 2;
            int y = GameScreen.HEIGHT * GameScreen.getScale() / 2;

            g.drawString(loadingStr, x, y);
        }

    }

    @Override
    public void update() {
        long elapsedTime = System.currentTimeMillis() - startTime;
        if ((isLoaded && elapsedTime > 4000) && (isLoaded || elapsedTime > 4000))
            isDone = true;
    }

    @Override
    public void keyPressed(int k) {

    }

    @Override
    public void keyReleased(int k) {

    }
}
