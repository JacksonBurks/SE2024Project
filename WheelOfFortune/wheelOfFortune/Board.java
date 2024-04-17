package wheelOfFortune;

import java.util.*;

public class Board {
	private String puzzle;
	private Set<Character> guessedLetters;
	private ArrayList<Character> board;
	private ArrayList<Player> players;

	public Board(String puzzle) {
		this.puzzle = puzzle;
		this.guessedLetters = new HashSet<>();
		this.board = new ArrayList<>();
		this.players = players;
		initializeBoard();
	}

	private void initializeBoard() {
		for (int i = 0; i < puzzle.length(); i++) {
			char c = puzzle.charAt(i);
			if (c == ' ') {
				board.add(' ');
			} else {
				board.add('_');
			}
		}
	}

	public boolean guessLetter(char letter) {
		if (guessedLetters.contains(letter)) {
			return false;
		}

		guessedLetters.add(letter);
		boolean foundLetter = false;
		for (int i = 0; i < puzzle.length(); i++) {
			if (puzzle.charAt(i) == letter) {
				board.set(i, letter);
				foundLetter = true;
			}
		}
		return foundLetter;
	}

	public boolean isSolved() {
		return !board.contains('_');
	}

	public void printBoard() {
		for (char c : board) {
			System.out.print(c + " ");
		}
		System.out.println();
	}

	public Set<Character> getGuessedLetters() {
		return guessedLetters;
	}
}