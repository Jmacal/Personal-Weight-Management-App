
public class ActivityCalculator {
	
	public double caloriesBurned(double weight, String activity, int minutesActive) {
		
		// Metabolic equivalent for task
		double MET = calculateMET(activity);
		
		// Calculates calories burned for the given activity
		return (minutesActive*(MET*3.5*weight)/200);
		
	}
	
	public double calculateMET(String activity) {
		
		// Returns the MET of the given activity
		switch(activity) {
		
			case "walking":
				return 3.5;
				
			case "running":
				return 8;
				
			case "cycling":
				return 8;
				
			case "swimming":
				return 10;
				
			case "calisthenics":
				return 6;
				
			case "weightlifting":
				return 6;
				
			case "sports":
				return 7;
				
			default:
				return 1;

		
		}
		
	}
	

}
