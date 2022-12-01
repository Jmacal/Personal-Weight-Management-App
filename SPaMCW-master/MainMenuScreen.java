import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainMenuScreen implements ActionListener {

    public static DataRecorder dataRecorder;

    protected JFrame mainFrame;
    public static String username;
    public JTextField caloriesText, caloriesBurnedText, weightText;


    private JButton saveChangesButton, searchForFood, searchForActivity;
    private JMenuItem about, exitBar, lineGraph, goalsTab, additionalFoodStats, tips;

    public static NoticeWindow currentNoticeWindow; //Setting each notice window to the same variable ensures only one window
                                              // will ever be open at once

    public JLabel date, enterInfoLabel, dailyCalorieGoalLabel;
    public static boolean noticeWindowOpen;


    public MainMenuScreen(String Username) {
        username = Username;
        mainFrame = new JFrame();
        mainFrame.setTitle("Main Menu");
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout()); // Allows the use of absolute values when positioning components
        mainFrame.setSize(700, 500);
        mainFrame.setLocationRelativeTo(null); // Centre the window
        mainFrame.setResizable(false);

        dataRecorder = new DataRecorder(username);
    }

    public void displayMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(Color.lightGray);
        menuBar.setBorder(BorderFactory.createLineBorder(Color.black, 1));

        //Create the help menu bar
        JMenu helpMenu = new JMenu("Help");
        about = new JMenuItem("About");
        exitBar = new JMenuItem("Exit");
        exitBar.addActionListener(this);
        helpMenu.add(about);
        helpMenu.add(exitBar);


        //Create the graphical view menu bar
        JMenu graphicalView = new JMenu("View Graphical Data");
        //JMenuItem pieChart = new JMenuItem("Pie Chart");
        lineGraph = new JMenuItem("Line Graph");
        //graphicalView.add(pieChart);
        graphicalView.add(lineGraph);
        lineGraph.addActionListener(this);

        //Create the more stats menu bar
        JMenu moreStats = new JMenu("More Statistics");
        additionalFoodStats = new JMenuItem("Additional food statistics");
        additionalFoodStats.addActionListener(this);
        goalsTab = new JMenuItem("View your goals");
        goalsTab.addActionListener(this);
        moreStats.add(additionalFoodStats);
        moreStats.add(goalsTab);


        JMenu adviceMenu = new JMenu("Advice");
        tips = new JMenuItem("Calories to consume per day");
        tips.addActionListener(this);
        adviceMenu.add(tips);

        menuBar.add(helpMenu);
        menuBar.add(graphicalView);
        menuBar.add(moreStats);
        menuBar.add(adviceMenu);

        mainFrame.setJMenuBar(menuBar);
    }

    public String constructCorrectDate(String date) {
        String[] parts = date.split("[-]", 0);
        String year = parts[0];
        String month = parts[1];
        String day = parts[2];

        if (month.length() < 2) {
            month = "0" + month;
        }
        if (day.length() < 2) {
            day = "0" + day;
        }

        String finalDate = day + "-" + month + "-" + year;
        return finalDate;
    }

    public void displayComponents() {


        String currentDate = constructCorrectDate(String.valueOf(java.time.LocalDate.now()));
        String caloriesConsumed = "";
        String caloriesBurned = "";
        String weight = "";
        try {
            System.out.println("before");
            caloriesConsumed = getCurrentData("caloriesConsumed", currentDate);
            caloriesBurned = getCurrentData("caloriesBurned", currentDate);
            weight = getCurrentData("weight", currentDate);
            System.out.println("after");
        } catch (IOException e) {
            System.out.println("IO exception when fetching data about users data for the main screen...");
        }

        //Create the calendar panel
        Panel calendarPanel = new Panel(250, 500, Color.lightGray);
        calendarPanel.setLayout(new GridBagLayout());

        //Create the constraints for the panels we create
        GridBagConstraints gbc = new GridBagConstraints();


        //Create the calendar and add it to its panel
        CalendarScreen calendar = new CalendarScreen(this);
        calendar.displayCalendar(calendarPanel);



        //Create the date label and add it to the calendar panel
        date = new JLabel(currentDate);
        date.setBackground(Color.white);
        date.setOpaque(true);
        date.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 50, 5, 50);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        calendarPanel.add(date, gbc);





        //Create the panel where the user can input data
        Panel inputPanel = new Panel(450, 500, Color.gray);
        inputPanel.setLayout(new GridBagLayout());







        //Create the label showing the calories eaten
        JLabel calorieLabel = new JLabel("Calories Consumed(kcal):");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 100, 50, 5);
        inputPanel.add(calorieLabel, gbc);



        //Create the label showing the calories burned
        JLabel caloriesBurnedLabel = new JLabel("Calories Lost(kcal):", SwingConstants.RIGHT);
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(caloriesBurnedLabel, gbc);


        //Create the label showing the weight
        JLabel weightLabel = new JLabel("Weight(kg):", SwingConstants.RIGHT);
        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(weightLabel, gbc);


        //Create the text field where the user can enter the calories eaten
        caloriesText = new JTextField(caloriesConsumed, 5);
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 0, 50, 10);
        gbc.fill = GridBagConstraints.NONE;
        inputPanel.add(caloriesText, gbc);

        //Create the button where the user can search for a food
        searchForFood = new JButton("Search for Food");
        searchForFood.setBackground(Color.white);
        searchForFood.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
        searchForFood.addActionListener(this);
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(searchForFood, gbc);


        //Create the text field where the user can enter the calories burned
        caloriesBurnedText = new JTextField(caloriesBurned, 5);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        inputPanel.add(caloriesBurnedText, gbc);

        //Create the button where the user can search for a food
        searchForActivity = new JButton("Search for Activity");
        searchForActivity.setBackground(Color.white);
        searchForActivity.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
        searchForActivity.addActionListener(this);
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(searchForActivity, gbc);


        //Create the text field where the user can enter the weight on the selected day
        weightText = new JTextField(weight, 5);
        gbc.gridx = 1;
        gbc.gridy = 3;
        inputPanel.add(weightText, gbc);





        //Create the label at the top of the panel instructing the user to enter data
        enterInfoLabel = new JLabel("Enter the following information for: " + getSelectedDate(), SwingConstants.CENTER);
        enterInfoLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 0;

        gbc.gridwidth = 3;

        inputPanel.add(enterInfoLabel, gbc);


        //Create the button allowing the user to save any changes made
        saveChangesButton = new JButton("Save Changes");
        saveChangesButton.setFont(new Font("Arial", Font.BOLD, 16));
        saveChangesButton.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
        saveChangesButton.setBackground(Color.white);
        saveChangesButton.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 50, 50, 40);
        inputPanel.add(saveChangesButton, gbc);







        mainFrame.add(calendarPanel, BorderLayout.WEST);
        mainFrame.add(inputPanel, BorderLayout.EAST);

        mainFrame.setVisible(true);
    }

    //Gets the current value for this heading for the user, if none then returns ""
    public static String getCurrentData(String heading, String date) throws IOException {
        //Check if the user has any data for this heading for this day, if yes then return it, if no then return ""
        System.out.println("date is: " + date);
        String line = dataRecorder.readSpecificData(date);
        System.out.println("LINE IS: " + line);
        if (!line.equals("")) {
            int column = 0; //the columns start at 0 (so the date is in column 0)
            System.out.println("line is: " + line);
            switch (heading) {
                case "caloriesConsumed":
                    column = 1;
                    break;
                case "caloriesBurned":
                    column = 2;
                    break;
                case "weight":
                    column = 3;
                    break;
                case "protein":
                    column = 4;
                    break;
                case "carbs":
                    column = 5;
                    break;
                case "fat":
                    column = 6;
                    break;
            }
            String[] split = line.split("[,]", 0);

            if (split.length > 4) { //They have info for protein etc...
                return split[column];
            } else {
                if (column == 4 || column == 5 || column == 6) { //These stats don't exist
                    return "";
                } else {
                    return split[column];
                }
            }

        }
        return "";
    }

    //Returns the current date selected by the user
    public String getSelectedDate() {
        return date.getText();
    }

    //This method will save the changes, but if the data input is invalid will return false and will be dealt with
    public boolean saveChanges() {
        //The try catch checks if the string is numeric

        return (isNumeric(caloriesText.getText()) && isNumeric(caloriesBurnedText.getText()) && isNumeric(weightText.getText()));

        //return true; //Only return true if the data is saved properly
    }

    public static boolean isNumeric(String text) {

        System.out.println("test is: " + text);

        try {
            Double.parseDouble(text);
            if (Double.parseDouble(text) > 0) { //Must be a positive value
                return true;
            } else {
                return  false;
            }
        } catch (NumberFormatException e) {
            System.out.println("Ensure you enter a valid integer");
        }
        return false;
    }

    public void setCorrectDatValues(String date) {
        try {
            caloriesText.setText(getCurrentData("caloriesConsumed", date));
            caloriesBurnedText.setText(getCurrentData("caloriesBurned", date));
            weightText.setText(getCurrentData("weight", date));
        } catch (IOException IOe) {
            System.out.println("IO exception when fetching data about users data for the main screen...");
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveChangesButton) {
            if (!saveChanges()) {
                setCorrectDatValues(getSelectedDate());
                currentNoticeWindow = new NoticeWindow("Error", "Please ensure you enter valid data!");
            } else {
                currentNoticeWindow = new NoticeWindow("Changes Saved", "Changes saved for: "
                        + getSelectedDate());

                String toAdd = caloriesText.getText().replaceAll("\\s", "") + ","
                        + caloriesBurnedText.getText().replaceAll("\\s", "") + ","
                        + weightText.getText().replaceAll("\\s", "");
                try {
                    String line = dataRecorder.readSpecificData(getSelectedDate());
                    if (DataRecorder.commaCount(line) == 6) { //All stats are on this line
                        String split[] = line.split("[,]", 0);
                        toAdd += "," + split[4] + "," + split[5] + "," + split[6];
                    }
                } catch (IOException e1) {
                    System.out.println("IO Exception when pressing save changes button");
                }

                try {
                    dataRecorder.writeToFile(toAdd, getSelectedDate(), "manual");
                } catch (IOException e1) {
                    System.out.println("error writing to the file");
                }

            }
            //noticeWindowOpen = true; //Ensures that multiple cannot be open at once
        } else if (e.getSource() == exitBar) {
            System.exit(0);
        } else if (e.getSource() == searchForFood) {
            mainFrame.dispose();
            new FoodDatabaseScreen(getSelectedDate(), username);
        } else if (e.getSource() == searchForActivity) {
            mainFrame.dispose();
            new ActivityScreen(getSelectedDate(), username);
        } else if (e.getSource() == lineGraph) {
            System.out.println("Clicked");
            mainFrame.dispose();
            new GraphScreen(dataRecorder, username);
        } else if (e.getSource() == additionalFoodStats) {
            mainFrame.dispose();
            new AdditionalStatsScreen(getSelectedDate(), username);
        } else if (e.getSource() == goalsTab) {
            mainFrame.dispose();
            new GoalsScreen(getSelectedDate(), username);
        } else if (e.getSource() == tips) {
            mainFrame.dispose();
            new AdviceScreen(getSelectedDate(), username);
        }
    }

    public static void start(String username) {
        MainMenuScreen main = new MainMenuScreen(username);
        main.displayMenuBar();
        main.displayComponents();
    }


    public static void main(String args[]) {
        MainMenuScreen.start("test#0");
    }

}
