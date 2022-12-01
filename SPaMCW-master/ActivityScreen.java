import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ActivityScreen extends SearchForScreen implements ActionListener {

    private String date;
    private String username;

    private JComboBox activityDropDown;
    private JButton confirmButton, doneButton;

    private JTextArea activtityArea;

    private ActivityWriter activityWriter;

    private String activitiesToWrite = "";

    public ActivityScreen(String Date, String Username) {

        super(Username, "Search for an activity");



        date = Date;
        username = Username;
        activityWriter = new ActivityWriter(username);
        displayComponents();
    }

    public void displayComponents() {

        //Create the panel on the left where the user picks their activity
        Panel enterPanel = new Panel(400, 500, Color.white);
        enterPanel.setLayout(new GridBagLayout());

        //Create the panel on the right which displays the activities
        Panel activitiesPanel = new Panel(300, 500, Color.lightGray);
        activitiesPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        //JLabel instructionLabel = new JLabel("Please select one of the activities");


        String activities [] = {"Walking", "Running", "Cycling", "Swimming", "Calisthenics", "Weightlifting", "Other Sport"};
        activityDropDown = new JComboBox(activities);
        activityDropDown.setPreferredSize(new Dimension(200, 25));

        confirmButton = new JButton("Confirm Activity");
        confirmButton.addActionListener(this);

        doneButton = new JButton("Done");
        doneButton.addActionListener(this);



        gbc.gridx = 0;
        gbc.gridy = 2;
        enterPanel.add(activityDropDown, gbc);


        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(10, 0, 0, 0);
        enterPanel.add(confirmButton, gbc);


        gbc.gridx = 0;
        gbc.gridy = 4;
        enterPanel.add(doneButton, gbc);



        activtityArea = new JTextArea("Activities for " + date + ":\n");
        setActivitiesText();
        activtityArea.setFont(new Font("Arial", Font.BOLD, 16));
        activtityArea.setPreferredSize(new Dimension(300, 500));
        activtityArea.setBackground(Color.lightGray);
        JScrollPane scrollPane = new JScrollPane(activtityArea);
        scrollPane.setHorizontalScrollBar(null);
        mainFrame.add(scrollPane, BorderLayout.EAST);



        mainFrame.add(enterPanel, BorderLayout.WEST);

        mainFrame.setVisible(true);
    }

    public void setActivitiesText() {
        String setText = "";
        try {
            setText = activityWriter.readActivity(date);
            if (!setText.equals("")) { //Then the user has data for this date
                String split[] = setText.split("[,]", 0); //Separate where the commas are
                setText = "";
                int i = 0;
                for (String part: split) {
                    if (i != 0) {
                        System.out.println("part is: " + part);
                        setText += "\n" + part;
                        activitiesToWrite += part + ",";
                    }
                    i ++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IO exception when reading activities from the user's activities file...");
        }
        activtityArea.setText(activtityArea.getText() + setText);
    }

    public int enterMinutesWindow() {
        String minutesPane = JOptionPane.showInputDialog("Enter the number of minutes you spent on this activity: ");
        while (minutesPane != null && !MainMenuScreen.isNumeric(minutesPane)) { //if its null they have pressed cancel
            minutesPane = JOptionPane.showInputDialog("Please enter a valid measurement");
        }
        try {
            return Integer.valueOf(minutesPane);
        } catch (NullPointerException e) {
            return -1;
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == confirmButton) {
            //Get the number of minutes they spent on this activity
            int minutes = enterMinutesWindow();

            if (minutes != -1) {
                String selectedActivity = String.valueOf(activityDropDown.getSelectedItem());
                ActivityCalculator activityCalculator = new ActivityCalculator();

                String caloriesBurned = String.valueOf(activityCalculator.caloriesBurned(50, selectedActivity.toLowerCase(), minutes));

                System.out.println("caloriesBurned is: " + caloriesBurned);
                String toWrite = caloriesBurned;
                //Now update or insert data for this date for this user
                dataRecorder.updateCaloriesBurned(date, toWrite);
                activtityArea.setText(activtityArea.getText() + " \n" + selectedActivity + " - " + minutes + " minutes");
                activitiesToWrite = activitiesToWrite + "," + selectedActivity + " - " + minutes + " minutes,";

            }

        } else if (e.getSource() == doneButton) {
            System.out.println("done button pressed");
            if (!activitiesToWrite.equals("")) {
                try {
                    activityWriter.writeActivity(date, activitiesToWrite);
                } catch (IOException e1) {
                    e1.printStackTrace();
                    System.out.println("IO exception when writing the activities to the user's activity file...");
                }
            }
            mainFrame.dispose();
            MainMenuScreen.start(username);
        }
    }



    public static void main(String args[]) {
        new ActivityScreen("16-04-2021", "test#0");
    }


}
