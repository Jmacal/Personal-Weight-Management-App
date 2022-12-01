import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoginScreen extends BasicSmallScreen implements ActionListener {

    private NoticeWindow currentNoticeWindow; //Setting each notice window to the same variable ensures only one window
                                              // will ever be open at once
    private JComboBox<String> dropDown;
    private JButton backButton;
    private JTextField accountEntry;

    public LoginScreen() {


        super();
        draw("Account Login");
        drawFrame();

        //Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        mainFrame.setVisible(true);

    }

    private void drawFrame() {

        String[] accounts = displayAllAccounts(); // Calls a method which accesses the database/text files to retrieve the account names

        // Create the label instructing the user to select their account
        JLabel selectAccountLabel = new JLabel("Please Select Account", SwingConstants.CENTER);
        selectAccountLabel.setBounds(100, 20, 200, 50);
        mainFrame.add(selectAccountLabel);

        // Create the drop down menu where the user can select their account
        accountEntry = new JTextField("", 30);
        accountEntry.setBounds(100, 90, 200, 20);
        mainFrame.add(accountEntry);

        // Create the label requesting the user to confirm their choice
        JLabel confirmAccount = new JLabel("Confirm Choice");
        confirmAccount.setBounds(100, 115, 140, 50);
        mainFrame.add(confirmAccount);

        // Create the confirm button which the user presses when they have selected the account they wish to use
        JButton confirm = new JButton("✓");
        confirm.setBounds(250, 125, 50, 30);
        confirm.addActionListener(this);
        mainFrame.add(confirm);

        backButton = new JButton("Back");
        backButton.setBounds(280, 250, 100, 50);
        backButton.addActionListener(this);
        mainFrame.add(backButton);
    }


    public String[] displayAllAccounts() {
        String[] accounts = {"Account 1", "Account 2", "Account 3", "Account 4"};
        return accounts;
    }



    public static boolean userExists(String username) throws IOException {
        BufferedReader fileReading = new BufferedReader(new FileReader("./userinfo/users.txt"));

        String currentLine;
        while ((currentLine = fileReading.readLine()) != null) {
            if (currentLine.equals(username)) {
                return true;
            }
        }
        return false;
    }



    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            // Take the user back to the account creation screen
            mainFrame.dispose(); // Close the window
            CreateAccountScreen createAccount = new CreateAccountScreen();
        } else {
            //String account = String.valueOf(dropDown.getSelectedItem());
            String userName = accountEntry.getText();
            System.out.println("Account selected is: " + userName);
            try {
                if (userExists(userName)) {
                    mainFrame.dispose(); // Close the window
                    MainMenuScreen.start(userName);
                    currentNoticeWindow = new NoticeWindow("Welcome Back", "Welcome back " + userName);

                } else {
                    currentNoticeWindow = new NoticeWindow("Error!", "Please enter an existing username");
                }

            } catch (IOException e1) {
                e1.printStackTrace();
            }



        }


    }


}
