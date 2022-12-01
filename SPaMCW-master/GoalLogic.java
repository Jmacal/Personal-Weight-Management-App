import java.io.*;
import java.util.ArrayList;

public class GoalLogic {

    private String filePath;

    public GoalLogic(String username) {
        filePath = "./userinfo/" + username + ".txt";
    }

    public String getGoalWeight() throws IOException {
        BufferedReader fileReader = new BufferedReader(new FileReader(filePath));
        StringBuffer fileRewrite = new StringBuffer();
        String line;
        int count = 0;
        String[] splitLine;
        String goalWeight = "";
        while ((line = fileReader.readLine()) != null) {
            if (count == 0){
                splitLine = line.split(",");
                goalWeight = splitLine[0];
            }
            fileRewrite.append(line);
            fileRewrite.append("\n");
            count++;
        }
        FileOutputStream fileOut = new FileOutputStream(filePath);
        fileOut.write(fileRewrite.toString().getBytes());
        fileOut.close();
        fileReader.close();
        return goalWeight;
    }

    public String getAdditionalData(String heading) throws IOException {
        int desiredSplit = 0; //Different data is stored in each column, so depending on the data we want we access a different column

        if (heading.equals("age")) {
            desiredSplit = 1;
        } else if (heading.equals("male")) {
            desiredSplit = 2;
        }
        BufferedReader fileReader = new BufferedReader(new FileReader(filePath));
        StringBuffer fileRewrite = new StringBuffer();
        String line;
        int count = 0;
        String[] splitLine;
        String height = "";
        while ((line = fileReader.readLine()) != null) {
            if (count == 2){
                splitLine = line.split(",");
                height = splitLine[desiredSplit];
            }
            fileRewrite.append(line);
            fileRewrite.append("\n");
            count++;
        }
        FileOutputStream fileOut = new FileOutputStream(filePath);
        fileOut.write(fileRewrite.toString().getBytes());
        fileOut.close();
        fileReader.close();
        return height;
    }

    public void changeGoalWeight(String newGoal) throws IOException {
      BufferedReader fileReader = new BufferedReader(new FileReader(filePath));
      StringBuffer fileRewrite = new StringBuffer();
      String line;
      int count = 0;
      String[] splitLine;
      while ((line = fileReader.readLine()) != null) {
          if (count == 0){
              splitLine = line.split(",");
              splitLine[0] = newGoal;
              line = "";
              for (int i = 0; i<splitLine.length; i++) {
                  line = line + splitLine[i] + ",";
              }
              line = line.substring(0, line.length() - 1);
          }
          fileRewrite.append(line);
          fileRewrite.append("\n");
          count++;
      }
      FileOutputStream fileOut = new FileOutputStream(filePath);
      fileOut.write(fileRewrite.toString().getBytes());
      fileOut.close();
      fileReader.close();
    }
}
