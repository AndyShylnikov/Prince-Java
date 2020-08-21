package prince;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class GameScreen extends JPanel implements Runnable, KeyListener {
    private static GameScreen instance;


    // Origin resolution of game TODO: make dynamic change of resoution
    public static final int WIDTH = 320;
    public static final int HEIGHT = 200;

    private GameScreen() {
        super();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocus();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {

    }

    public static GameScreen getInstance() {
        if (instance == null) {
            instance = new GameScreen();
        }
        return instance;
    }
}
