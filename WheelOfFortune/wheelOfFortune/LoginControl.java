package wheelOfFortune;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class LoginControl implements ActionListener
{
  // Private data fields for the container and chat client.
  private JPanel container;
  private GameClient client;
  private Player player;
  private String username;
  private String pw;
  private NewGameControl ngc;
  
  // Constructor for the login controller.
  public LoginControl(JPanel container, GameClient client)
  {
    this.container = container;
    this.client = client;
  }
  
  // Handle button clicks.
  public void actionPerformed(ActionEvent ae)
  {
    // Get the name of the button clicked.
    String command = ae.getActionCommand();

    // The Cancel button takes the user back to the initial panel.
    if (command == "Cancel")
    {
      CardLayout cardLayout = (CardLayout)container.getLayout();
      cardLayout.show(container, "1");
    }

    // The Submit button submits the login information to the server.
    else if (command == "Submit")
    {
      // Get the username and password the user entered.
      LoginPanel loginPanel = (LoginPanel)container.getComponent(1);
      LoginData data = new LoginData(loginPanel.getUsername(), loginPanel.getPassword());
      
      // Check the validity of the information locally first.
      if (data.getUsername().equals("") || data.getPassword().equals(""))
      {
        displayError("You must enter a username and password.");
        return;
      }
      
      username = data.getUsername();
      pw = data.getPassword();
      // Submit the login information to the server.
      try
      {
        client.sendToServer(data);
      }
      catch (IOException e)
      {
        displayError("Error connecting to the server.");
      }
    }
  }

  // After the login is successful, set the Player object and display the new Game screen.
  public void loginSuccess()
  {
	player = new Player(username, pw, 0);
	ngc = new NewGameControl(container, client);
	ngc.setPlayer(player);
    //LoginPanel loginPanel = (LoginPanel)container.getComponent(1);
    CardLayout cardLayout = (CardLayout)container.getLayout();
    cardLayout.show(container, "4");
  }

  // Method that displays a message in the error label.
  public void displayError(String error)
  {
    LoginPanel loginPanel = (LoginPanel)container.getComponent(1);
    loginPanel.setError(error);
  }
}
