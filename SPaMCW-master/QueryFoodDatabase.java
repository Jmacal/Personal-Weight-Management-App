import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;



public class QueryFoodDatabase {

    public static ArrayList<FoodItem> foodItemArray = new ArrayList<FoodItem>();
    private static final int pageLimit = 5;
    private static int pageCounter = 0;
    private static JSONArray hints;
    private static JSONObject nextPageLink;
    private String appID;
	private String appKey;
    //Rounds a number to n decimal places
    public static double round(double d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Double.toString(d));
        bd = bd.setScale(decimalPlace, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
	
	public QueryFoodDatabase() {
		appID = "87136f4c";
        appKey = "2b284b262a3ed8a04352b8347cb32143";
	}
	
	private String findURL(String userInput) {
		StringBuilder sb = new StringBuilder();
        sb.append("https://api.edamam.com/api/food-database/v2/parser?ingr=");
		 for(int i = 0; i < userInput.length(); i++){
            char c = userInput.charAt(i);
            if(c == ' '){
                sb.append("%20");
            }else{
                sb.append(userInput.charAt(i));
            }
        }

        sb.append("&app_id=");
		sb.append(appID);
		sb.append("&app_key=");
		sb.append(appKey);
        String URL = sb.toString();
		return URL;
	}
	
	private static void openDB(String URL) {
		HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URL)).build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body) //gets the response body
                .thenApply(QueryFoodDatabase::parse) //sends responseBody to be parsed
                .join();
	}
	
	public void searchDB(String userInput) {
		foodItemArray.clear(); //clears array for new search
        pageCounter = 0;
		String URL = findURL(userInput);
        openDB(URL);
	}

    public static ArrayList<FoodItem> getFoodItemArray() {
        return foodItemArray;
    }

    public static String parse(String responseBody) {
        try {
            String APIResponse = "[" + responseBody + "]";
            JSONArray responseArray = new JSONArray(APIResponse);
            JSONObject wholePage = responseArray.getJSONObject(0); //response array contains one element - the page of results
            String text = wholePage.getString("text"); // what was searched
            JSONArray parsed = wholePage.getJSONArray("parsed"); //summary data from default choice
            hints = wholePage.getJSONArray("hints"); //food items matching search
            JSONObject links = wholePage.getJSONObject("_links"); //contains link to next page
            nextPageLink = links.getJSONObject("next"); //contains link to next page
        } catch (JSONException e){

        }

        //loop through hints - each element is a distinct food item
        System.out.println(hints.length());
        for (int j = 0; j < hints.length(); j++) {
            try{
                JSONObject hint = hints.getJSONObject(j); //gets specific item
                JSONObject food = hint.getJSONObject("food"); //the food information
                JSONArray measures = hint.getJSONArray("measures"); //measurements of the food
                String foodId = food.getString("foodId");
                String foodLabel = food.getString("label"); //name of food

                JSONObject nutrients = food.getJSONObject("nutrients");
                Double cals = round(nutrients.getDouble("ENERC_KCAL"), 2);
                Double protein = round(nutrients.getDouble("PROCNT"), 2);
                Double carbs = round(nutrients.getDouble("CHOCDF"), 2);
                Double fats = round(nutrients.getDouble("FAT"), 2);

                Double defaultServingGrams = 0.0;
                for (int k = 0; k < measures.length(); k++) {
                    JSONObject measure = measures.getJSONObject(k);
                    String uri = measure.getString("uri");
                    String measureLabel = measure.getString("label");
                    Double weight = measure.getDouble("weight");

                    if (measureLabel.equals("Whole")||measureLabel.equals("Serving")){
                        defaultServingGrams = weight; //the serving size for which the nutritional info is accurate for
                        break;
                    }
                }

                FoodItem newFoodItem = new FoodItem(foodId,foodLabel, cals, protein, carbs, fats, defaultServingGrams);
                foodItemArray.add(newFoodItem); //adds to array

                //how an element can be accessed
                //FoodItem temp = foodItemArray.get(0);
                //temp.getCalories();

            } catch (JSONException e) {
                //skips over this item in the database
                continue;
            }
        }

        //gets next page
        try {
            String title = nextPageLink.getString("title");

            if (pageCounter < pageLimit) {
                if (title.equals("Next page")) {
                    String href = nextPageLink.getString("href");
                    pageCounter++;
                    openDB(href);
                }
            }
            System.out.println("ALL ITEMS ADDED TO ARRAY");
            System.out.println(foodItemArray);
            System.out.println(foodItemArray.size());
        } catch (NullPointerException e) {
            new NoticeWindow("Invalid Food", "Please ensure you enter a valid food");
        }


        return null;
    }


}


