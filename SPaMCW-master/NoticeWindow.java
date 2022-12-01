import javax.swing.*;
import java.awt.*;

public class NoticeWindow {


    private JFrame mainFrame;


    public NoticeWindow(String title, String message) {

        mainFrame = new JFrame(title);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setSize(400, 250);
        mainFrame.setLocationRelativeTo(null); // Centre the window
        mainFrame.setResizable(false);


        JLabel messageLabel = new JLabel(message, SwingConstants.CENTER);
        messageLabel.setBackground(Color.darkGray);
        messageLabel.setOpaque(true);
        messageLabel.setForeground(Color.white);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 16));

        mainFrame.add(messageLabel, BorderLayout.CENTER);

        /*mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                MainMenuScreen.noticeWindowOpen = false;
            }
        });
         */
        mainFrame.setVisible(true);
    }
    


}
