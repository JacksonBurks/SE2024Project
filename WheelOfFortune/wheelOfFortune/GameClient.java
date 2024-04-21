package wheelOfFortune;

import ocsf.client.AbstractClient;

public class GameClient extends AbstractClient
{
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
}
