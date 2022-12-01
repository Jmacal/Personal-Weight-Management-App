import java.io.File;
import java.io.IOException;

public class SpawnAccountFiles {

	public SpawnAccountFiles() throws IOException {
		File userDirectory = new File("./userinfo");
		File activitiesDirectory = new File("./userinfo/activities");
		if (!userDirectory.exists()) {
			userDirectory.mkdir();
			activitiesDirectory.mkdir();
		}
		if (!checkForUserList()) {
			new File("./userinfo/users.txt").createNewFile();
		}
	}

	private boolean checkForUserList() {
		if (new File("./userinfo/users.txt").exists()) {
			return true;
		} else {
			return false;
		}
	}
}
