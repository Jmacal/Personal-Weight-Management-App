import com.mindfusion.common.DateTime;
import com.mindfusion.scheduling.Calendar;
import com.mindfusion.scheduling.ThemeType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class CalendarScreen {

    private JFrame mainFrame;
    private Calendar calendar;
    private MainMenuScreen mainScreen;

    public CalendarScreen(MainMenuScreen screen) {

        mainScreen = screen;
        DataRecorder dataRecorder = mainScreen.dataRecorder;
        mainFrame = screen.mainFrame;
        //mainFrame.setLocationRelativeTo(null); // Centre the window
        // mainFrame.setLayout(null); // Allows the use of absolute values when positioning components


        //mainFrame.setSize(900, 700);
        //mainFrame.setResizable(false);
    }

    public void displayCalendar (JPanel panel) {

        Calendar calendar = new Calendar();
        calendar.setTheme(ThemeType.Light);
        calendar.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        GridBagConstraints gbc = new  GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(calendar, gbc);

        mainFrame.add(panel);

        calendar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    DateTime chosenDate = calendar.getDateAt(e.getX(), e.getY());
                    String dateToUse = mainScreen.constructCorrectDate(chosenDate.getYear() + "-"
                            + chosenDate.getMonth() + "-" + chosenDate.getDay());
                    mainScreen.date.setText(dateToUse);
                    mainScreen.enterInfoLabel.setText("Enter the following information for: " + dateToUse);
                    //Now we must update the text fields to hold the appropriate data
                    String caloriesConsumed = "";
                    String caloriesBurned = "";
                    String weight = "";
                    try {
                        mainScreen.caloriesText.setText(mainScreen.getCurrentData("caloriesConsumed", dateToUse));
                        mainScreen.caloriesBurnedText.setText(mainScreen.getCurrentData("caloriesBurned", dateToUse));
                        mainScreen.weightText.setText(mainScreen.getCurrentData("weight", dateToUse));
                    } catch (IOException IOe) {
                        System.out.println("IO exception when fetching data about users data for the main screen...");
                    }

                }
            }
        });
    }

    public static void main(String args[]) {
        CalendarScreen main = new CalendarScreen(new MainMenuScreen("test1"));
        main.displayCalendar(new JPanel());
    }


}
