import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class CreateAccountScreen extends BasicSmallScreen implements ActionListener {

    private JTextField nameEntry;
    private JButton confirmName;
    private JButton loginButton;

    public CreateAccountScreen() {

        super();
        try {
          new SpawnAccountFiles();
        } catch (IOException e) {
          System.out.println("Error making files");
        }
        draw("Create an Account");
        drawFrame();

        //Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        mainFrame.setVisible(true);
    }

    public void drawFrame() {

        // Create the label instructing the user to select their account
        JLabel selectAccountLabel = new JLabel("Please Select Account Name", SwingConstants.CENTER);
        selectAccountLabel.setBounds(100, 20, 200, 50);
        mainFrame.add(selectAccountLabel);

        nameEntry = new JTextField("", 30);
        nameEntry.setBounds(100, 80, 200, 25);
        mainFrame.add(nameEntry);

        confirmName = new JButton("Confirm Account Name");
        confirmName.setBounds(100, 130, 200, 50);
        confirmName.addActionListener(this);
        mainFrame.add(confirmName);

        loginButton = new JButton("Already have an account?");
        loginButton.setBounds(100, 200, 200, 50);
        loginButton.addActionListener(this);
        mainFrame.add(loginButton);


    }

    public boolean nameIsValid(String name) {
        // if name is valid and is not used yet then return true, else false
        //https://stackoverflow.com/questions/5238491/check-if-string-contains-only-letters/29836318
        //return name.matches("[a-zA-Z]+");
        return true;
    }

    public void addNameToDatabase() {
        return;
    }

    public void openLoginScreen() {
        // The user already has an account , or the user has just created one, so either way take them to the the login screen
        LoginScreen loginScreen = new LoginScreen();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == confirmName) {
            // Calls a method that checks if the name is valid etc... then adds it to the database
            if (nameIsValid(nameEntry.getText())) {
                //addNameToDatabase();  Add this name to the database/text file
                //openLoginScreen();
                mainFrame.dispose();
                NewUserScreen newUserScreen = new NewUserScreen(nameEntry.getText());
            } else {
                new NoticeWindow("Error!", "Please enter a valid username");
                // Inform the user that the name is invalid, maybe through a pop-up window as a separate thread
            }
        } else { // The user selected the "Already have an account?" button
            mainFrame.dispose();
            openLoginScreen();

        }



    }

    public static void main(String args[]) {
        CreateAccountScreen loadLoginScreen = new CreateAccountScreen();
    }

}
