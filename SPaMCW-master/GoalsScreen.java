import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GoalsScreen extends BasicSmallScreen implements ActionListener {


    private JLabel instruction, weightGoal, distanceFromGoal, pointsLabel;
    private GoalLogic goalLogic;
    private JButton backButton;
    private String username;
    private String date;
    private DataRecorder dataRecorder;


    public GoalsScreen(String Date, String Username) {

        super();
        username = Username;
        date = Date;
        goalLogic = new GoalLogic(username);
        dataRecorder = new DataRecorder(username);
        draw("Your Goals");
        mainFrame.setLayout(new BorderLayout());
        displayComponents();

    }

    public void displayComponents() {

        Panel panel = new Panel(400, 350, Color.gray);
        panel.setLayout(new GridBagLayout());

        int goalWeight = 0;
        String currentWeight = "";
        int currentWeightInt = 0;
        String distanceFromWeight = "";
        //int points = 0;

        new MainMenuScreen(username);


        try {
            goalWeight = Integer.valueOf(goalLogic.getGoalWeight());
            currentWeight = (MainMenuScreen.getCurrentData("weight", date));
            if (MainMenuScreen.isNumeric(String.valueOf(currentWeight))) {
                currentWeightInt = Integer.valueOf(MainMenuScreen.getCurrentData("weight", date));
                distanceFromWeight = String.valueOf(Math.abs(goalWeight - currentWeightInt));
            }
            //points = Integer.valueOf(dataRecorder.getPoints());


        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IO exception...");
        }

        instruction = new JLabel("Your current goals from:" + date);
        instruction.setFont(new Font("Arial", Font.BOLD, 20));

        weightGoal = new JLabel("Your goal weight: " + String.valueOf(goalWeight) + "kg", SwingConstants.CENTER);
        weightGoal.setFont(new Font("Arial", Font.BOLD, 16));

        distanceFromGoal = new JLabel("kg from goal: " + String.valueOf(distanceFromWeight) + "kg", SwingConstants.CENTER);
        distanceFromGoal.setFont(new Font("Arial", Font.BOLD, 16));

        //pointsLabel = new JLabel("Your points: " + points, SwingConstants.CENTER);
        //pointsLabel.setFont(new Font("Arial", Font.BOLD, 16));

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
        panel.add(weightGoal, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(10, 0, 10, 0);
        panel.add(distanceFromGoal, gbc);


        gbc.gridx = 0;
        gbc.gridy = 3;
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
        new GoalsScreen("16-04-2021", "test#0");
    }

}
