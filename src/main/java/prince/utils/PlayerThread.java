package prince.utils;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class PlayerThread implements Runnable {
    private Player player;
    private Thread playerThread;
//    static int fileRunning = 0;

    public PlayerThread(String uri) {
//        if (fileRunning == 0) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(uri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                player = new Player(fis);
            } catch (JavaLayerException e) {
                e.printStackTrace();
            }
            playerThread = new Thread(this);
            playerThread.start();
//            fileRunning = 1;
//        }
    }

    @Override
    public void run() {
        try {
            player.play();
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        player.close();
    }
}
