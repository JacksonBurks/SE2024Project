package wheelOfFortune;

import ocsf.client.AbstractClient;

public class GameClient extends AbstractClient
{
<<<<<<< HEAD
  // Private data fields for storing the GUI controllers.
  private LoginControl loginControl;
  private CreateAccountControl createAccountControl;
  private NewGameControl newGameControl;
  private SpinControl spinControl;
  private GuessControl guessControl;
  

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
  
  public void setSpinControl(SpinControl spinControl)
  {
    this.spinControl = spinControl;
  }
  public void setGuessControl(GuessControl guessControl) {
	  this.guessControl = guessControl;
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
 
      // Other message handling logic...
  
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
    else if (arg0 instanceof WordData) {
        // Received category and word message from server
        WordData message = (WordData) arg0;
        
        // Extract category and word from the message
        String category = message.getCategory();
        String word = message.getWord();
        
        guessControl.updateCategoryAndWord(category, word); 
        guessControl.setWord(word);
    }
  }  
=======
	// Private data fields for storing the GUI controllers.
	private LoginControl loginControl;
	private CreateAccountControl createAccountControl;
	private NewGameControl newGameControl;
	private GameControl gameControl;
	private final int MAX_POINTS = 1000;
	private final int MIN_POINTS = 300;
	private int yourID;
	private int yourScore;
	private int oppScore;


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
			else if (message.equals("Highest Spun: " + String.valueOf(yourID))){
				gameControl.showGameButtons();
			}
			else if (message.equals("Go again")) {
				gameControl.showSpinButton();
				gameControl.showSpinLabel();
				//gameControl.removeUpdateButton();
			}
			else if (message.equals("Not your turn")) {
				gameControl.removeGameButtons();
				gameControl.removeSpinButton();
				gameControl.removeSpinLabel();

			}
			else if (message.equals("Turn switch " + String.valueOf(yourID))) {
				gameControl.showGameButtons();
				gameControl.showSpinButton();
				gameControl.showSpinLabel();
			}
			else if(message.equals("Next Round")){
				gameControl.setRound();
				gameControl.showSpinButton();
			}
			else if(message.equals("Game Over")) {
				gameControl.gameOver(yourScore);
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
					gameControl.specialResults(res.getResult());
				}
				else if(res.getResult().equals("Lose Turn")) {
					gameControl.specialResults(res.getResult());
				}
				else if (Integer.parseInt(res.getResult()) >= MIN_POINTS && Integer.parseInt(res.getResult()) <= MAX_POINTS) {
					gameControl.showGameButtons();
					gameControl.pointResults(Integer.parseInt(res.getResult()));
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
		else if (arg0 instanceof PointsData) {
			PointsData pd = (PointsData)arg0;
			yourScore = pd.getPoints();
			gameControl.updateScore(yourScore);
		}
	}  
>>>>>>> ardley
}
