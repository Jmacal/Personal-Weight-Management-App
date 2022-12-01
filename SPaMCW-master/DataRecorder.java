import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

public class DataRecorder {
    private String username;
    private BufferedReader userReader;
    private BufferedWriter userWriter;
    private String filePath;

    public DataRecorder(String name){
        username = name;
        filePath = "./userinfo/" + username + ".txt";
    }

    private void openFileReadWrite(){
        try{
            userWriter= new BufferedWriter(new FileWriter(filePath, true));
            userReader = new BufferedReader(new FileReader(filePath));
        } catch(IOException e){
          System.out.println("Error opening readers");
        }
    }

    private void closeFileReadWrite(){
        try{
            userWriter.close();
            userReader.close();
        } catch(IOException e){
          System.out.println("Error closing readers");
        }
    }

    public void writeToFile(String toWrite, String currentDate, String wayOfInput) throws IOException {
        //https://stackoverflow.com/questions/20039980/java-replace-line-in-text-file

        /*if (wayOfInput.equals("manual")) { //Then the user has manually entered the data

        } else if (wayOfInput.equals("automatic")) { //the user has not manually entered the data

        }
         */
        this.openFileReadWrite();
        boolean lineFound = false;
        StringBuffer fileRewrite = new StringBuffer();
        String line;
        while ((line = userReader.readLine()) != null) {
            if (line.startsWith(currentDate)){
                lineFound = true;
                if (lineFound) {
                    int commaCount = 0;
                    for (int i = 0; i < line.length(); i ++) {
                        if (line.charAt(i) == ',') {
                            commaCount ++;
                        }
                    }
                    if (wayOfInput.equals("manual")) { //The user has entered caloriesConsumed, caloriesBurned and weight
                        if (commaCount == 6) { //Then the user has protein etc... stats for this day already
                            String split[] = line.split("[,]", 0);
                            line = currentDate + toWrite + "," + split[4] + "," + split[5] + "," + split[6];

                        } else {
                            line = currentDate + "," + toWrite;
                        }
                    } else { //The user has selected a food and so we must write protein etc...
                        if (commaCount == 6) { //Then the user has consumed, weight etc... data already
                            String split[] = line.split("[,]", 0);
                            String toWriteSplit[] = toWrite.split("[,]", 0);
                            line = currentDate + "," + toWriteSplit[0] + "," + split[2] + "," + split[3] + "," + toWriteSplit[1] + ","
                                    + toWriteSplit[2] + "," + toWriteSplit[3];
                        } else {
                            line = currentDate + "," + toWrite;
                        }

                    }
                }
            }

            fileRewrite.append(line);
            fileRewrite.append("\n");
        }
        if (lineFound) {
            FileOutputStream fileOut = new FileOutputStream(filePath);
            fileOut.write(fileRewrite.toString().getBytes());
            fileOut.close();
        } else {
            if (wayOfInput.equals("manual")) {
                String lineToWrite = currentDate + "," + toWrite;
                userWriter.write(lineToWrite);
            } else {
                System.out.println("automatic");
                String split[] = toWrite.split("[,]", 0);
                System.out.println("split is: " + split[0] + ", " + split[1]);
                toWrite = split[1] + "," + split[2] + "," + split[3];
                String lineToWrite = currentDate + "," + split[0] + ", , " +  "," + toWrite;
                System.out.println("writing: " + lineToWrite);
                userWriter.write(lineToWrite);
            }


            userWriter.newLine();
        }
        this.closeFileReadWrite();
    }

    //Created in case the data is formatted incorrectly for whatever reason it may be...
    public static String correctlyFormatDate(String line) {
        if (line.charAt(10) != ',') {
            line = line.substring(0, 10) + "," + line.substring(10);
        }
        return line;
    }

    public String readSpecificData(String date) throws IOException {

        this.openFileReadWrite();
        String line;

        while ((line = userReader.readLine()) != null) {
            if (line.startsWith(date)) {
                this.closeFileReadWrite();
                System.out.println("LINE being read here is: " + line);
                if (line.length() > 9) {
                    line = DataRecorder.correctlyFormatDate(line);
                }
                return line;
            }
        }
        this.closeFileReadWrite();
        return ""; //returns "" representing no current data for this day
    }

    public ArrayList<String[]> getDataForGraph(int flag) throws IOException {
        //Calories consumed: 1, calories burned: 2, weight: 3, protein: 4, carbs: 5, fat: 6
        ArrayList<String> allDates = readsAllDateLines();
        String thisLine[];
        String toAppend[];
        ArrayList<String[]> data = new ArrayList<String[]>();
        for (String s : allDates){
            thisLine = s.split(",");
            for (String test: thisLine) {
                System.out.println("--> " + test);
            }
            try {
                toAppend = new String[]{thisLine[0], thisLine[flag]};
                data.add(toAppend);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("No data for this");

            }

        }
        return data;
    }

    public ArrayList<String> readsAllDateLines() throws IOException {
        this.openFileReadWrite();
        String line;
        ArrayList<String> matchingLines = new ArrayList<String>();
        while ((line = userReader.readLine()) != null) {
            if (line.length() > 9) {
                line = DataRecorder.correctlyFormatDate(line);
            }
            if (line.length() > 9 && line.substring(0,10).matches("[0-9]{2}-[0-9]{2}-[0-9]{4}")) {
                matchingLines.add(line);
            }
        }
        this.closeFileReadWrite();
        return matchingLines;
    }

    public void incrementPoints() throws IOException {
        this.openFileReadWrite();
        StringBuffer fileRewrite = new StringBuffer();
        String line;
        int temp;
        int count = 0;
        while ((line = userReader.readLine()) != null) {
            if (count == 1){
                temp = Integer.parseInt(line);
                temp++;
                line = Integer.toString(temp);
            }
            fileRewrite.append(line);
            fileRewrite.append("\n");
            count++;
        }
        FileOutputStream fileOut = new FileOutputStream(filePath);
        fileOut.write(fileRewrite.toString().getBytes());
        fileOut.close();
        this.closeFileReadWrite();
    }

    public String getPoints() throws IOException {
        this.openFileReadWrite();
        String line;
        int count = 0;
        while ((line = userReader.readLine()) != null) {
            if (count == 1){
                this.closeFileReadWrite();
                return line;
            }
            count ++;
        }
        this.closeFileReadWrite();
        return "fail";
    }

    private String getMostRecentInput() throws IOException {
        this.openFileReadWrite();
        SimpleDateFormat thisDateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        Date currentNearestDate = new Date();
        try {
            currentNearestDate = thisDateFormatter.parse("10-10-1970");
        } catch (ParseException e) {
            System.out.println("error1");
        }
        Date currentDate = new Date();
        String line;
        while ((line = userReader.readLine()) != null) {

            if (line.length() > 9 && line.substring(0,10).matches("[0-9]{2}-[0-9]{2}-[0-9]{4}")) {

                try {
                    currentDate = thisDateFormatter.parse(line.substring(0,10));
                } catch (ParseException e) {
                    System.out.println("error2");
                }
                if (currentNearestDate.compareTo(currentDate) < 0) {
                    currentNearestDate = currentDate;
                }
            }
        }

        String stringDate = thisDateFormatter.format(currentNearestDate);
        this.closeFileReadWrite();
        return stringDate;

    }

    public void updateCaloriesBurned(String date, String caloriesBurned) {
        String toWrite = "";

        try {
            String line = readSpecificData(date);
            this.openFileReadWrite(); //Must open it here as the readSpecificData methdd closes the fileReader
            if (line.equals("")) { //no current data
                toWrite = date + ", ," + (int)Double.parseDouble(caloriesBurned) + ", ";
                userWriter.write(toWrite);
            } else { //Data already exists for this date

                String split [] = line.split("[,]", 0);
                if (split[2].equals(" ")) { //there is no data entered for the calories burned this day
                    System.out.println("HERE");
                    writeCaloriesBurned(date, caloriesBurned, line);
                } else { //there is an integer value here representing the number of calories burned
                    caloriesBurned = String.valueOf((int)(Double.parseDouble(split[2]) + Double.parseDouble(caloriesBurned)));
                    writeCaloriesBurned(date, caloriesBurned, line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IO exception when updating calories burned");
        }
        this.closeFileReadWrite();
    }

    public static int commaCount(String line) {
        int commaCount = 0;
        System.out.println("line found is: " + line);
        for (int i = 0; i < line.length(); i ++) {
            if (line.charAt(i) == ',') {
                System.out.println("YES");
                commaCount ++;
            }
        }
        return commaCount;
    }

    public void writeCaloriesBurned(String date, String caloriesBurned, String line) {
        int commaCount = commaCount(line);


        String toWrite = "";

        String split [] = line.split("[,]", 0);


        toWrite = split[1] + "," + caloriesBurned + "," + split[3];
        try {
            writeToFile(toWrite, date, "manual");
        } catch (IOException e) {
            System.out.println("IO exception when writing calories burned to file");
        }



    }

    public String getMostRecentLine() throws IOException {

        String nearestDate = this.getMostRecentInput();
        String line;

        this.openFileReadWrite();
        while ((line = userReader.readLine()) != null) {
            if (line.length() > 9 && line.substring(0,10).equals(nearestDate)) {
                this.closeFileReadWrite();
                return line;
            }
        }
        this.closeFileReadWrite();
        return "fail";
    }
}
