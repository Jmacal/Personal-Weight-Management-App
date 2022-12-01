import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class TempGUIAccessDB extends JFrame implements ActionListener{

    ArrayList<FoodItem> foodItemArray = new ArrayList<FoodItem>();
    Double selectedCals, selectedPro, selectedCarbs, selectedFats = 0.0;
    String [] foodOptions = {"MSG1","MSG2","MSG3"}; //change to blank
    JComboBox cmbFoodList = new JComboBox(foodOptions);
    JLabel lblText = new JLabel();
    JPanel pnlSearch = new JPanel();
    JPanel pnlDropDown = new JPanel();
    JLabel lblEntryField = new JLabel();
    JTextField userEntryField = new JTextField("Search for an item...",30);
    JButton searchButton = new JButton("Search");
    JButton chooseButton = new JButton("Choose");
    public static void main(String[] args){
        //THIS CODE WILL BECOME A METHOD IN CALENDER SCREEN
        //SEARCH BUTTON IS PRESSED (calendar screen) THEN
        //THIS WINDOW IS CREATED - USERINPUT STRING IS PASSED

        DataRecorder dataRecorder = new DataRecorder("test#0");
        try {
            System.out.println(dataRecorder.getMostRecentLine());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("error reading all date line");
        }


        //TempGUIAccessDB window = new TempGUIAccessDB();

        //window.setVisible(true);
    }

    public TempGUIAccessDB(){

        getContentPane().setLayout(new BorderLayout());
        setSize(600,200);
        setResizable(false);
        setTitle("Access DB");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //The search panel includes the search text box for inputting search terms and two buttons for searching and
        //choosing a food item
        pnlSearch.setPreferredSize(new Dimension(600,40));
        pnlSearch.add(userEntryField);
        pnlSearch.add(searchButton);
        pnlSearch.add(chooseButton);
        userEntryField.addActionListener(this);
        searchButton.addActionListener(this);
        chooseButton.addActionListener(this);
        //pnlSearch.add(lblEntryField); //remove when not needed
        add(pnlSearch, BorderLayout.PAGE_START);

        //The drop down panel displays the list of items fetched from the DB and their respective nutrient values
        cmbFoodList.setSelectedIndex(0);
        cmbFoodList.addActionListener(this);
        pnlDropDown.setLayout(new GridLayout(3,0));
        pnlDropDown.setPreferredSize(new Dimension(600,120));
        pnlDropDown.add(new JLabel("Per 100g:"));
        pnlDropDown.add(cmbFoodList);
        pnlDropDown.add(lblText);
        add(pnlDropDown, BorderLayout.PAGE_END);

        pnlDropDown.setBorder(BorderFactory.createTitledBorder("Search Results"));
    }
    //Checks a string if it's a number or not
    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == cmbFoodList){
            JComboBox cb = (JComboBox)e.getSource(); //stores the combo box again
            String selected = (String)cb.getSelectedItem();

            //display nutritional info
            StringBuilder sb = new StringBuilder();

            String nutInfo = "";
            for (FoodItem food : foodItemArray){
                //find matching item
                if (food.getFoodName().equals(selected) && foodItemArray.indexOf(food) == cb.getSelectedIndex()){
                    System.out.println("herre");
                    //get nut.info of that item - displayed and then used for saving data with confirm button
                    selectedCals = food.getCalories();
                    selectedPro = food.getProtein();
                    selectedCarbs = food.getCarbs();
                    selectedFats = food.getFats();
                    sb.append(" Calories: ");
                    sb.append(selectedCals);
                    sb.append(" Protein: ");
                    sb.append(selectedPro);
                    sb.append(" Carbs: ");
                    sb.append(selectedCarbs);
                    sb.append(" Fats: ");
                    sb.append(selectedFats);
                    nutInfo = sb.toString();
                    lblText.setText(nutInfo);
                    System.out.println(foodItemArray.indexOf(food));
                    System.out.println(cb.getSelectedIndex());
                    break;
                }
            }

        }else if (e.getSource() == userEntryField){ //when enter is pressed
            String userInput = userEntryField.getText();
            lblEntryField.setText(userInput);

            QueryFoodDatabase DBQuery = new QueryFoodDatabase();
            DBQuery.searchDB(userInput);

            foodItemArray = DBQuery.getFoodItemArray();
            ArrayList<String> newOptions = new ArrayList<>();
            for (FoodItem food : foodItemArray){
                String name = food.getFoodName();
                newOptions.add(name);
            }
            foodOptions = newOptions.toArray(new String[newOptions.size()]);
            cmbFoodList.removeAllItems();
            for (String s : foodOptions){
                cmbFoodList.addItem(s);
            }
        }else if (e.getSource() == searchButton){ // when search is pressed
            String userInput = userEntryField.getText();
            lblEntryField.setText(userInput);

            QueryFoodDatabase DBQuery = new QueryFoodDatabase();
            DBQuery.searchDB(userInput);

            foodItemArray = DBQuery.getFoodItemArray();
            ArrayList<String> newOptions = new ArrayList<>();
            for (FoodItem food : foodItemArray){
                String name = food.getFoodName();
                newOptions.add(name);
            }
            foodOptions = newOptions.toArray(new String[newOptions.size()]);
            cmbFoodList.removeAllItems();
            for (String s : foodOptions){
                cmbFoodList.addItem(s);
            }
        // Currently not error-proof (eg. Choosing a value before searching)
        // TODO: Fix potential erroneous cases
        }else if (e.getSource() == chooseButton) {// when choose is pressed
            String foodAmount = JOptionPane.showInputDialog("Enter food weight in grams:");
            while(!isNumeric(foodAmount)){
                foodAmount = JOptionPane.showInputDialog("The value entered was not a number. Try again:");
            }

            String selected = (String)cmbFoodList.getSelectedItem();
            for (FoodItem food : foodItemArray){
                //find matching item
                if (food.getFoodName().equals(selected) && foodItemArray.indexOf(food) == cmbFoodList.getSelectedIndex()){
                    //Ratio of 100 to the amount of food entered
                    Double ratio = 100.0/Double.parseDouble(foodAmount);

                    //Nutrients of chosen amount of food
                    Double chosenCals = food.getCalories()/ratio;
                    Double chosenPro = food.getProtein()/ratio;
                    Double chosenCarbs = food.getCarbs()/ratio;
                    Double ChosenFats = food.getFats()/ratio;
                    System.out.println("Test");
                    System.out.println(chosenCals);

                    break;
                }
            }
        }
    }
                //Text box and "Search" button could be moved to calendar screen
                // --> Search/ enter pressed opens up this window, passing in userInput with the combobox and confirm button
                // OR button added to calendar screen that opens this window w/ text field
                // --> Likely an easier option, less refactoring of code needed
                // --> Someone with GUI experience will need to make the appearance of this window up to scratch
                //Confirm is pressed - the values (just cals stored?) are amended to the user's data
                //ADD CONFIRM BUTTON - THIS IS WHEN DATA IS SAVED TO USER'S INFO

}
