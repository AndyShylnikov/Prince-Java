package prince;


import prince.states.GameState;
import prince.states.LoadingState;

import java.awt.*;


public class GameStateManager {

    private GameState currentStateInstance;

    private static GameStateManager instance;

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
//            TODO: perform handling ending of state
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
