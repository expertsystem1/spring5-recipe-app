package guru.springframework.model;

public enum Difficulty {
	
	EASY, MODERATE, KIND_OF_HARD, HARD;
	
	public String getValue(Difficulty difficulty) {
		switch(difficulty) {
		case EASY:
			return "easy";
		case MODERATE:
			return "moderate";
		case KIND_OF_HARD:
			return "kind of hard";	
		case HARD:
			return "hard";
	    default:
	    	return "easy";
		}
	}
	
}
