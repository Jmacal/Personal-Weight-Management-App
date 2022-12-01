import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

// TODO - Indefinite weight loss? Eg maintain current weight, lose this amount per week?
public class GoalWeight {

    LocalDate deadline;
    Weight goalWeight;

    public GoalWeight(LocalDate targetDate, Weight targetWeight) {
        deadline = targetDate;
        goalWeight = targetWeight;
    }

    public long getDaysToDeadline() {
        return ChronoUnit.DAYS.between(LocalDate.now(), deadline);
    }

    public Weight getGoalWeight() {
        return goalWeight;
    }

    public boolean hasDeadlinePassed(LocalDate currentDate) {
        return currentDate.isEqual(deadline) || deadline.isAfter(currentDate);
    }
}