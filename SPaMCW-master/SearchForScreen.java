import javax.swing.*;
import java.awt.*;

public class SearchForScreen {

    protected JFrame mainFrame;
    protected DataRecorder dataRecorder;

    public SearchForScreen(String username, String title) {


        mainFrame = new JFrame();
        mainFrame.setTitle(title);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout()); // Allows the use of absolute values when positioning components
        mainFrame.setSize(700, 500);
        mainFrame.setLocationRelativeTo(null); // Centre the window
        mainFrame.setResizable(false);
        dataRecorder = new DataRecorder(username);


    }
}
