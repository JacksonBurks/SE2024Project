package wheelOfFortune;

import java.util.ArrayList;
import java.util.Scanner;

public class Wrapper {
	private Board board;
	private ArrayList<Player> players;
	private int currentPlayerIndex;
	private PuzzleGenerator puzzleGenerator;

	public Wrapper() {
		this.players = new ArrayList<>();
		this.currentPlayerIndex = 0;
		this.puzzleGenerator = new PuzzleGenerator();
	}

	public void startGame() {
		createPlayers();
		startRound();
	}

	private void createPlayers() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter the number of players: ");
		int numPlayers = scanner.nextInt();

		for (int i = 0; i < numPlayers; i++) {
			System.out.print("Enter the username of player " + (i + 1) + ": ");
			String username = scanner.next();
			System.out.print("Enter the password of player " + (i + 1) + ": ");
			String password = scanner.next();
			players.add(new Player(username, password, 0));
		}
	}

	private void startRound() {
		Puzzle puzzle = puzzleGenerator.generatePuzzle();
		board = new Board(puzzle.getWord());
		System.out.println("Category: " + puzzle.getCategory());

		while (!board.isSolved()) {
			Player currentPlayer = players.get(currentPlayerIndex);
			takeTurn(currentPlayer);
			currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
		}

		System.out.println("The puzzle was: " + puzzle.getWord());
		System.out.println("The winner is: " + getWinner().getUsername());
	}

	private void takeTurn(Player player) {
		System.out.println("Current player: " + player.getUsername());
		board.printBoard();

		char letter = getLetterGuess(player);
		if (board.guessLetter(letter)) {
			System.out.println("Correct!");
			player.setScore(player.getScore() + 100);
		} else {
			System.out.println("Incorrect.");
		}
	}

	private char getLetterGuess(Player player) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter a letter: ");
		char letter = scanner.next().toUpperCase().charAt(0);
		while (board.getGuessedLetters().contains(letter)) {
			System.out.print("You already guessed that letter. Enter a different letter: ");
			letter = scanner.next().toUpperCase().charAt(0);
		}
		return letter;
	}

	private Player getWinner() {
		int maxScore = 0;
		Player winner = null;

		for (Player player : players) {
			if (player.getScore() > maxScore) {
				maxScore = player.getScore();
				winner = player;
			}
		}

		return winner;
	}
}
