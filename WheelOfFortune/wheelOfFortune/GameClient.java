package wheelOfFortune;

import ocsf.client.AbstractClient;

public class GameClient extends AbstractClient
{
	// Private data fields for storing the GUI controllers.
	private LoginControl loginControl;
	private CreateAccountControl createAccountControl;
	private NewGameControl newGameControl;
	private GameControl gameControl;
	private final int MAX_POINTS = 1000;
	private final int MIN_POINTS = 300;
	private int yourID;
	private int gameRound = 1;

	
	//private BoardControl boardControl;
	//private int firstTurn = 1;

	public void setGameControl(GameControl gameControl) {
		this.gameControl = gameControl;
	}
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

	public void setGuessControl(GameControl gameControl) {
		this.gameControl = gameControl;
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
			else if(message.equals("Next Round")){
				gameRound += 1;
				
					gameControl.updateRound(gameRound);
				
				
			}
			else if (message.equals("Highest Spun: " + String.valueOf(yourID))){
				gameControl.showGameButtons();
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
		else if(arg0 instanceof NewGameData) {
			NewGameData id = (NewGameData)arg0;
			yourID = id.getId();
	
		}  
		else if (arg0 instanceof SpinResult) {
			SpinResult res = (SpinResult)arg0;
			gameControl.removeSpinButton();
			gameControl.removeSpinLabel();
			if (res.getType().equals("First")) {
				// first spin specials equal 0 points
				if(res.getResult().equals("Bankrupt") || res.getResult().equals("Lose Turn")) {
					gameControl.specialResults(res.getResult());
				}
				else if (Integer.parseInt(res.getResult()) >= MIN_POINTS && Integer.parseInt(res.getResult()) <= MAX_POINTS) {
					gameControl.pointResults(Integer.parseInt(res.getResult()));
				}
			}
			else if (res.getType().equals("Round")) {
				if(res.getResult().equals("Bankrupt")) {

				}
				else if(res.getResult().equals("Lose Turn")) {

				}
				else if (Integer.parseInt(res.getResult()) >= MIN_POINTS && Integer.parseInt(res.getResult()) <= MAX_POINTS) {

				}
			}
		}
		 else if (arg0 instanceof WordData) {
		        // Received category and word message from server
		        WordData message = (WordData) arg0;
		        
		        // Extract category and word from the message
		        String category = message.getCategory();
		        String word = message.getWord();
		        
		        gameControl.updateCategoryAndWord(category, word); 
		        gameControl.setCategory(category);
		        gameControl.setWord(word);
		    }
	}  
}
