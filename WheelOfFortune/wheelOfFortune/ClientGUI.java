package wheelOfFortune;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ClientGUI extends JFrame
{
  
  // Constructor that creates the client GUI.
  public ClientGUI()
  {
    // Set up the chat client.
    GameClient client = new GameClient();
    client.setHost("localhost");
    client.setPort(8300);
    
    try
    {
      client.openConnection();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    
    // Set the title and default close operation.
    this.setTitle("Game Client");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    // Create the card layout container.
    CardLayout cardLayout = new CardLayout();
    JPanel container = new JPanel(cardLayout);
    
    
    //Create the Controllers next
    //Next, create the Controllers
    InitialControl ic = new InitialControl(container,client);
    LoginControl lc = new LoginControl(container,client);
    CreateAccountControl cac = new CreateAccountControl(container,client);
    NewGameControl ngc = new NewGameControl(container,client);
    GameControl gc = new GameControl(container, client);
    

    //Set the client info
    client.setLoginControl(lc);
    client.setCreateAccountControl(cac);
    client.setNewGameControl(ngc);
    client.setGameControl(gc);

    
    // Create the four views. (need the controller to register with the Panels
    JPanel view1 = new InitialPanel(ic);
    JPanel view2 = new LoginPanel(lc);
    JPanel view3 = new CreateAccountPanel(cac);
    JPanel view4 = new NewGamePanel(ngc); // Ready button screen
    JPanel view5 = new GamePanel(gc);


    // Add the views to the card layout container.
    container.add(view1, "1");
    container.add(view2, "2");
    container.add(view3, "3");
    container.add(view4, "4"); // ready button screen
    container.add(view5, "5"); // combined panel

    
    // Show the initial view in the card layout.
    cardLayout.show(container, "1");
    
    // Add the card layout container to the JFrame.
    // GridBagLayout makes the container stay centered in the window.
    this.setLayout(new GridBagLayout());
    this.add(container);

    // Show the JFrame.
    this.setSize(850, 400);
    this.setVisible(true);
  }

  // Main function that creates the client GUI when the program is started.
  public static void main(String[] args)
  {
    new ClientGUI();
  }
  
}