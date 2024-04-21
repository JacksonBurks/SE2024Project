package wheelOfFortune;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GuessControl implements ActionListener{

	private JPanel container;
	private GameClient client;
	private GuessPanel guessPanel;
	private String word;
	private String category;
	private Boolean testGuess = false;
	public void setGuessPanel(GuessPanel guessPanel) {
        this.guessPanel = guessPanel;
    }

    public GuessControl(JPanel container, GameClient client) {
        this.client = client;
        this.container = container;  
        
    }

	@Override
	 public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals("Guess")) {
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
	}
	private void handleGuess() {
        if (guessPanel != null) {
            String guessText = guessPanel.getTextField().getText().trim().toUpperCase();

            // Validate that the input is a consonant (A-Z) and not already guessed
            if (isValidConsonant(guessText)) {
                char guessedChar = guessText.charAt(0);

                // Check if the guessed letter is in the word
                if (word.toUpperCase().contains(String.valueOf(guessedChar))) {
                    // Call updateWordDisplay method in GuessPanel
                    guessPanel.updateWordDisplay(guessedChar);
                    guessPanel.revalidate();
                    guessPanel.repaint();
                } else {
                    // Inform the user that the guess is incorrect
                    JOptionPane.showMessageDialog(container, "Sorry, incorrect guess!", "Incorrect Guess", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // Inform the user that only consonants are allowed for guessing
                JOptionPane.showMessageDialog(container, "Please enter a consonant (A-Z)!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }

            guessPanel.getTextField().setText(""); // Clear text field
        }
    }
	 private void handleSolve() {
	        if (guessPanel != null) {
	            String guessText = guessPanel.getTextField().getText().trim();

	            // Check if the guessed word matches the solution
	            if (!guessText.isEmpty() && word.equalsIgnoreCase(guessText)) {
	                // Display the full word
	                guessPanel.revealWord();
	                guessPanel.revalidate();
	                guessPanel.repaint();
	            } else {
	                // Inform the user that the guess is incorrect
	                JOptionPane.showMessageDialog(container, "Sorry, wrong guess!", "Incorrect Guess", JOptionPane.ERROR_MESSAGE);
	            }
	        }
	    }
	  private void handleUpdate() {
	        // Handle Update button action (if needed)
	        if (guessPanel != null) {
	            guessPanel.initWord();
	            guessPanel.revalidate();
	            guessPanel.repaint();
	        }
	    }
	  private void handleVowelButton(String vowel) {
		    if (guessPanel != null && word != null) {
		        char guessedChar = vowel.charAt(0); // Get the character of the guessed vowel

		        // Check if the guessed vowel is in the word
		        boolean found = false;
		        for (int i = 0; i < word.length(); i++) {
		            if (Character.toUpperCase(word.charAt(i)) == Character.toUpperCase(guessedChar)) {
		                // Update the display to reveal the vowel in the word
		                guessPanel.updateWordDisplay(word.charAt(i));
		                found = true;
		            }
		        }

		        // Display a message if the guessed vowel is not in the word
		        if (!found) {
		            // Inform the user that the guess is incorrect
		            JOptionPane.showMessageDialog(container, "Sorry, wrong guess!", "Incorrect Guess", JOptionPane.ERROR_MESSAGE);
		        }

		        // Always refresh the panel display
		        guessPanel.revalidate();
		        guessPanel.repaint();
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
	        guessPanel.setCategory(category);
	        guessPanel.setWord(word);
	        
	    }
	   public void setCategory(String category) {
	    	this.category = category;
	    }
	    public void setWord(String word) {
	    	this.word = word;
	    }
}





       
   