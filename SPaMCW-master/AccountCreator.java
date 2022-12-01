import java.io.*;

public class AccountCreator {

	String username;

	public AccountCreator(String name, String target, String start, String gain, String height, String age, String male) throws IOException {
		writeNameToFile(name);
		writeInfoToFile(target, start, gain, height, age, male);
	}

	private int getID() throws IOException {
		BufferedReader userFile = new BufferedReader(new FileReader("./userinfo/users.txt"));
		String currentLine;
		int lineCount = 0;
		String lastLine = null;
		int id;
		while ((currentLine = userFile.readLine()) != null) {
			lastLine = currentLine;
			lineCount++;
		}
		if (lineCount == 0) {
			id = 0;
		} else {
			id = Integer.parseInt(lastLine.split("#")[1]) + 1;
		}
		userFile.close();
		return id;
	}

	private void writeNameToFile(String name) throws IOException {
		BufferedWriter writingUser = new BufferedWriter(new FileWriter("./userinfo/users.txt", true));
		username = name + "#" + Integer.toString(getID());
		writingUser.write(username);
		writingUser.newLine();
		writingUser.close();
	}

	private void makeUserTextFile() throws IOException {
		File userFile = new File("./userinfo/" + username + ".txt");
		File userActivityFile = new File("./userinfo/activities/" + username + ".txt");
		if (!userFile.exists() && !userActivityFile.exists()) {
			userFile.createNewFile();
			userActivityFile.createNewFile();
		}
	}

	private void writeInfoToFile(String targetWeight, String startWeight, String loseWeight, String height, String age, String male) throws IOException {
		makeUserTextFile();
		BufferedWriter userInfoWriter = new BufferedWriter(new FileWriter("./userinfo/" + username + ".txt", false));
		userInfoWriter.write(targetWeight + "," + startWeight + "," + loseWeight);
		userInfoWriter.newLine();
		userInfoWriter.write("0");
		userInfoWriter.newLine();
		userInfoWriter.write(height + "," + age + "," + male);
		userInfoWriter.newLine();
		userInfoWriter.close();
	}



	/*
	public static boolean userExists(String username) throws IOException{
		try {
			BufferedReader userFile = new BufferedReader(new FileReader("./userinfo/users.txt"));
			String line;

			while ((line = userFile.readLine()) != null) {
				if (line.equals(username)) {
					return true;
				}
			}
			return false;
		} catch (FileNotFoundException e) {
			System.out.println("file not found");
		}
		return false;
	}
	 */

	/*public static boolean skipUserEntry(String username) throws IOException{
		try{
			String filePath = "./userinfo/" + username;
			BufferedReader userFile = new BufferedReader(new FileReader(filePath));
			int count = 0;
			String line;
			while (true) {
				try {
					if (!((userFile.readLine()) != null)) break;
				} catch (IOException e) {
					e.printStackTrace();
				}
				count++;
				}
			if (count >= 1){
				return true;
			} else{
				return false;
			}
		} catch (FileNotFoundException e){
			System.out.println("Couldnt find user file");
		}
		return false;
	}
	 */
}
