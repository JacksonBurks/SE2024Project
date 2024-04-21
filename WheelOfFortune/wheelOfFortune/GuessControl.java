package wheelOfFortune;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


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
            // Check if guessPanel is properly initialized
            
                String guessText = guessPanel.getTextField().getText().trim();

                if (!guessText.isEmpty()) {
                    char firstLetter = guessText.charAt(0);                    
                    guessPanel.getTextField().setText(""); // Clear text field
                
            }
        } else if (command.equals("Solve")) {
        	if (guessPanel != null) {
                
                String guessText = guessPanel.getTextField().getText();
                if (!guessText.isEmpty()) {
                    
                    // Save the guessed word to a variable in GameClient or GuessControl
                	  
                	if(testGuess = word.equalsIgnoreCase(guessText)) {
                		System.out.print("Correct");
                		
                	}else {
                		System.out.print("Sorry wrong guess");
                	}
                    guessPanel.getTextField().setText(""); // Clear text field
                }
            }
    }
        else if(command.equals("Update")) {
        	if (guessPanel != null) {
        		guessPanel.initWord();
        		
        	}
        }
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





       
   