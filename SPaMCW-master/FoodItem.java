public class FoodItem {

    private String FoodID;
    private String FoodName;
    private Double Calories;
    private Double Protein;//grams
    private Double Carbs;//grams
    private Double Fats;//grams
    private Double ServingSize; //grams

    public FoodItem(String foodId, String foodname, Double cals, Double pro, Double carbs, Double fats, Double serving){
        this.FoodID = foodId;
        this.FoodName = foodname;
        this.Calories = cals;
        this.Protein = pro;
        this.Carbs = carbs;
        this.Fats = fats;
        this.ServingSize = serving;

    }

    public String getFoodID(){
        return FoodID;
    }

    public String getFoodName() {
        return FoodName;
    }

    public Double getCalories() {
        return Calories;
    }

    public Double getProtein() {
        return Protein;
    }

    public Double getCarbs() {
        return Carbs;
    }

    public Double getFats() {
        return Fats;
    }

    public Double getServingSize() {
        return ServingSize;
    }

    private void setCalories(Double calories) {
        Calories = calories;
    }

    private void setProtein(Double protein) {
        Protein = protein;
    }

    private void setCarbs(Double carbs) {
        Carbs = carbs;
    }

    private void setFats(Double fats) {
        Fats = fats;
    }

    public void calculateNutrients(Double measure, String units){
        //calculate values for one gram
        Double cals, pro, carbs, fats;
        cals = this.getCalories() / this.getServingSize();
        pro = this.getProtein() / this.getServingSize();
        carbs = this.getCarbs() / this.getServingSize();
        fats = this.getFats() / this.getServingSize();

        if (units.equalsIgnoreCase("grams")){
            this.setCalories(cals * measure);
            this.setProtein(pro * measure);
            this.setCarbs(carbs * measure);
            this.setFats(fats * measure);
        }
        // else if for other units - check with team what units we will support
        //convert the measure from those units to grams
        // then do calc of xyz * newMeasure
    }

}
