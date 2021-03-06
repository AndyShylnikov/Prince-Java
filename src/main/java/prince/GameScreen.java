package prince;

import prince.utils.JsonHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.Map;

public class GameScreen extends JPanel implements Runnable, KeyListener {
    public static final int WIDTH = 320;
    public static final int HEIGHT = 200;
    private static GameScreen instance = null;
    private static float vScale = 1;
    private static float hScale = 1;
    private static int scale = 1;
    private static String environment = "dos";
    private final Dimension screenSize;
    private GameStateManager manager;
    private JFrame frame;
    private Thread thread;
    private boolean isRunning;
    private int FPS = 8;
    private long targetTime = 1000 / FPS;
    private Graphics2D g;
    private BufferedImage img;

    private GameScreen() {

        super();
        Map<String, Object> config = JsonHelper.getMap(Constants.CONFIG_PATH);
        screenSize = getToolkit().getScreenSize();
        vScale = ((float) screenSize.height) / HEIGHT;
        hScale = ((float) screenSize.width) / WIDTH;
        environment = ((String) config.get("environment"));
        String title = ((String) config.get("title"));
        setPreferredSize(new Dimension(((int) (WIDTH * hScale)), ((int) (HEIGHT * vScale))));
        setFocusable(true);
        requestFocus();
        setBackground(Color.BLACK);
        frame = new JFrame(title);
        frame.setUndecorated(true);
        frame.setBackground(Color.BLACK);
        frame.setContentPane(this);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);//making the frame visible
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    public synchronized static GameScreen getInstance() {
        GameScreen result = instance;
        if (instance == null) {
            instance = new GameScreen();
        }
        return instance;
    }

    public static int getScale() {
        return scale;
    }

    public static String getEnvironment() {
        return environment;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        manager.keyPressed(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        manager.keyReleased(e.getKeyCode());
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

    private void init() {

        img = new BufferedImage(((int) (WIDTH * hScale)), (int) (HEIGHT * vScale), BufferedImage.TYPE_INT_RGB);
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
        g.clearRect(0,0,screenSize.width,screenSize.height);
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

    public static float getVScale() {
        return vScale;
    }

    public static float getHScale() {
        return hScale;
    }
}
