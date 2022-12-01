import java.io.*;

public class ActivityWriter{

    private String filePath;

    public ActivityWriter(String username){
        filePath = "./userinfo/activities/" + username + ".txt";
    }

    public void writeActivity(String date, String toWrite) throws IOException {
        BufferedReader fileReader = new BufferedReader(new FileReader(filePath));
        boolean lineFound = false;
        StringBuffer fileRewrite = new StringBuffer();
        String line;
        FileOutputStream fileOut = new FileOutputStream(filePath);
        while ((line = fileReader.readLine()) != null){
            if (line.startsWith(date)){
                lineFound = true;
                line = date + "," + toWrite;
            }
            fileRewrite.append(line);
            fileRewrite.append("\n");
        }
        if (lineFound){
            fileOut.write(fileRewrite.toString().getBytes());
        } else{
            String tempString = date + "," + toWrite;
            fileRewrite.append(tempString);
            fileRewrite.append("\n");
            fileOut.write(fileRewrite.toString().getBytes());
        }
        fileOut.close();
        fileReader.close();
    }

    public String readActivity(String date) throws IOException {
        BufferedReader fileReader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = fileReader.readLine()) != null){
            if (line.startsWith(date)){
                fileReader.close();
                return line;
            }
        }
        return "";
    }
}
