package wheelOfFortune;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class NewGameControl implements ActionListener
{
	// Private data field for storing the container.
	private JPanel container;
	private GameClient client;
	// Constructor for the new game controller.
	public NewGameControl(JPanel container, GameClient client)
	{
		this.container = container;
		this.client = client;
	}

	// Handle button clicks.
	public void actionPerformed(ActionEvent ae)
	{
		// Get the name of the button clicked.
		String command = ae.getActionCommand();

		// The New Game button takes the user to the Spin panel.
		if (command.equals("Start New Game"))
		{
			CardLayout cardLayout = (CardLayout)container.getLayout();
			cardLayout.show(container, "5");

		}

		// The join button takes the user to the Join Game panel.
		else if (command.equals("Join Game"))
		{
			//JoinGamePanel joinGamePanel = (JoinGamePanel)container.getComponent(5);
			//joinGamePanel.setError("");
			CardLayout cardLayout = (CardLayout)container.getLayout();
			cardLayout.show(container, "6");
		}

		// The logout button takes the user to the login panel.
		else if (command.equals("Log Out"))
		{
			CardLayout cardLayout = (CardLayout)container.getLayout();
			cardLayout.show(container, "2");
		}

	}
}

