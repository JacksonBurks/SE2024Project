package wheelOfFortune;

import ocsf.client.AbstractClient;

public class GameClient extends AbstractClient
{
	// Private data fields for storing the GUI controllers.
	private LoginControl loginControl;
	private CreateAccountControl createAccountControl;
	private NewGameControl newGameControl;
	private FirstSpinControl firstSpinControl;
	private int turn;

	// Setters for the GUI controllers.
	public void setLoginControl(LoginControl loginControl)
	{
		this.loginControl = loginControl;
	}
	public void setCreateAccountControl(CreateAccountControl createAccountControl)
	{
		this.createAccountControl = createAccountControl;
	}

	public void setNewGameControl(NewGameControl newGameControl)
	{
		this.newGameControl = newGameControl;
	}

	public void setSpinControl(FirstSpinControl firstSpinControl)
	{
		this.firstSpinControl = firstSpinControl;
	}

	// Constructor for initializing the client with default settings.
	public GameClient()
	{
		super("localhost", 8300);
	}

	// Method that handles messages from the server.
	public void handleMessageFromServer(Object arg0)
	{
		// If we received a String, figure out what this event is.
		if (arg0 instanceof String)
		{
			// Get the text of the message.
			String message = (String)arg0;

			// If we successfully logged in, tell the login controller.
			if (message.equals("LoginSuccessful"))
			{
				loginControl.loginSuccess();
			}

			// If we successfully created an account, tell the create account controller.
			else if (message.equals("CreateAccountSuccessful"))
			{
				createAccountControl.createAccountSuccess();
			}

			else if (message.equals("PlayerReady"))
			{
				newGameControl.readySuccess();
			}
			else if (message.equals("Bankrupt") || message.equals("Lose Turn"))
			{
				turn++;
				if (turn == 1) {
					firstSpinControl.specialResults(message);
				}
				else {
					
				}
			}
			else {
				turn++;
				if (turn == 1) {
					firstSpinControl.pointResults(Integer.parseInt(message));
				}
			}
		}

		// If we received an Error, figure out where to display it.
		else if (arg0 instanceof Error)
		{
			// Get the Error object.
			Error error = (Error)arg0;

			// Display login errors using the login controller.
			if (error.getType().equals("Login"))
			{
				loginControl.displayError(error.getMessage());
			}

			// Display account creation errors using the create account controller.
			else if (error.getType().equals("CreateAccount"))
			{
				createAccountControl.displayError(error.getMessage());
			}

			else if (error.getType().equals("NotReady"))
			{
				newGameControl.colorReadyLabel();
				newGameControl.displayError(error.getMessage());
			}
		}
	}  
}
