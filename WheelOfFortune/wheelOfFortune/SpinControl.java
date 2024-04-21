package wheelOfFortune;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class SpinControl implements ActionListener
{
	// Private data field for storing the container.
	private JPanel container;
	private GameClient client;
	private SpinPanel spinPanel;
	private int currentPlayerIndex = 0; // Track the index of the current player
	private boolean currentPlayerGuessed = false; // Track if the current player has guessed
	
	public void setSpinPanel(SpinPanel spinPanel) {
		this.spinPanel = spinPanel;
	}

	// Constructor for the new game controller.
	public SpinControl(JPanel container, GameClient client)
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
		if (command.equals("Spin"))
		{
			spinPanel.spin();
		}
		else if (command.equals("Guess")) {
		    CardLayout cardLayout = (CardLayout) container.getLayout();
		    cardLayout.show(container, "6"); // Show the GuessPanel
		    
		}

		// The logout button takes the user to the login panel.
		else if (command.equals("Log Out"))
		{
			LoginPanel loginPanel = (LoginPanel)container.getComponent(1);
			loginPanel.setError("");
			CardLayout cardLayout = (CardLayout)container.getLayout();
			cardLayout.show(container, "2");
		}

	}
}

