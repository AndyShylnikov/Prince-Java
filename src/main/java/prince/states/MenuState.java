package prince.states;

import prince.Constants;
import prince.GameScreen;
import prince.GameStateManager;
import prince.enums.MenuScreenEnum;
import prince.uielems.ImageElement;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

public class MenuState extends GameState {
    private ImageElement background;
    private ImageElement highlighted;
    private ImageElement credits;
    private MenuScreenEnum currentMenuScreen;
    private int currentChoice = 0;
    private Font font;
    private Color color;


    public MenuState(GameStateManager manager) {
        super(manager);

        currentMenuScreen = MenuScreenEnum.MAIN_MENU;
        currentChoice = 0;

        background = new ImageElement(Constants.MENU_BACKGROUND_PATH);
        background.setPosition(0, 0);
        background.setDimensions(GameScreen.WIDTH, GameScreen.HEIGHT);

        credits = new ImageElement(Constants.CREDITS_PATH);
        credits.setPosition(0, 0);
        credits.setDimensions(GameScreen.WIDTH, GameScreen.HEIGHT);

        highlighted = new ImageElement(Constants.MENU_HIGHLIGHTED_ITEM);
        highlighted.setPosition(92, 70);
        highlighted.setDimensions(154, 25);

        color = new Color(Constants.COLOR_RED);

        try {
            Font customFont = Font.createFont(0, new File(Constants.FONT_PATH));
            font = customFont.deriveFont(30f);
        } catch (FontFormatException e) {
            e.printStackTrace();
            font = new Font("Arial", Font.PLAIN, 30);
        } catch (IOException e) {
            font = new Font("Arial", Font.PLAIN, 30);
            e.printStackTrace();
        }
    }

    @Override
    public void init() {

    }

    @Override
    public void draw(Graphics2D g) {
        background.draw(g);

        g.setColor(color);
        g.setFont(font);
        if (currentMenuScreen == MenuScreenEnum.MAIN_MENU) {
            drawMenu(g);
        } else if (currentMenuScreen == MenuScreenEnum.CREDITS) {
            drawCredits(g);
        }


    }


    @Override
    public void update() {

    }

    @Override
    public void keyPressed(int k) {
        int optionsLength = Constants.MENU_OPTIONS.length;
        if (currentMenuScreen == MenuScreenEnum.MAIN_MENU) {
            if (k == KeyEvent.VK_ENTER) {
                select();
            } else if (k == KeyEvent.VK_UP) {
                currentChoice--;
                if (currentChoice < 0)
                    currentChoice = 0;
            } else if (k == KeyEvent.VK_DOWN) {
                currentChoice++;
                if (currentChoice >= optionsLength)
                    currentChoice = optionsLength - 1;
            }
        }
        if (k == KeyEvent.VK_ESCAPE) {
            if (currentMenuScreen == MenuScreenEnum.MAIN_MENU) {
                System.exit(0);
            } else if (currentMenuScreen == MenuScreenEnum.CREDITS) {
                showMenu();
                currentChoice = 2;
            }
        }
    }

    @Override
    public void keyReleased(int k) {

    }

    private void select() {
        switch (currentChoice) {
            case (0):
                isDone = true;
//                manager.setCurrentLevel(1);
//                manager.setGameState(GameStateEnum.LEVEL_STATE);
                break;
            case (1):
// TODO: MAke saved game loading
                break;
            case (2):
                showCredits();
                break;
            case (3):
                System.exit(0);

        }

    }

    private void showCredits() {
        currentMenuScreen = MenuScreenEnum.CREDITS;
    }


    private void showMenu() {
        currentMenuScreen = MenuScreenEnum.MAIN_MENU;

    }

    private void drawMenu(Graphics2D g) {
        highlighted.setPosition(92, 70 + currentChoice * 24);
        highlighted.draw(g);

        for (int i = 0; i < Constants.MENU_OPTIONS.length; i++) {
            if (i == currentChoice) {
                g.setColor(new Color(Constants.COLOR_NAVY));
            } else
                g.setColor(new Color(Constants.COLOR_DARK_RED));


            int x = (int) (110 * GameScreen.getHScale());
            int y = (int) ((85 + i * 25) * GameScreen.getVScale());
            g.drawString(Constants.MENU_OPTIONS[i], x, y);
        }
    }

    private void drawCredits(Graphics2D g) {
        credits.draw(g);
    }
}
