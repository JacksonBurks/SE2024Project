package wheelOfFortune;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class SpinControl implements ActionListener
{
	// Private data field for storing the container.
	private JPanel container;
	private GameClient client;
	private SpinPanel spinPanel;
	private Player player;
	private SpinData spin;
	private int numberOfSpins;
	
	public void setPlayer(Player player) {
		this.player = player;
	}

	public void setSpinPanel(SpinPanel spinPanel) {
		this.spinPanel = spinPanel;
	}

	// Constructor for the new game controller.
	public SpinControl(JPanel container, GameClient client)
	{
		this.container = container;
		this.client = client;
		spin = new SpinData();
	}

	// Handle button clicks.
	public void actionPerformed(ActionEvent ae)
	{
		// Get the name of the button clicked.
		String command = ae.getActionCommand();
		// The New Game button takes the user to the Spin panel.
		if (command.equals("Spin"))
		{
			spin.setSpun(true);
			try
			{
				//spinPanel.spin();
				client.sendToServer(spin);
			}
			catch (IOException e)
			{
				displayError("Error connecting to the server.");
			}
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
	public void displayError(String error)
	{
		SpinPanel spinPanel = (SpinPanel)container.getComponent(4);
		spinPanel.setError(error);
	}
	public void specialResults(String result)
	{
		System.out.println("Result: " + result + "\n");
	}
	
	public void pointResults(int result)
	{
		System.out.println("Result: " + result + "\n");
	}
}

