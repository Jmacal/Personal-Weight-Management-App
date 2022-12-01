public class Creator {

    public Creator() {
        MainMenuScreen main = new MainMenuScreen("Test#01");
        main.displayMenuBar();
        main.displayComponents();
    }

    public static void main(String args[]) {
        new Creator();
    }

}
