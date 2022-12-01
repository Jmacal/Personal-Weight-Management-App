import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

// TODO - Class for tracked statistics

public class User {

    int heightCm;
    LocalDate birthday;
    boolean useMaleCalculations;

    GoalWeight goalCurrent = null;
    ArrayList<GoalWeight> goalPast = new ArrayList<>();
    ArrayList<Weight> weightPast = new ArrayList<>();

    // Required for calorific calculations
    // For most users, they should use their sex
    // Transgender and intersex individuals will need to consult with doctors
    //      about which gender fits them best for these calculations
    // TODO - Add notice about which gender for user in app
    final static int GENDER_MALE = 0;
    final static int GENDER_FEMALE = 1;

    // TODO - Research age with weight loss app. Should this be recommended to underage users?
    // TODO - Assuming height is constant. What happens if this changes?
    public User(int gender, LocalDate birthday, int heightCm) throws UnknownGenderException{
        this.heightCm = heightCm;
        if (gender == GENDER_MALE) {
            useMaleCalculations = true;
        } else if (gender == GENDER_FEMALE) {
            useMaleCalculations = false;
        } else {
            throw new UnknownGenderException();
        }

        // TODO - Check birthday in oast, age, etc
        this.birthday = birthday;
    }

    public int getHeightCentimeters() {
        return heightCm;
    }

    // TODO - Region support?
    public int getAgeYears() {
        Period difference = Period.between(LocalDate.now(), birthday);
        return difference.getYears();
    }

    public boolean canUseMaleCalorieCalculations() {
        return useMaleCalculations;
    }

    // TODO - Kilograms
    public float getLastRecordedWeight() {
        return 0f;
    }

    public static class UnknownGenderException extends Exception { 
        private static final long serialVersionUID = 1L;

        public UnknownGenderException() {
            super("Invalid calculation gender provided!");
        }
    }
}
