package wheelOfFortune;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuessPanel extends JPanel {

    private JLabel[] letterLabels;
    private JButton buyVowelButton;
    private GuessControl guessControl;
    private JTextField textField;
    private JButton guessButton;
    private JButton buyVowelButton1;

    public GuessPanel(GuessControl guessControl) {
        this.guessControl = guessControl;

        setLayout(new BorderLayout());

        // Create a panel for the top blank section
        JPanel topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(400, 200)); // Adjust dimensions as needed
        topPanel.setBackground(Color.WHITE); // Set background color for the blank section

        // Create a panel for the bottom section with text field and buttons
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());

        // Create a text field above the buttons
        textField = new JTextField(20); // Adjust width of text field
        bottomPanel.add(textField); // Add text field to the bottom panel

        // Create the "Guess" button
        guessButton = new JButton("Guess");
        bottomPanel.add(guessButton); // Add Guess button to the bottom panel

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
        }

        // Add the vowel panel to the main panel (west)
        add(vowelPanel, BorderLayout.WEST);
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
}
