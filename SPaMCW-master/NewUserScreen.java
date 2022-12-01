import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class NewUserScreen extends BasicSmallScreen implements ActionListener {

    private JCheckBox gain, lose, male, female;
    private JTextField initialWeightText, targetWeightText, ageText, heightText;
    private String username;


    public NewUserScreen(String name) {

        super();
        username = name;
        draw("Gain or Lose Weight");
        drawFrame();
        mainFrame.setVisible(true);

    }

    public NewUserScreen() {
        super();
        draw("Gain or Lose Weight");
        drawFrame();
        mainFrame.setVisible(true);
    }

    public void drawFrame() {

        ButtonGroup checkBoxes = new ButtonGroup(); // Created so that only one of the checkboxes will ever be selected at a time
        ButtonGroup checkBoxes2 = new ButtonGroup(); // Created so that only one of the checkboxes will ever be selected at a time

        // Create the label instructing the user to select their account
        JLabel selectAccountLabel = new JLabel("Are you hoping to: ");
        selectAccountLabel.setBounds(8, 10, 160, 20);
        mainFrame.add(selectAccountLabel);

        // Create the gain button which the user presses if they intend to gain weight during their journey
        gain = new JCheckBox("Gain Weight");
        gain.setBounds(20, 30, 100, 30);
        gain.setBackground(Color.white);

        // Create the lose button which the user presses if they intend to lose weight during their journey
        lose = new JCheckBox("Lose Weight");
        lose.setBounds(20, 60, 100, 50);
        lose.setBackground(Color.white);



        // Create the label asking the user if they are male or female
        JLabel selectGenderLabel = new JLabel("Select gender: ");
        selectGenderLabel.setBounds(8, 120, 160, 20);
        mainFrame.add(selectGenderLabel);

        male = new JCheckBox("Male");
        male.setBounds(20, 140, 100, 30);
        male.setBackground(Color.white);

        female = new JCheckBox("Female");
        female.setBounds(20, 170, 100, 50);
        female.setBackground(Color.white);

        //lose.addItemListener(this);
        checkBoxes.add(gain);
        checkBoxes.add(lose);
        checkBoxes2.add(male);
        checkBoxes2.add(female);

        mainFrame.add(gain);
        mainFrame.add(lose);
        mainFrame.add(male);
        mainFrame.add(female);


        //Create the label asking the user to enter their age
        JLabel ageLabel = new JLabel("Enter your age: ");
        ageLabel.setBounds(240, 10, 100, 20);
        mainFrame.add(ageLabel);
        //Create the text field where the user will enter their age
        ageText = new JTextField("", 10);
        ageText.setBounds(240, 30, 100, 20);
        mainFrame.add(ageText);



        //Create the label asking the user to enter their age
        JLabel heightLabel = new JLabel("Enter your height (cm): ");
        heightLabel.setBounds(240, 50, 200, 20);
        mainFrame.add(heightLabel);
        //Create the text field where the user will enter their age
        heightText = new JTextField("", 10);
        heightText.setBounds(240, 80, 100, 20);
        mainFrame.add(heightText);



        // Create the label instructing the user to enter their current weight
        JLabel initialWeightLabel = new JLabel("Enter your current weight (kg): ");
        initialWeightLabel.setBounds(210, 140, 190, 20);
        mainFrame.add(initialWeightLabel);

        // Create the text field where the user can enter their initial weight
        initialWeightText = new JTextField("", 30);
        initialWeightText.setBounds(240, 160, 100, 20);
        mainFrame.add(initialWeightText);




        // Create the label instructing the user to select their account
        JLabel targetWeightLabel = new JLabel("Enter your target weight (kg): ");
        targetWeightLabel.setBounds(210, 190, 170, 20);
        mainFrame.add(targetWeightLabel);

        // Create the text field where the user can enter their initial weight
        targetWeightText = new JTextField("", 30);
        targetWeightText.setBounds(240, 210, 100, 20);
        mainFrame.add(targetWeightText);









        // Create the button where the user can confirm their choices
        JButton confirmDetails = new JButton("Confirm Details");
        confirmDetails.setBounds(100, 240, 200, 50);
        confirmDetails.addActionListener(this);
        mainFrame.add(confirmDetails);


    }

    public static String getUserName() throws IOException {
        BufferedReader fileReading = new BufferedReader(new FileReader("./userinfo/users.txt"));

        String currentLine;
        String lastLine = " ";
        while ((currentLine = fileReading.readLine()) != null) {
            lastLine = currentLine;
        }
        return lastLine;
    }


    public void actionPerformed(ActionEvent e) {
        try {
            String female = "true";
            if (male.isSelected()) {
                female = "false";
            }
            if (gain.isSelected()) {
                new AccountCreator(username, targetWeightText.getText(), initialWeightText.getText(), "false",
                        heightText.getText(), ageText.getText(), female);
            } else {
                // Store this in the database, indicating that the user wants to lose weight
                new AccountCreator(username, targetWeightText.getText(), initialWeightText.getText(), "true",
                        heightText.getText(), ageText.getText(), female);
            }

            mainFrame.dispose(); // Close the window


            MainMenuScreen.start(getUserName());

            new NoticeWindow("Remember your username!", "Your username is: " + getUserName());
            //InputTargetScreen getWeights = new InputTargetScreen();

        } catch (IOException exception) {
            System.out.println("Could not write to user file");
        }
    }

    public static void main(String args[]) {
        NewUserScreen newUserScreen = new NewUserScreen();
    }
}
