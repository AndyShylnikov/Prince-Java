package prince;


import prince.enums.GameStateEnum;
import prince.states.GameState;
import prince.states.LoadingState;
import prince.states.SplashState;

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
//                TODO: finish splash
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
