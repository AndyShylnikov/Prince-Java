package prince.states;

import prince.uielems.AnimatedItem;
import prince.Constants;
import prince.GameScreen;
import prince.GameStateManager;
import prince.enums.SceneActionEnum;
import prince.enums.SceneStateEnum;
import prince.pojo.SceneSequenceItem;
import prince.uielems.Actor;
import prince.uielems.ImageElement;
import prince.uielems.Scene;
import prince.uielems.Sprite;
import prince.utils.GameStorage;
import prince.utils.GameUtil;
import prince.utils.PlayerThread;
import prince.uielems.SandClock;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.awt.event.KeyEvent.VK_ESCAPE;
import static prince.utils.GameUtil.generateFrameNames;

public class SceneState extends GameState {
    Sprite princess;
    private GameStorage storage = GameStorage.getInstance();
    private Rectangle fadingRect = new Rectangle();
    private int sceneNumber;
    private ImageElement background;
    private List<AnimatedItem> animatedItems;
    private int[][] torchesLocation;
    private int[][] starsLocations;
    private List<Actor> actors;
    private float alpha = 0f;
    private Sprite pillar;
    private Scene scene;
    private int waitFrames = 0;
    private int currentStep;
    private List<PlayerThread> sounds;
    private SceneStateEnum state;
    private boolean isFlashing;
    private int flashFrame;

    public SceneState(GameStateManager gameStateManager, int i) {
        super(gameStateManager);
        fadingRect.setBounds(0, 0, ((int) (GameScreen.WIDTH * GameScreen.getHScale())), ((int) (GameScreen.HEIGHT * GameScreen.getVScale())));
        background = new ImageElement(storage.getSprite("princess-room"));
        background.setPosition(0, 0).setDimensions((int) (background.getWidth() * GameScreen.getHScale()), ((int) (background.getHeight() * GameScreen.getVScale())));
        pillar = storage.getSprite("room-pillar");

        sceneNumber = i;
        actors = new ArrayList();
        sounds = new ArrayList<>();
    }

    @Override
    public void init() {
        scene = storage.getScene(sceneNumber - 1);

        if (GameScreen.getEnvironment().equals("dos")) {
            torchesLocation = new int[][]{{92, 97}, {208, 97}};
            starsLocations = new int[][]{{20, 97}, {16, 104}, {23, 110}, {17, 116}, {24, 120}, {18, 128}};
        } else {
            torchesLocation = new int[][]{{92, 97}, {208, 97}};
            starsLocations = new int[][]{{20, 97}, {16, 104}, {23, 110}, {17, 116}, {24, 120}, {18, 128}};
        }
        animatedItems = Stream.of(torchesLocation).
                map(location -> new AnimatedItem(location[0], location[1], 16, 18, Stream.
                        of(generateFrameNames("fire_", 1, 9, "", true, 1)).
                        map(storage::getSprite).
                        collect(Collectors.toList()))).
                collect(Collectors.toList());
        Stream.of(starsLocations).
                map(starsLocation -> new AnimatedItem(starsLocation[0], starsLocation[1], 1, 1, Stream.of(generateFrameNames("star", 0, 2, "", true, 2)).
                        map(storage::getSprite).
                        collect(Collectors.toList()))).
                forEach(animatedItems::add);
        state = SceneStateEnum.RUNNING;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        background.draw(g);
        animatedItems.forEach(item -> item.draw(g));

        actors.forEach(actor -> {
            int x;
            if (actor.faceR())
                x = (int) ((actor.getX() - actor.getWidth()) * GameScreen.getHScale());
            else
                x = (int) (actor.getX() * GameScreen.getHScale());
            int y = (int) ((actor.getY() - actor.getHeight()) * GameScreen.getVScale());

            actor.draw(g, x, y);
        });
        g.drawImage(pillar.getImage(),
                (int) (237 * GameScreen.getHScale()),
                ((int) (118 * GameScreen.getVScale())),
                ((int) (pillar.getWidth() * GameScreen.getHScale())),
                (int) (pillar.getHeight() * GameScreen.getVScale()),
                null);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                alpha));
        if (isFlashing) {
            if (flashFrame == 10) {
                isFlashing = false;
                flashFrame = 0;
                return;
            }
            if (flashFrame % 2 == 0) {
                g.setColor(Color.decode("#000000"));
                g.fillRect(0, 0, (int) (GameScreen.WIDTH * GameScreen.getHScale()), ((int) (GameScreen.HEIGHT * GameScreen.getVScale())));

            } else if(flashFrame%3==0) {
                g.setColor(Color.decode("#FFFFFF"));
                g.fillRect(0, 0, (int) (GameScreen.WIDTH * GameScreen.getHScale()), ((int) (GameScreen.HEIGHT * GameScreen.getVScale())));

            };
            flashFrame++;
        }

    }

    @Override
    public void update() {

        animatedItems.forEach(AnimatedItem::update);
        actors.forEach(Actor::update);

        if (state == SceneStateEnum.FADEOUT) {
            if (alpha < 1) {
                alpha += 0.1f;
                if (alpha > 1)
                    alpha = 1f;

            } else {
                state = SceneStateEnum.RUNNING;
            }
        } else if (state == SceneStateEnum.FADEIN) {
            if (alpha > 0) {
                alpha -= 0.1f;
                if (alpha < 0)
                    alpha = 0;
            } else {
                isDone = true;
            }
        }
        if (state == SceneStateEnum.WAITING) {
            waitFrames--;
            if (waitFrames == 0) {
                state = SceneStateEnum.RUNNING;
            }
        }
        while (state == SceneStateEnum.RUNNING) {
            SceneSequenceItem sceneSequenceItem = scene.getSequenceList().get(currentStep);
            SceneActionEnum actionType = sceneSequenceItem.getActionType();
            switch (actionType) {
                case START:
                    state = SceneStateEnum.FADEOUT;
                    break;
                case END:
                    state = SceneStateEnum.FADEIN;
                    break;
                case ADD_ACTOR:
                    actors.add(new Actor(sceneSequenceItem.getActorName(),
                            sceneSequenceItem.getDirection(),
                            sceneSequenceItem.getX(),
                            sceneSequenceItem.getY()
                    ));
                    break;
                case ACTION:
                    Actor actor = actors.get(sceneSequenceItem.getActorId());
                    actor.action(sceneSequenceItem.getActionName());
                    break;
                case WAIT:
                    waitFrames = sceneSequenceItem.getFrames();
                    state = SceneStateEnum.WAITING;
                    break;
                case PLAY_SONG:
                    sounds.add(new PlayerThread(String.format(Constants.SONGS_PATH_TEMPLATE, sceneSequenceItem.getSongName())));
                    break;
                case PLAY_SOUND:
                    sounds.add(new PlayerThread(GameUtil.getSoundName(sceneSequenceItem.getSoundName())));
                    break;
                case REM_ACTOR:
                    actors.remove(actors.get(sceneSequenceItem.getActorId()));
                    break;
                case ADD_OBJECT:
                    animatedItems.add(new SandClock(sceneSequenceItem.getX(), sceneSequenceItem.getY()));
                    break;
                case START_OBJECT:
                    ((SandClock) animatedItems.stream().filter(item -> item.getClass().equals(SandClock.class)).findFirst().get()).setActive(true);
                case EFFECT:
                    isFlashing = true;

            }
            currentStep++;

        }
    }

    @Override
    public void keyPressed(int k) {
        if (k==VK_ESCAPE){
            isDone=true;
        }

    }

    @Override
    public void keyReleased(int k) {

    }
}
