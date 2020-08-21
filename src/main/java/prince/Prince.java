package prince;

import javax.swing.*;
import java.awt.*;

public class Prince {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Prince of Persia"); //TODO: move title to json
        frame.setBackground(Color.BLACK);
        frame.setContentPane(GameScreen.getInstance());
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);//making the frame visible
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
