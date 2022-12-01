import javax.swing.*;
import java.awt.*;

public class BasicSmallScreen {

    protected JFrame mainFrame;

    public BasicSmallScreen() {

        mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setLayout(null); // Allows the use of absolute values when positioning components

    }

    public void draw(String title) {


        mainFrame.setTitle(title);
        mainFrame.setSize(400, 350);
        mainFrame.setLocationRelativeTo(null); // Centre the window
        mainFrame.getContentPane().setBackground(Color.WHITE);




    }
}
