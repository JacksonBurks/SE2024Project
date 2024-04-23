package wheelOfFortune;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GameControl implements ActionListener{

	private JPanel container;
	private GameClient client;
	private GamePanel gamePanel;
	private String word;
	private String category;
	private Boolean testGuess = false;
	private boolean lastGuessCorrect = false;
	private Wheel wheel;
	//Spin
	private SpinData spin;
	private int spinNumber = 0;

	public void setWheel(Wheel wheel) {
		this.wheel = wheel;
	}

	public void setGamePanel(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	public GameControl(JPanel container, GameClient client) {
		this.client = client;
		this.container = container;  
		spin = new SpinData();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		
		if (command.equals("Spin"))
		{
			spinNumber++;
			wheel.spin();
			spin.setSpun(true);
			if(spinNumber == 1) {
				spin.setSpinType("First");
				try
				{
					//spinPanel.spin();
					client.sendToServer(spin);
				}
				catch (IOException e1)
				{
					displayError("Error connecting to the server.");
				}
			}else {
				spin.setSpinType("Round");
				try
				{
					//spinPanel.spin();
					client.sendToServer(spin);
				}
				catch (IOException e1)
				{
					displayError("Error connecting to the server.");
				}
			}
		}

		else if (command.equals("Guess")) {
			handleGuess();

		} else if (command.equals("Solve")) {
			handleSolve();
		}
		else if(command.equals("Update")) {
			handleUpdate();
		}
		else if (isVowelButton(command)) {
			handleVowelButton(command);
		}
		else if (command.equals("Log Out"))
		{
			LoginPanel loginPanel = (LoginPanel)container.getComponent(1);
			loginPanel.setError("");
			CardLayout cardLayout = (CardLayout)container.getLayout();
			cardLayout.show(container, "2");
		}
	}
	private void handleGuess() {
		if (gamePanel != null) {
			String guessText = gamePanel.getTextField().getText().trim().toUpperCase();

			// Validate that the input is a consonant (A-Z) and not already guessed
			if (isValidConsonant(guessText)) {
				char guessedChar = guessText.charAt(0);

				// Check if the guessed letter is in the word
				if (word.toUpperCase().contains(String.valueOf(guessedChar))) {
					// Call updateWordDisplay method in GuessPanel
					gamePanel.updateWordDisplay(guessedChar);
					gamePanel.revalidate();
					gamePanel.repaint();
					lastGuessCorrect = true; 
				} else {
					lastGuessCorrect = false; 
					// Inform the user that the guess is incorrect
					JOptionPane.showMessageDialog(container, "Sorry, incorrect guess!", "Incorrect Guess", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				lastGuessCorrect = false; // Mark the guess as incorrect (invalid input)
				// Inform the user that only consonants are allowed for guessing
				JOptionPane.showMessageDialog(container, "Please enter a consonant (A-Z)!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
			}

			gamePanel.getTextField().setText(""); // Clear text field

			// Send the guess result to the server
			sendGuessResultToServer(lastGuessCorrect);
		}
	}
	private void handleSolve() {
		if (gamePanel != null) {
			String guessText = gamePanel.getTextField().getText().trim();

			// Check if the guessed word matches the solution
			boolean isCorrect = !guessText.isEmpty() && word.equalsIgnoreCase(guessText);

			if (isCorrect) {
				// Display the full word
				gamePanel.revealWord();
			} else {
				// Inform the user that the guess is incorrect
				JOptionPane.showMessageDialog(container, "Sorry, wrong guess!", "Incorrect Guess", JOptionPane.ERROR_MESSAGE);
			}


			gamePanel.revalidate();
			gamePanel.repaint();


			sendSolveResultToServer(isCorrect);
		}
	}

	private void handleUpdate() {
		// Handle Update button action (if needed)
		if (gamePanel != null) {
			gamePanel.initWord();
			gamePanel.revalidate();
			gamePanel.repaint();
			category = getCategory();
			gamePanel.setCategoryText(category);
		}
	}
	private void handleVowelButton(String vowel) {
		if (gamePanel != null && word != null) {
			char guessedChar = vowel.charAt(0); // Get the character of the guessed vowel

			// Check if the guessed vowel is in the word
			boolean found = false;
			for (int i = 0; i < word.length(); i++) {
				if (Character.toUpperCase(word.charAt(i)) == Character.toUpperCase(guessedChar)) {
					// Update the display to reveal the vowel in the word
					gamePanel.updateWordDisplay(word.charAt(i));
					found = true;
				}
			}

			// Determine if the guess was correct or incorrect
			boolean isCorrect = found;

			// Display a message if the guessed vowel is not in the word
			if (!found) {
				// Inform the user that the guess is incorrect
				JOptionPane.showMessageDialog(container, "Sorry, wrong guess!", "Incorrect Guess", JOptionPane.ERROR_MESSAGE);
			}

			// Always refresh the panel display
			gamePanel.revalidate();
			gamePanel.repaint();


			sendGuessResultToServer(isCorrect);
		}
	}

	private boolean isVowelButton(String command) {
		return command.equals("A") || command.equals("E") || command.equals("I") || command.equals("O") || command.equals("U");
	}

	private boolean isValidConsonant(String input) {
		// Check if the input is a single alphabetic character and is a consonant
		return input.length() == 1 && input.matches("[A-Z&&[^AEIOU]]");
	}


	public void updateCategoryAndWord(String category,String word) {
		// Update the game panel with the received category and word
		gamePanel.setCategory(category);
		gamePanel.setWord(word);

	}
	public void updateRound(int gameRound) {
		
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	public void setWord(String word) {
		this.word = word;
	}
	private void sendGuessResultToServer(boolean isCorrect) {
		// Send the guess result to the server
		if (client != null) {
			try {
				client.sendToServer(new GameData(isCorrect));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	private void sendSolveResultToServer(boolean isCorrect) {
		// Send the solve result to the server
		if (client != null) {
			try {
				client.sendToServer(new SolveData(isCorrect));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void displayError(String error)
	{
		GamePanel gamePanel = (GamePanel)container.getComponent(4);
		gamePanel.setError(error);
	}
	public void specialResults(String result)
	{
		GamePanel gamePanel = (GamePanel)container.getComponent(4);
		gamePanel.setSpecialSpun(result);
	}
	
	public void pointResults(int result)
	{
		GamePanel gamePanel = (GamePanel)container.getComponent(4);
		gamePanel.setPointsSpun(result);
	}
	
	public void showWaitingLabel(String msg)
	{
		GamePanel gamePanel = (GamePanel)container.getComponent(4);
		gamePanel.setWaiting(msg);
	}

	public void removeSpinButton() {
		// TODO Auto-generated method stub
		GamePanel gamePanel = (GamePanel)container.getComponent(4);
		gamePanel.removeSpinButton();

	}
	
	public void removeSpinLabel() {
		GamePanel gamePanel = (GamePanel)container.getComponent(4);
		gamePanel.removeSpinLabel();
	}
	public void showGameButtons() {
		GamePanel gamePanel = (GamePanel)container.getComponent(4);
		gamePanel.showBuyVowelButton();
		gamePanel.showGuessButton();
		gamePanel.showSolveButton();
		gamePanel.disableSpinButton();
	}
	
	public String getCategory( ){
		return category;
	}
	
}












