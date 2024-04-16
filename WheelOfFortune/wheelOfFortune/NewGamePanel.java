package wheelOfFortune;

import java.awt.*;
import javax.swing.*;

public class NewGamePanel extends JPanel
{
	
	private JLabel errorLabel;
	private JLabel readyLabel;

	public void setError(String error)
	{
		errorLabel.setText(error);
	}
	
	public void setReadyLabel(String ready, Color color) {
		readyLabel.setText(ready);
		readyLabel.setForeground(color);
	}
	
	// Constructor for the contacts panel.
	public NewGamePanel(NewGameControl ngc)
	{
	    errorLabel = new JLabel("", JLabel.CENTER);
	    errorLabel.setForeground(Color.RED);
	    
	    readyLabel = new JLabel("Not ready", JLabel.CENTER);
	    readyLabel.setForeground(Color.red);

		// Use BorderLayout to lay out the components in this panel.
		this.setLayout(new BorderLayout());

		// Create the contacts label in the north.
		JLabel label = new JLabel("New Game", JLabel.CENTER);
		this.add(label, BorderLayout.NORTH);    


		// Create the buttons in the south.
		JPanel buttonsPanel = new JPanel(new BorderLayout());
		JPanel newGameButtons = new JPanel();
		JButton startButton = new JButton("Ready");
		startButton.addActionListener(ngc);
		newGameButtons.add(startButton);
		buttonsPanel.add(newGameButtons, BorderLayout.NORTH);
		JButton logoutButton = new JButton("Log Out");
		logoutButton.addActionListener(ngc);
		JPanel logoutButtonBuffer = new JPanel();
		logoutButtonBuffer.add(logoutButton);
		buttonsPanel.add(logoutButtonBuffer, BorderLayout.SOUTH);
		this.add(buttonsPanel, BorderLayout.SOUTH);
	}

}
