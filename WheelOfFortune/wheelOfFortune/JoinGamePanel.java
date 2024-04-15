package wheelOfFortune;

import java.awt.*;
import javax.swing.*;

public class JoinGamePanel extends JPanel
{

	private JLabel errorLabel;

	// Setter for the error text.
	public void setError(String error)
	{
		errorLabel.setText(error);
	}
	
	// Constructor for the contacts panel.
	public JoinGamePanel(JoinGameControl jgc)
	{

		// Use BorderLayout to lay out the components in this panel.
		this.setLayout(new BorderLayout());

		// Create the contacts label in the north.
		errorLabel = new JLabel("", JLabel.CENTER);
	    errorLabel.setForeground(Color.RED);
		JLabel label = new JLabel("Join Game", JLabel.CENTER);
		this.add(label, BorderLayout.NORTH);    


		// Create the buttons in the south.
		JPanel buttonsPanel = new JPanel(new BorderLayout());
		JPanel newGameButtons = new JPanel();
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(jgc);
		newGameButtons.add(submitButton);
		buttonsPanel.add(newGameButtons, BorderLayout.NORTH);
		JButton logoutButton = new JButton("Log Out");
		logoutButton.addActionListener(jgc);
		JPanel logoutButtonBuffer = new JPanel();
		logoutButtonBuffer.add(logoutButton);
		buttonsPanel.add(logoutButtonBuffer, BorderLayout.SOUTH);
		this.add(buttonsPanel, BorderLayout.SOUTH);
	}
}
