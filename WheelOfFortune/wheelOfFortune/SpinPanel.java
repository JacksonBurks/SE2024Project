package wheelOfFortune;

import java.awt.*;
import javax.swing.*;

public class SpinPanel extends JPanel {
    
    private JLabel errorLabel;
    private JLabel spinLabel;
    private JLabel currentScore;
    private JLabel pointsSpun;
    //buttons
    private JPanel buttonsPanel;
    private JButton spinButton;
    private JButton takeTurn;

    
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
    
    public void addTakeTurnButton() {
    	buttonsPanel.add(takeTurn);
    }
    
    // Constructor for the contacts panel.
    public SpinPanel(SpinControl sc) {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        
        // Create the contacts label in the north.
        spinLabel = new JLabel("Spin The Wheel!", JLabel.CENTER);
        spinLabel.setForeground(Color.GREEN);
        this.add(spinLabel, gbc);
                
        // Create the error label in the center.
        errorLabel = new JLabel("", JLabel.CENTER);
        errorLabel.setForeground(Color.RED);
        gbc.gridy++;
        this.add(errorLabel, gbc);
        
        // Add an image
        ImageIcon imageIcon = new ImageIcon("wheelOfFortune/Wheel_of_Fortune_logo.png");
        JLabel imageLabel = new JLabel(imageIcon);
        gbc.gridy++;
        this.add(imageLabel, gbc);
        
        gbc.gridy++;
        currentScore = new JLabel("", JLabel.CENTER);
        setCurrentScore(0);
        this.add(currentScore, gbc);
        
        gbc.gridy++;
        pointsSpun = new JLabel("", JLabel.CENTER);
        this.add(pointsSpun, gbc);
        
        // Create the ready label in the center.
        gbc.gridy++;
        this.add(spinLabel, gbc);

        // Create the buttons in the south.
        gbc.gridy++;
        buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        spinButton = new JButton("Spin");
        spinButton.addActionListener(sc);
        buttonsPanel.add(spinButton);
        JButton logoutButton = new JButton("Log Out");
        logoutButton.addActionListener(sc);
        buttonsPanel.add(logoutButton);
        takeTurn = new JButton("Take Turn");
        takeTurn.addActionListener(sc);
        this.add(buttonsPanel, gbc);
    }
}

