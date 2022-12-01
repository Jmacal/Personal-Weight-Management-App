import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;

public class AdviceScreen extends BasicSmallScreen implements ActionListener {

    private String date, username;

    private JLabel currentWeightLabel, goalLabel, timeLabel, titleLabel, adviceArea, adviceText;
    private JTextField currentWeightEntry, goalEntry, timeEntry;
    private JButton getAdviceButton, back;

    public AdviceScreen(String Date, String Username) {

        super();
        username = Username;
        date = Date;
        draw("Personal Advice");
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setResizable(false);
        displayComponents();


        date = Date;
        username = Username;
    }

    public void displayComponents() {

        Panel panel = new Panel(400, 350, Color.gray);
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        //Create the labels and display them
        currentWeightLabel = new JLabel("Enter starting weight (kg): ");
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 0, 10, 5);
        panel.add(currentWeightLabel, gbc);

        goalLabel = new JLabel("Enter target weight (kg): ");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(goalLabel, gbc);

        timeLabel = new JLabel("Enter days to reach this goal: ");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(timeLabel, gbc);


        //Create the text fields and display them
        currentWeightEntry = new JTextField("", 5);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(currentWeightEntry, gbc);

        goalEntry = new JTextField("", 5);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(goalEntry, gbc);

        timeEntry = new JTextField("", 5);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(timeEntry, gbc);




        //Create the get advice button
        getAdviceButton = new JButton("Get Advice");
        getAdviceButton.setBackground(Color.white);
        getAdviceButton.addActionListener(this);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 0, 0, 5);
        panel.add(getAdviceButton, gbc);

        //Create the back button
        back = new JButton("Back");
        back.setBackground(Color.white);
        back.addActionListener(this);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 0, 0, 5);
        panel.add(back, gbc);

        //Create the label that acts as the title
        titleLabel = new JLabel("Enter details to get specific advice!");
        titleLabel.setFont(new Font(null, Font.BOLD, 14));
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridwidth = 4;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 10, 5);
        panel.add(titleLabel, gbc);


        //Create the area where the calories epr day will be displayed
        adviceArea = new JLabel("Recommended calorie consumption:");
        adviceArea.setFont(new Font("Arial", Font.BOLD, 14));
        //adviceArea.setPreferredSize(new Dimension(100, 350);
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(10, 0, 0, 0);
        panel.add(adviceArea, gbc);

        adviceText = new JLabel();
        adviceText.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(adviceText, gbc);







        mainFrame.add(panel);
        mainFrame.setVisible(true);
    }

    public void getCaloriesNeeded() {
        GoalLogic goalLogic = new GoalLogic(username);
        int days = Integer.valueOf(timeEntry.getText());
        LocalDate targetDate = LocalDate.now().plusDays(days);
        float current = Integer.valueOf(currentWeightEntry.getText());
        Weight currentWeight = new Weight(current);
        float target = Integer.valueOf(goalEntry.getText());
        Weight targetWeight = new Weight(target);
        GoalWeight testGoal = new GoalWeight(targetDate, targetWeight);



        try {
            int height = Integer.valueOf(goalLogic.getAdditionalData("height"));
            int age = Integer.valueOf(goalLogic.getAdditionalData("age"));
            boolean female = Boolean.valueOf(goalLogic.getAdditionalData("male"));
            float caloriesPerDay = CalorieMath.getDailyCaloriesForPlan(testGoal,
                    currentWeight, height, age, !female); //!female as the parameter passed in is a boolean for male

            adviceText.setText(caloriesPerDay + " kcal per day");
            adviceText.setBackground(Color.lightGray);
            adviceText.setOpaque(true);
            adviceText.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        } catch (IOException e) {
            System.out.println("IO Exception when giving advace...");
        }

        /*try {
            System.out.println((int)CalorieMath.getDailyCaloriesForPlan(goalLogic.getGoalWeight(),
                    MainMenuScreen.getCurrentData("weight", date), goalLogic.getHeight(), 11, true));
        } catch (IOException e) {
            System.out.println("IO Exception when displaying advice");
        }

         */


    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == getAdviceButton) {
            if (MainMenuScreen.isNumeric(currentWeightEntry.getText()) && MainMenuScreen.isNumeric(goalEntry.getText())
                    && MainMenuScreen.isNumeric(timeEntry.getText())) {
                getCaloriesNeeded();
            } else {
                new NoticeWindow("Invalid Data", "Please ensure that you enter valid data!");
            }

        } else if (e.getSource() == back) {
            mainFrame.dispose();
            MainMenuScreen.start(username);
        }
    }

    public static void main(String[] args) {
        new AdviceScreen("21-04-2021", "test#0");
    }

}
