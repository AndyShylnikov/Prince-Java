package prince.states;

import prince.GameStateManager;

import java.awt.*;


public abstract class GameState {
    protected boolean isDone;

    protected GameState(GameStateManager manager) {
        this.manager = manager;
    }

    protected GameStateManager manager;

    public abstract void init();

    public abstract void draw(Graphics2D g);

    public abstract void update();

    public abstract void keyPressed(int k);

    public abstract void keyReleased(int k);

    public boolean isDone() {
        return isDone;
    }
}
