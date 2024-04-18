package wheelOfFortune;

import java.awt.*;
import javax.swing.*;

public class NewGamePanel extends JPanel {
    
    private JLabel errorLabel;
    private JLabel readyLabel;

    public void setError(String error) {
        errorLabel.setText(error);
    }
    
    public void setReadyLabel(String ready, Color color) {
        readyLabel.setText(ready);
        readyLabel.setForeground(color);
    }
    
    // Constructor for the contacts panel.
    public NewGamePanel(NewGameControl ngc) {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        
        // Create the contacts label in the north.
        JLabel label = new JLabel("New Game", JLabel.CENTER);
        this.add(label, gbc);
                
        // Create the error label in the center.
        errorLabel = new JLabel("", JLabel.CENTER);
        errorLabel.setForeground(Color.RED);
        gbc.gridy++;
        this.add(errorLabel, gbc);
        
        // Add an image
        ImageIcon imageIcon = new ImageIcon("wheelOfFortune/Wheel_of_Fortune_logo.png"); // Modify path as needed
        JLabel imageLabel = new JLabel(imageIcon);
        gbc.gridy++;
        this.add(imageLabel, gbc);
        
        // Create the ready label in the center.
        readyLabel = new JLabel("Not ready", JLabel.CENTER);
        readyLabel.setForeground(Color.RED);
        gbc.gridy++;
        this.add(readyLabel, gbc);

        // Create the buttons in the south.
        gbc.gridy++;
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton startButton = new JButton("Ready");
        startButton.addActionListener(ngc);
        buttonsPanel.add(startButton);
        JButton logoutButton = new JButton("Log Out");
        logoutButton.addActionListener(ngc);
        buttonsPanel.add(logoutButton);
        this.add(buttonsPanel, gbc);
    }
}

