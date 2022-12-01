import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GraphScreen extends BasicSmallScreen implements ActionListener {

    private String date, username;
    private DataRecorder dataRecorder;
    private JCheckBox consumed, burned, weight, protein, carbs, fat;
    private JButton confirm, back;
    private ButtonGroup checkBoxes;

    public GraphScreen(DataRecorder recorder, String Username) {

        super();
        username = Username;
        draw("Select a graph to view");
        dataRecorder = recorder;
        mainFrame.setLayout(new BorderLayout());
        displayComponents();
    }

    public void displayComponents() {
        Panel panel = new Panel(400, 350, Color.lightGray);
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        checkBoxes = new ButtonGroup(); // Created so that only one of the checkboxes will ever be selected at a time
        //consumed = new JCheckBox("Gain Weight");

        //Create the labels
        JLabel info = new JLabel("Please select a data category");
        info.setBackground(Color.lightGray);
        info.setFont(new Font("Arial", Font.BOLD, 13));
        info.setBorder(BorderFactory.createLineBorder(Color.black));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(info, gbc);





        consumed = new JCheckBox("Calories Consumed");
        consumed.setBackground(Color.lightGray);
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(20, 0, 0, 0);
        panel.add(consumed, gbc);

        burned = new JCheckBox("Calories Burned");
        burned.setBackground(Color.lightGray);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 0, 0, 0);
        panel.add(burned, gbc);

        weight = new JCheckBox("Weight");
        weight.setBackground(Color.lightGray);
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(weight, gbc);

        protein = new JCheckBox("Protein Consumed");
        protein.setBackground(Color.lightGray);
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(protein, gbc);

        carbs = new JCheckBox("Carbohydrates Consumed");
        carbs.setBackground(Color.lightGray);
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(carbs, gbc);

        fat = new JCheckBox("Fat");
        fat.setBackground(Color.lightGray);
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(fat, gbc);


        //Create the confirm choice button
        confirm = new JButton("View Graph");
        confirm.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(confirm, gbc);

        //Create the back button
        back = new JButton("Back");
        back.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 8;
        panel.add(back, gbc);


        //Add these to the button group so that only one can be selected at a time
        checkBoxes.add(consumed);
        checkBoxes.add(burned);
        checkBoxes.add(weight);
        checkBoxes.add(protein);
        checkBoxes.add(carbs);
        checkBoxes.add(fat);


        mainFrame.add(panel, BorderLayout.CENTER);
        mainFrame.setVisible(true);
    }

    public int getNumberOfDaysBack() {
        String days = JOptionPane.showInputDialog("Enter number of days of data you wish to view (must be greater than 1): ");

        while ((days != null && !MainMenuScreen.isNumeric(days) || days.equals("1"))) { //if its null they have pressed cancel
            days = JOptionPane.showInputDialog("Please enter a valid measurement");
        }

        System.out.println("days is: " + days);
        try {
            return Integer.valueOf(days);
        } catch (NumberFormatException e) {
            return -1; //The user pressed the exit button instead
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == confirm) {
            //Now we load the line graph for their choice
            int days = getNumberOfDaysBack();
            if (days != -1) {
                try {
                    if (consumed.isSelected()) {
                        new GraphCreator(dataRecorder.getDataForGraph(1), days, "Calories", "(kcal)",
                                "Calories Consumed Line Graph");
                    } else if (burned.isSelected()) {
                        new GraphCreator(dataRecorder.getDataForGraph(2), days, "Calories", "(kcal)",
                                "Calories Burned Line Graph");
                    } else if (weight.isSelected()) {
                        new GraphCreator(dataRecorder.getDataForGraph(3), days, "Weight", "(kg)",
                                "Weight Line Graph");
                    } else if (protein.isSelected()) {
                        new GraphCreator(dataRecorder.getDataForGraph(4), days, "Protein", "(g)",
                                "Protein Line Graph");
                    } else if (carbs.isSelected()) {
                        new GraphCreator(dataRecorder.getDataForGraph(5), days, "Carbs", "(g)",
                                "Carbohydrates Line Graph");
                    } else if (fat.isSelected()) {
                        new GraphCreator(dataRecorder.getDataForGraph(6), days, "Fat", "(g)",
                                "Fat Line Graph");
                    } else {
                        new NoticeWindow("Please make a choice", "Please select the data you wish to view");
                    }
                } catch (IOException e1) {
                    System.out.println("IO exception when selecting which graph to display...");
                }
            }

        } else if (e.getSource() == back) {
            mainFrame.dispose();
            MainMenuScreen.start(username);
        }
    }

    public static void main(String[] args) {
        new GraphScreen(new DataRecorder("test#0"), "test#0");
    }
}
