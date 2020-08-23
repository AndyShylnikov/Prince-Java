package prince;


import prince.states.GameState;

import java.awt.*;


public class GameStateManager {

    private GameState currentStateInstance;

    private static GameStateManager instance;

    private GameStateManager() {

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
    }

    public void draw(Graphics2D g) {

    }

    public void keyPressed(int e) {

    }

    public void keyReleased(int e) {
    }


}
