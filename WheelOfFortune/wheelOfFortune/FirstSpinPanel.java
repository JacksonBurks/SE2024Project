package wheelOfFortune;

import java.awt.*;
import javax.swing.*;

public class FirstSpinPanel extends JPanel {
    
    private JLabel errorLabel;
    private JLabel spinLabel;

    public void setError(String error) {
        errorLabel.setText(error);
    }
    
    
    // Constructor for the contacts panel.
    public FirstSpinPanel(FirstSpinControl sc) {
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
        
        // Create the ready label in the center.
        gbc.gridy++;
        this.add(spinLabel, gbc);

        // Create the buttons in the south.
        gbc.gridy++;
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton startButton = new JButton("Spin");
        startButton.addActionListener(sc);
        buttonsPanel.add(startButton);
        JButton logoutButton = new JButton("Log Out");
        logoutButton.addActionListener(sc);
        buttonsPanel.add(logoutButton);
        this.add(buttonsPanel, gbc);
    }
}

