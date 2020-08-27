package prince.states;

import prince.GameScreen;
import prince.GameStateManager;
import prince.loaders.AnimationLoader;
import prince.loaders.LevelLoader;
import prince.loaders.SpriteLoader;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import static prince.Constants.FONT_PATH;

public class LoadingState extends GameState {

    private Font font;

    public LoadingState(GameStateManager manager) {
        super(manager);
    }

    @Override
    public void init() {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File(FONT_PATH)).deriveFont(20f * GameScreen.getInstance().getScale());
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        new SpriteLoader().loadItems();
        new AnimationLoader().loadItems();

    }


    @Override
    public void draw(Graphics2D g) {
        if (font != null) {
            g.setFont(font);
            String loadingStr = "Loading...";
            int textWidth = g.getFontMetrics().stringWidth(loadingStr);
            int x = (GameScreen.WIDTH * GameScreen.getInstance().getScale() - textWidth) / 2;
            int y = GameScreen.HEIGHT * GameScreen.getInstance().getScale() / 2;

            g.drawString(loadingStr, x, y);
        }

    }

    @Override
    public void update() {
    }

    @Override
    public void keyPressed(int k) {

    }

    @Override
    public void keyReleased(int k) {

    }
}
