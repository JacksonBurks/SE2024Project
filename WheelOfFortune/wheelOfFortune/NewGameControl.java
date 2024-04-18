package wheelOfFortune;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class NewGameControl implements ActionListener
{
	// Private data field for storing the container.
	private JPanel container;
	private GameClient client;
	private NewGameData newGame;
	private Player player;
	
	public void setNewGameData(NewGameData newGame)
	{
		this.newGame = newGame;
	}
	// Constructor for the new game controller.
	public NewGameControl(JPanel container, GameClient client)
	{
		this.container = container;
		this.client = client;
		this.newGame = new NewGameData(false);
	}

	// Handle button clicks.
	public void actionPerformed(ActionEvent ae)
	{		
		// Get the name of the button clicked.
		String command = ae.getActionCommand();

		// The New Game button takes the user to the Spin panel.
		if (command.equals("Ready"))
		{
			newGame.setReady(true);
			try
			{
				client.sendToServer(newGame);
			}
			catch (IOException e)
			{
				displayError("Error connecting to the server.");
			}
		}

		// The logout button takes the user to the login panel.
		else if (command.equals("Log Out"))
		{
			CardLayout cardLayout = (CardLayout)container.getLayout();
			cardLayout.show(container, "2");
		}

	}


	public void colorReadyLabel() {
		NewGamePanel newGamePanel = (NewGamePanel)container.getComponent(3);
		newGamePanel.setReadyLabel("Ready", Color.GREEN);

	}

	public void readySuccess()
	{
		//ClientGUI clientGUI = (ClientGUI)SwingUtilities.getWindowAncestor(newGamePanel);
		//clientGUI.setUser(new User(createAccountPanel.getUsername(), createAccountPanel.getPassword()));
		CardLayout cardLayout = (CardLayout)container.getLayout();
		cardLayout.show(container, "5");
	}

	// Method that displays a message in the error label.
	public void displayError(String error)
	{
		NewGamePanel newGamePanel = (NewGamePanel)container.getComponent(3);
		newGamePanel.setError(error);
	}

}

