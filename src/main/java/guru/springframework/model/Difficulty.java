package guru.springframework.model;

public enum Difficulty {
	
	LOW, MEDIUM, HIGH;
	
	public String getValue(Difficulty difficulty) {
		switch(difficulty) {
		case LOW:
			return "low";
		case MEDIUM:
			return "medium";
		case HIGH:
			return "high";
	    default:
	    	return "low";
		}
	}
	
}
