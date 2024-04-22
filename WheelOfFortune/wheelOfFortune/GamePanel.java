package wheelOfFortune;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private JLabel[] letterLabels;
    private GameClient client;
    private GameControl gameControl;
    private JTextField textField;
    private JButton guessButton;
    private JButton vowelButton;
    private JButton update;
    private JButton solveButton; // New button for guessing the entire word
    private String word;
    private String category;
    private JLabel errorLabel;
    private JLabel spinLabel;
    private JLabel currentScore;
    private JLabel pointsSpun;
    private JLabel specialSpun;
    private JLabel waiting;
    private JButton spinButton;
    private Wheel wheel;
    private JPanel vowelPanel;

    public void removeSpinLabel() {
    	spinLabel.setVisible(false);
    }
    public void addSpinLabel() {
    	spinLabel.setVisible(true);
    }
    
	public void setWaiting(String msg) {
		waiting.setText(msg);
	}
    
	public void setSpecialSpun(String special) {
		specialSpun.setText(special);
	}

    public void setPointsSpun(int points) {
    	pointsSpun.setText("Points Spun: " + points);
    }
    public void setCurrentScore(int points) {
        currentScore.setText("Current Points: " + points);
    }

    public void setError(String error) {
        errorLabel.setText(error);
    }
    
    public void removeSpinButton() {
    	spinButton.setVisible(false);
    }
    
    public JTextField getTextField() {
        return textField;
    }

    public void setGuessButton() {
        guessButton.setVisible(true);;
    }
    
    public void setSolveButton() {
        guessButton.setVisible(true);;
    }

    public void setBuyVowelButton() {
        solveButton.setVisible(true);
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
    

   
  
    public GamePanel(GameControl gameControl) {
        
        this.setCategory(category);
    	
        setLayout(new BorderLayout());
        
        spinLabel = new JLabel("Spin The Wheel!", JLabel.CENTER);
        spinLabel.setForeground(Color.BLUE);
        JPanel topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(600, 400));
        topPanel.add(spinLabel);
        
        
        ImageIcon imageIcon = new ImageIcon("wheelOfFortune/Wheel_of_Fortune_logo.png");
        JLabel imageLabel = new JLabel(imageIcon);
        topPanel.add(imageLabel);
        
        
        currentScore = new JLabel("", JLabel.CENTER);
        setCurrentScore(0);
        topPanel.add(currentScore);
        
        pointsSpun = new JLabel("", JLabel.CENTER);
        topPanel.add(pointsSpun);
        
        
        specialSpun = new JLabel("", JLabel.CENTER);
        topPanel.add(specialSpun);
        
        waiting = new JLabel("", JLabel.CENTER);
        waiting.setForeground(Color.BLUE);
        topPanel.add(waiting);
        
        //topPanel.setBackground(Color.WHITE);
        
        
        // Initialize letterLabels array to hold labels for each letter in the word
        letterLabels = new JLabel[0]; 

        // Add the topPanel to the main panel (center)
        add(topPanel, BorderLayout.CENTER);

        // Create a panel for the bottom section with text field and buttons
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        
        spinButton = new JButton("Spin");
        spinButton.addActionListener(gameControl);
        
        JButton logoutButton = new JButton("Log Out");
        logoutButton.addActionListener(gameControl);
        
        bottomPanel.add(spinButton);
        bottomPanel.add(logoutButton);
 
        // Create a text field above the buttons
        textField = new JTextField(20);
        bottomPanel.add(textField); 

        // Create the "Guess" button
        guessButton = new JButton("Guess");
        guessButton.setVisible(false);
        bottomPanel.add(guessButton); 
        
        // Create the "Guess Word" button
        solveButton = new JButton("Solve");
        solveButton.setVisible(false);
        bottomPanel.add(solveButton); 
        update = new JButton("Update");
        bottomPanel.add(update);
        

        // Add action listeners to the buttons
        guessButton.addActionListener(gameControl);
        solveButton.addActionListener(gameControl);
        update.addActionListener(gameControl);

        // Add the bottom panel to the main panel (south)
        add(bottomPanel, BorderLayout.SOUTH);

     
        vowelPanel = new JPanel();
        vowelPanel.setLayout(new GridLayout(6, 1)); // Layout for vowel buttons (6 rows, 1 column)

        // Add a label "Buy Vowel" above the vowel buttons
        JLabel buyVowelLabel = new JLabel("Buy Vowel");
        vowelPanel.add(buyVowelLabel); // Add Buy Vowel label to the vowel panel

        
        JPanel categoryPanel = new JPanel(new BorderLayout());
        categoryPanel.setPreferredSize(new Dimension(200, 200));
        //categoryPanel.setBackground(Color.WHITE);
        JLabel categoryTextLabel = new JLabel("Category:" + category);
        categoryPanel.add(categoryTextLabel, BorderLayout.NORTH);
       

        JLabel categoryLabel = new JLabel(category);
        categoryLabel.setFont(new Font("Arial", Font.BOLD, 16));
        categoryPanel.add(categoryLabel, BorderLayout.CENTER);
        wheel = new Wheel();
        gameControl.setWheel(wheel);
        categoryPanel.add(wheel);

        // Add the category panel to the main panel on the east (right side)
        add(categoryPanel, BorderLayout.EAST);

        // Add the vowel panel to the main panel (west)
        add(vowelPanel, BorderLayout.WEST);
        gameControl.setGamePanel(this);
       
        this.setWord(word);
        
    }

 
    public void initWord() {
        if (word == null) {
            return;
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
            return; 
        }

        for (int i = 0; i < word.length(); i++) {
            char letter = word.charAt(i);
            if (Character.toUpperCase(letter) == Character.toUpperCase(guessedChar)) {
                
                letterLabels[i].setText(String.valueOf(letter));
            }
        }
    }
    
    public void revealWord() {
        JPanel topPanel = (JPanel) this.getComponent(0); 
        topPanel.removeAll(); 

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
    
    public void addVowelButtons() {
        // Create buttons for vowels and add them to the vowel panel
        String[] vowels = {"A", "E", "I", "O", "U"};
        for (String vowel : vowels) {
            vowelButton = new JButton(vowel);
            vowelPanel.add(vowelButton);
            vowelButton.addActionListener(gameControl);
        }
    }
}