package prince;


import prince.enums.GameStateEnum;
import prince.states.*;

import java.awt.*;


public class GameStateManager {

    private static GameStateManager instance;
    private GameState currentStateInstance;

    private GameStateManager() {
        currentStateInstance = new LoadingState(this);
        currentStateInstance.init();
    }

    public static GameStateManager getInstance() {
        if (instance == null) {
            instance = new GameStateManager();
        }
        return instance;
    }

    public void setGameState(GameStateEnum gameState) {
    }

    public void update() {
        currentStateInstance.update();
        if (currentStateInstance.isDone()) {

            if (currentStateInstance.getClass().equals(LoadingState.class)) {
                currentStateInstance = new SplashState(this);
                currentStateInstance.init();
            } else if (currentStateInstance.getClass().equals(SplashState.class)) {
                currentStateInstance = new MenuState(this);
                currentStateInstance.init();
            }
            else if (currentStateInstance.getClass().equals(MenuState.class)){
               //TODO: make game starting
            }
        }
    }

    public void draw(Graphics2D g) {
        currentStateInstance.draw(g);

    }

    public void keyPressed(int e) {
        currentStateInstance.keyPressed(e);

    }

    public void keyReleased(int e) {
        currentStateInstance.keyReleased(e);
    }
}
