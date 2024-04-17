package wheelOfFortune;

import java.util.Random;

public class PuzzleGenerator {

	private static final String[] CATEGORIES = {"Movie", "Book", "Country", "City", "Food"};
	private static final String[] WORDS = {"APPLE", "BANANA", "CHERRY", "ORANGE", "PEAR", "STRAWBERRY", "LEMON", "MANGO", "KIWI", "GRAPE"};

	public Puzzle generatePuzzle() {
		Random random = new Random();
		String category = CATEGORIES[random.nextInt(CATEGORIES.length)];
		String word = WORDS[random.nextInt(WORDS.length)];
		return new Puzzle(category, word);
	}
}
