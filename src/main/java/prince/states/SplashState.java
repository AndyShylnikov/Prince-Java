package prince.states;

import prince.Constants;
import prince.GameScreen;
import prince.GameStateManager;
import prince.uielems.ImageElement;
import prince.utils.PlayerThread;

import java.awt.*;
import java.awt.event.KeyEvent;

public class SplashState extends GameState {
    private ImageElement backGround;
    private ImageElement authorLogo;
    private ImageElement gameLogo;
    private ImageElement companyLogo;
    private ImageElement portedLogo;
    private PlayerThread playerThread;
    private int elapsedTime;
    private long startTime;

    {
        isDone = false;
    }

    public SplashState(GameStateManager manager) {
        super(manager);

        backGround = new ImageElement(String.format(Constants.SPLASH_SCREEN_BG_PATH, GameScreen.getEnvironment()));
        backGround.setPosition(0, 0);
        backGround.setDimensions(GameScreen.WIDTH, GameScreen.HEIGHT);

        authorLogo = new ImageElement(String.format(Constants.SPLASH_SCREEN_AUTHOR_PATH, GameScreen.getEnvironment()));
        gameLogo = new ImageElement(String.format(Constants.SPLASH_SCREEN_GAME_PATH, GameScreen.getEnvironment()));
        companyLogo = new ImageElement(String.format(Constants.SPLASH_SCREEN_COMPANY_PATH, GameScreen.getEnvironment()));
        portedLogo = new ImageElement(String.format(Constants.SPLASH_SCREEN_PORTED_PATH, GameScreen.getEnvironment()));

        authorLogo.setScale(1.3f).
                setPosition(GameScreen.WIDTH / 2 - authorLogo.getWidth() / 2, (int) (GameScreen.HEIGHT * 0.65));
        companyLogo.setPosition(GameScreen.WIDTH / 4, (int) (GameScreen.HEIGHT * 0.65)).
                setScale((float) GameScreen.WIDTH / (2 * companyLogo.getOriginalDimensions()[0]));
        gameLogo.setScale((float) GameScreen.WIDTH * 0.95f / gameLogo.getOriginalDimensions()[0]).
                setPosition((int) (GameScreen.WIDTH * .025), (int) (GameScreen.HEIGHT * 0.3));
        portedLogo.setScale(.3f).
                setPosition(GameScreen.WIDTH / 2 - portedLogo.getWidth() / 2, (int) (GameScreen.HEIGHT * .6));
        playerThread = new PlayerThread(Constants.MAIN_THEME_MP3_PATH);


    }

    @Override
    public void init() {
        startTime = System.currentTimeMillis();
    }

    @Override
    public void draw(Graphics2D g) {
        backGround.draw(g);
        if (elapsedTime >= 2 && elapsedTime <= 6) {
            companyLogo.draw(g);
        } else if (elapsedTime >= 7 && elapsedTime <= 10) {
            authorLogo.draw(g);
        } else if (elapsedTime >= 12 && elapsedTime < 27) {
            gameLogo.draw(g);
            if (elapsedTime > 18) {
                portedLogo.draw(g);
            }
        } else if (elapsedTime > 27) {
            isDone = true;
            playerThread.stop();
        }
    }

    @Override
    public void update() {
        elapsedTime = ((int) (System.currentTimeMillis() - startTime)) / 1000;
    }

    @Override
    public void keyPressed(int k) {
        System.out.println(k);
        if (k == KeyEvent.VK_ESCAPE) {
            isDone = true;
            playerThread.stop();

        }
    }

    @Override
    public void keyReleased(int k) {

    }
}
