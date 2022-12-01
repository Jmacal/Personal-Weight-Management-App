import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class FoodDatabaseScreen extends SearchForScreen implements ActionListener{


    private ArrayList<FoodItem> allFoods = new ArrayList<FoodItem>();
    private String date;

    private JTextArea mealArea;
    private JTextField foodNameText;
    private JLabel caloriesLabel, proteinLabel, fatLabel, carbsLabel;
    private JComboBox foodOptions;
    private DataRecorder dataRecorder;
    private JButton confirmChoiceButton, addFoodButton, searchFoodButton, doneButton;

    public FoodDatabaseScreen(String Date, String Username) {

        super(Username, "Search for a food!");
        date = Date;

        dataRecorder = new DataRecorder(Username);



        displayComponents();
    }

    public void displayComponents() {

        Panel mainPanel = new Panel(700, 500, Color.white);
        mainPanel.setLayout(new GridBagLayout());

        //mealArea = new JTextArea("Food");
        //mealArea.setPreferredSize(new Dimension(300, 500));
        //mealArea.setBackground(Color.lightGray);

        //JScrollPane scrollPane = new JScrollPane(mealArea);
        //scrollPane.setHorizontalScrollBar(null);
        //mainFrame.add(scrollPane, BorderLayout.EAST);


        foodNameText = new JTextField(20);
        foodNameText.addActionListener(this);

        searchFoodButton = new JButton("Search");
        searchFoodButton.addActionListener(this);

        JLabel gramsLabel = new JLabel("Per 100g:");
        //gramsLabel.setBackground(Color.lightGray);

        caloriesLabel = new JLabel("Total Calories: ");
        caloriesLabel.setBackground(Color.lightGray);
        caloriesLabel.setOpaque(true);

        proteinLabel = new JLabel("Total Protein: ");
        proteinLabel.setBackground(Color.lightGray);
        proteinLabel.setOpaque(true);

        fatLabel = new JLabel("Total Fat: ");
        fatLabel.setBackground(Color.lightGray);
        fatLabel.setOpaque(true);

        carbsLabel = new JLabel("Total Carbs: ");
        carbsLabel.setBackground(Color.lightGray);
        carbsLabel.setOpaque(true);

        String[] tempList = {"Please enter a food above"};
        foodOptions = new JComboBox(tempList);
        foodOptions.addActionListener(this);

        confirmChoiceButton = new JButton("Confirm Choice");
        confirmChoiceButton.addActionListener(this);

        addFoodButton = new JButton("Add Another Food");
        addFoodButton.addActionListener(this);

        doneButton = new JButton("Done");
        doneButton.addActionListener(this);


        GridBagConstraints gbc = new GridBagConstraints();

        //Add to the first column
        //Place the textField where the user enters the food
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(20, 0, 20, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(foodNameText, gbc);


        //Place the label saying per 100g
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 0, 0, 0);
        mainPanel.add(gramsLabel, gbc);


        //Place the label informing them about the calories below the label entry
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 0, 0);
        mainPanel.add(caloriesLabel, gbc);


        //Place the label informing them about the fat
        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(proteinLabel, gbc);


        //Place the label informing them about the carbs
        gbc.gridx = 0;
        gbc.gridy = 5;
        mainPanel.add(fatLabel, gbc);


        //Place the label informing them about the protein
        gbc.gridx = 0;
        gbc.gridy = 6;
        mainPanel.add(carbsLabel, gbc);


        //Place the drop down menu where the user can find the food they are referring to
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.insets = new Insets(40, 0, 0, 0);
        mainPanel.add(foodOptions, gbc);


        //Place the confirm food button
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.insets = new Insets(10, 0, 20, 0);
        mainPanel.add(confirmChoiceButton, gbc);

        //Place the done button
        gbc.gridx = 0;
        gbc.gridy = 11;
        mainPanel.add(doneButton, gbc);


        //Add to the second column
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(20, 5, 20, 0);
        mainPanel.add(searchFoodButton, gbc);


        mainFrame.add(mainPanel, BorderLayout.WEST);


        mainFrame.setVisible(true);

    }

    public double enterQuantityWindow() {


        String foodQuantity = JOptionPane.showInputDialog("Enter food weight in grams:");

        while (foodQuantity != null && !MainMenuScreen.isNumeric(foodQuantity)) { //if its null they have pressed cancel
            foodQuantity = JOptionPane.showInputDialog("Please enter a valid measurement");
        }

        try {
            return Double.parseDouble(foodQuantity);
        } catch (NullPointerException e) {
            return -1;
        }
    }


    public void actionPerformed(ActionEvent e) {
        if ((e.getSource() == foodNameText) || (e.getSource() == searchFoodButton)) {


            String userInput = foodNameText.getText();


            QueryFoodDatabase DBQuery = new QueryFoodDatabase();
            DBQuery.searchDB(userInput);
            allFoods = DBQuery.getFoodItemArray();




            ArrayList<String> validFoods = new ArrayList<String>();

            for (FoodItem food : allFoods) {
                String name = food.getFoodName();
                validFoods.add(name);
            }
            foodOptions.removeAllItems();
            for (String food : validFoods) {
                foodOptions.addItem(food);
            }

            //foodOptions.insertItemAt("Please select from below...", 0);


        } else if (e.getSource() == foodOptions) { //If the combo box is selected
            JComboBox tempDropDown = (JComboBox) e.getSource(); //stores the combo box again
            String selected = String.valueOf(tempDropDown.getSelectedItem());


            for (FoodItem food : allFoods) {
                //find matching item
                if (food.getFoodName().equals(selected) && allFoods.indexOf(food) == tempDropDown.getSelectedIndex()) {
                    System.out.println("here");
                    //get nut.info of that item - displayed and then used for saving data with confirm button
                    caloriesLabel.setText("Total Calories: " + food.getCalories() + "kcal");
                    proteinLabel.setText("Total Protein: " + food.getProtein() + "g");
                    fatLabel.setText("Total Fat: " + food.getFats() + "g");
                    carbsLabel.setText("Total Carbs: " + food.getCarbs() + "g");

                    break;
                }
            }
        } else if (e.getSource() == confirmChoiceButton) {
            //Ask for a quantity
            if (foodOptions.getItemCount() == 1) {
                if (foodOptions.getItemAt(0) == "Please enter a food above") {
                    return;
                }
            }
            Double foodQuantity = enterQuantityWindow();
            if (foodQuantity != -1) {
                Double divideBy = 100.0 / foodQuantity; //So that the correct values for calories, protein etc... can be stored
                //Make sure its valid

                String selected = String.valueOf(foodOptions.getSelectedItem());

                try {
                    Double currentCalories = 0.0;
                    Double currentProtein = 0.0;
                    Double currentCarbs = 0.0;
                    Double currentFat = 0.0;
                    try {
                        currentCalories = Double.parseDouble(MainMenuScreen.getCurrentData("caloriesConsumed", date));
                        currentProtein = Double.parseDouble(MainMenuScreen.getCurrentData("protein", date));
                        currentCarbs = Double.parseDouble(MainMenuScreen.getCurrentData("carbs", date));
                        currentFat = Double.parseDouble(MainMenuScreen.getCurrentData("fat", date));
                    } catch (NumberFormatException e2) {
                        //there exists no data for it, so do nothing and keep it at 0
                    }
                    for (FoodItem food : allFoods) {
                        //find matching item
                        int chosenCals = 0;
                        Double chosenPro = 0.0;
                        Double chosenCarbs = 0.0;
                        Double chosenFats = 0.0;
                        if (food.getFoodName().equals(selected) && allFoods.indexOf(food) == foodOptions.getSelectedIndex()) {
                            chosenCals = (int)(food.getCalories() / divideBy);
                            chosenPro = (food.getProtein() / divideBy);
                            chosenCarbs = (food.getCarbs() / divideBy);
                            chosenFats = (food.getFats() / divideBy);

                            int finalCalories = (int)(chosenCals + currentCalories);
                            Double finalProtein = (chosenPro + currentProtein);
                            Double finalCarbs = (chosenCarbs + currentCarbs);
                            Double finalFat = (chosenFats + currentFat);
                            System.out.println("final calories are: " + finalCalories + "," + currentCalories);

                            String toWrite = ((finalCalories) + "," + (finalProtein) + "," + (finalCarbs)
                                    + "," + (finalFat));
                            System.out.println("writing: " + toWrite + "\n");
                            dataRecorder.writeToFile(toWrite, date, "automatic");
                            break;
                        }

                    }

                } catch (IOException e2) {
                    System.out.println("IO exception when fetching data about users data for the food access screen");
                }
            }





            //Save it to the file
        } else if (e.getSource() == doneButton) {
            System.out.println("done button pressed on the food database screen");
            mainFrame.dispose();
            MainMenuScreen.start(MainMenuScreen.username);
        }


    }

    public static void main(String[] args) {
        new FoodDatabaseScreen("14-04-2021", "test#0");
    }

}
