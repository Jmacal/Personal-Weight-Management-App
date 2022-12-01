import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class AdditionalStatsScreen extends BasicSmallScreen implements ActionListener {


    private JLabel instruction, proteinLabel, carbsLabel, fatLabel;
    private GoalLogic goalLogic;
    private JButton backButton;
    private String username;
    private String date;
    private DataRecorder dataRecorder;


    public AdditionalStatsScreen(String Date, String Username) {

        super();
        username = Username;
        date = Date;
        goalLogic = new GoalLogic(username);
        dataRecorder = new DataRecorder(username);
        draw("Additional Statistics");
        mainFrame.setLayout(new BorderLayout());
        displayComponents();
    }

    public void displayComponents() {

        Panel panel = new Panel(400, 350, Color.gray);
        panel.setLayout(new GridBagLayout());

        String protein = "-";
        String carbs = "-";
        String fat = "-";

        new MainMenuScreen(username);

        try {
            protein = MainMenuScreen.getCurrentData("protein", date);
            carbs = MainMenuScreen.getCurrentData("carbs", date);
            fat = MainMenuScreen.getCurrentData("fat", date);
        } catch (IOException e) {
            System.out.println("IO exception when taking the additional stats out...");
        }




        instruction = new JLabel("Additional statistics for: " + date);
        instruction.setFont(new Font("Arial", Font.BOLD, 20));

        proteinLabel = new JLabel("Protein: " + (protein) + "g", SwingConstants.CENTER);
        proteinLabel.setFont(new Font("Arial", Font.BOLD, 16));

        carbsLabel = new JLabel("Carbohydrates: " + (carbs) + "g", SwingConstants.CENTER);
        carbsLabel.setFont(new Font("Arial", Font.BOLD, 16));

        fatLabel = new JLabel("Fats: " + (fat) + "g", SwingConstants.CENTER);
        fatLabel.setFont(new Font("Arial", Font.BOLD, 16));

        backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.addActionListener(this);


        //points = new JLabel()
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(instruction, gbc);


        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(60, 0, 10, 0);
        gbc.anchor = GridBagConstraints.LINE_START;
        panel.add(proteinLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(10, 0, 10, 0);
        panel.add(carbsLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(fatLabel, gbc);



        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(20, 0, 10, 0);
        panel.add(backButton, gbc);

        mainFrame.add(panel, BorderLayout.CENTER);

        mainFrame.setVisible(true);



    }

    public void actionPerformed(ActionEvent e) {
        mainFrame.dispose();
        MainMenuScreen.start(username);
    }

    public static void main(String args[]) {
        new AdditionalStatsScreen("15-04-2021", "test#0");
    }

}
