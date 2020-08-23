package prince;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class GameScreen extends JPanel implements Runnable, KeyListener {
    private static GameScreen instance;
    private GameStateManager manager;


    // Origin resolution of game TODO: make dynamic change of resoution
    public static final int WIDTH = 320;
    public static final int HEIGHT = 200;

    private Thread thread;
    private boolean isRunning;
    private int FPS = 8;
    private long targetTime = 1000 / FPS;

    private Graphics2D g;
    private BufferedImage img;

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
        init();
        long start;
        long elapsed;
        long wait;
        while (isRunning) {
            start = System.nanoTime();
            update();
            draw();
            render();
            elapsed = System.nanoTime() - start;
            wait = Math.abs(targetTime - elapsed / 1000000);
            try {
                Thread.sleep(wait);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static GameScreen getInstance() {
        if (instance == null) {
            instance = new GameScreen();
        }
        return instance;
    }
    private void init() {
        img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) img.getGraphics();
        isRunning = true;
        manager = GameStateManager.getInstance();
    }
    private void render() {
        Graphics g2 = getGraphics();
        g2.drawImage(img, 0, 0, null);
        g2.dispose();
    }
    private void draw() {
        manager.draw(g);
    }

    private void update() {
        manager.update();

    }
    /**
     * Notifies this component that it now has a parent component.
     * When this method is invoked, the chain of parent components is
     * set up with <code>KeyboardAction</code> event listeners.
     * This method is called by the toolkit internally and should
     * not be called directly by programs.
     *
     * @see #registerKeyboardAction
     */
    @Override
    public void addNotify() {
        super.addNotify();
        if (thread == null) {
            thread = new Thread(this);
            addKeyListener(this);
            thread.start();
        }
    }
}
