import javax.swing.*;
import java.awt.*;


public class Test {

    private JFrame mainFrame;


    public Test() {
        mainFrame = new JFrame();
        mainFrame.setLocationRelativeTo(null); // Centre the window
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // mainFrame.setLayout(null); // Allows the use of absolute values when positioning components



        mainFrame.setSize(900, 700);
        mainFrame.setResizable(false);






        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.blue);
        GridBagConstraints gbc = new GridBagConstraints();

        JButton Button1 = new JButton("Button 1");
        JButton Button2 = new JButton("Button 2");
        JButton Button3 = new JButton("Button 3");
        JButton Button4 = new JButton("Button 4");

        gbc.insets = new Insets(5, 5, 5, 5);


        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(Button1, gbc);

        //gbc.weightx = 10;
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(Button2, gbc);


        gbc.insets = new Insets(10, 5, 10, 5);

        gbc.gridx = 1;
        gbc.gridy = 0;

        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel.add(Button3, gbc);


        gbc.insets = new Insets(15, 0, 15, 0);

        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 2;

        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(Button4, gbc);

        mainFrame.add(panel);






        mainFrame.setVisible(true);
    }



    public static void main(String args[]) {
        Test main = new Test();
    }

}
