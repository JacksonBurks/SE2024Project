package wheelOfFortune;

import javax.swing.*;
import java.awt.*;

public class GuessPanel extends JPanel {

    private JLabel[] letterLabels;
    private GameClient client;
    private GuessControl guessControl;
    private JTextField textField;
    private JButton guessButton;
    private JButton buyVowelButton;
    private JButton update;
    private JButton solveButton; // New button for guessing the entire word
    private String word;
    private String category;
   
  
    public GuessPanel(GuessControl guessControl) {
        
        this.setCategory(category);
    	
        setLayout(new BorderLayout());
        
        
        JPanel topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(400, 200));
        topPanel.setBackground(Color.WHITE);
        
        
        // Initialize letterLabels array to hold labels for each letter in the word
        letterLabels = new JLabel[0]; // Start with an empty array

        // Add the topPanel to the main panel (center)
        add(topPanel, BorderLayout.CENTER);

        // Create a panel for the bottom section with text field and buttons
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());

        // Create a text field above the buttons
        textField = new JTextField(20); // Adjust width of text field
        bottomPanel.add(textField); // Add text field to the bottom panel

        // Create the "Guess" button
        guessButton = new JButton("Guess");
        bottomPanel.add(guessButton); // Add Guess button to the bottom panel
        
        // Create the "Guess Word" button
        solveButton = new JButton("Solve");
        bottomPanel.add(solveButton); // Add Guess Word button to the bottom panel
        update = new JButton("Update");
        bottomPanel.add(update);
        

        // Add action listeners to the buttons
        guessButton.addActionListener(guessControl);
        solveButton.addActionListener(guessControl);
        update.addActionListener(guessControl);

        // Add the bottom panel to the main panel (south)
        add(bottomPanel, BorderLayout.SOUTH);

        // Create a panel for vowel buttons on the left side
        JPanel vowelPanel = new JPanel();
        vowelPanel.setLayout(new GridLayout(6, 1)); // Layout for vowel buttons (6 rows, 1 column)

        // Add a label "Buy Vowel" above the vowel buttons
        JLabel buyVowelLabel = new JLabel("Buy Vowel");
        vowelPanel.add(buyVowelLabel); // Add Buy Vowel label to the vowel panel

        // Create buttons for vowels and add them to the vowel panel
        String[] vowels = {"A", "E", "I", "O", "U"};
        for (String vowel : vowels) {
            JButton vowelButton = new JButton(vowel);
            vowelPanel.add(vowelButton);
            vowelButton.addActionListener(guessControl);
        }
        
        JPanel categoryPanel = new JPanel(new BorderLayout());
        categoryPanel.setPreferredSize(new Dimension(200, 200));
        categoryPanel.setBackground(Color.WHITE);
        JLabel categoryTextLabel = new JLabel("Category:" + category);
        categoryPanel.add(categoryTextLabel, BorderLayout.NORTH);

        JLabel categoryLabel = new JLabel(category);
        categoryLabel.setFont(new Font("Arial", Font.BOLD, 16));
        categoryPanel.add(categoryLabel, BorderLayout.CENTER);

        // Add the category panel to the main panel on the east (right side)
        add(categoryPanel, BorderLayout.EAST);

        // Add the vowel panel to the main panel (west)
        add(vowelPanel, BorderLayout.WEST);
        guessControl.setGuessPanel(this);
       
        this.setWord(word);
        
    }

 
    public void initWord() {
        if (word == null) {
            return; // Handle null word gracefully
        }

        letterLabels = new JLabel[word.length()];
        JPanel topPanel = (JPanel) this.getComponent(0); // Get the topPanel
        topPanel.removeAll(); // Clear existing labels

        for (int i = 0; i < word.length(); i++) {
            char letter = word.charAt(i);
            String labelText = (letter == ' ') ? " " : "_"; // Display spaces as empty initially
            JLabel label = new JLabel(labelText, SwingConstants.CENTER);
            label.setFont(new Font("Arial", Font.BOLD, 24)); // Set font and size
            letterLabels[i] = label;
            topPanel.add(label);
        }

        // Refresh the panel to reflect the new labels
        topPanel.revalidate();
        topPanel.repaint();
    }
    public void updateWordDisplay(char guessedChar) {
        if (letterLabels == null || letterLabels.length == 0) {
            return; // Handle uninitialized or empty letterLabels array
        }

        for (int i = 0; i < word.length(); i++) {
            char letter = word.charAt(i);
            if (Character.toUpperCase(letter) == Character.toUpperCase(guessedChar)) {
                // Update the corresponding label to display the guessed letter
                letterLabels[i].setText(String.valueOf(letter));
            }
        }
    }
    
    public void revealWord() {
        JPanel topPanel = (JPanel) this.getComponent(0); // Get the topPanel
        topPanel.removeAll(); // Clear existing labels

        for (int i = 0; i < word.length(); i++) {
            char letter = word.charAt(i);
            JLabel label = new JLabel(String.valueOf(letter), SwingConstants.CENTER);
            label.setFont(new Font("Arial", Font.BOLD, 24)); // Set font and size
            topPanel.add(label);
        }

        // Refresh the panel to reflect the revealed word
        topPanel.revalidate();
        topPanel.repaint();
    }




    // Optional: Add getter methods for the text field and buttons if needed
    public JTextField getTextField() {
        return textField;
    }

    public JButton getGuessButton() {
        return guessButton;
    }

    public JButton getBuyVowelButton() {
        return buyVowelButton;
    }

    public JButton getSolveButton() {
        return solveButton;
    }
    public void setCategory(String category) {
    	this.category = category;
    }
    public void setWord(String word) {
    	this.word = word;
    }
    public String getWord() {
    	return word;
    }
    public String getCategory() {
    	return category;
    }
}
