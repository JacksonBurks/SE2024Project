package wheelOfFortune;

public class Puzzle {
	private String category;
	private String word;

	public Puzzle(String category, String word) {
		this.category = category;
		this.word = word;
	}

	public String getCategory() {
		return category;
	}

	public String getWord() {
		return word;
	}

}
