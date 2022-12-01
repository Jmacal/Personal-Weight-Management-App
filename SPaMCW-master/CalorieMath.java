import java.time.LocalDate;

public class CalorieMath {

    final static float CALORIES_PER_KG = 7700;

    // TODO - When generating calorific deficit, consider change in birthday from user
    /* TODO - Offer more specific versions of the BMR equation,
              since the Harris-Benedict equation isn't very accurate with non-average body fat
    */

    // Total Daily Energy Expenditure is the amount of calories burned prior to exercise
    // Otherwise called BMR (Basal Metabolic Rate)
    private static float getBMR_MiffinStJoer(float weight, int heightCm, int ageYears, boolean useMaleCalc) {
        double bmr = (10 * weight) + (6.25 * heightCm) - (5 * ageYears);
        if (useMaleCalc) {
            return (float)(bmr + 5);
        }
        return (float)(bmr - 161);
    }

    public static float getRequiredDailyDeficitForPlan(Weight currentWeight, GoalWeight goal) {
        // TODO - Birthday
        float totalDeficit = goal.getGoalWeight().getKilograms() - currentWeight.getKilograms();
        totalDeficit *= CALORIES_PER_KG;
        long totalDays = goal.getDaysToDeadline();
        return (float)((long)totalDeficit / totalDays);
    }

    public static float getDailyCaloriesForPlan(GoalWeight goal, Weight currentWeight, int heightCm, int ageYears, boolean useMaleCalc) {
        float dailyDecifit = getRequiredDailyDeficitForPlan(currentWeight, goal);
        float bmr = getBMR_MiffinStJoer(currentWeight.getKilograms(), heightCm, ageYears, useMaleCalc);
        return bmr + dailyDecifit;
    }

     public static void main(String[] args) {

         System.out.println(getBMR_MiffinStJoer(55.0f, 185, 21, true));
         LocalDate targetDate = LocalDate.now().plusDays(12);
         Weight currentWeight = new Weight(55.0f);
         Weight targetWeight = new Weight(50.0f);
         GoalWeight testGoal = new GoalWeight(targetDate, targetWeight);
         System.out.println(getDailyCaloriesForPlan(testGoal, currentWeight, 185, 21, true));
     }
}
