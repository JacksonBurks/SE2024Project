package wheelOfFortune;

import java.awt.*;
import javax.swing.*;

public class NewGamePanel extends JPanel
{
  // Constructor for the contacts panel.
  public NewGamePanel()
  {

    // Use BorderLayout to lay out the components in this panel.
    this.setLayout(new BorderLayout());

    // Create the contacts label in the north.
    JLabel label = new JLabel("New Game", JLabel.CENTER);
    this.add(label, BorderLayout.NORTH);    


    // Create the buttons in the south.
    JPanel buttonsPanel = new JPanel(new BorderLayout());
    JPanel newGameButtons = new JPanel();
    JButton deleteButton = new JButton("Start New Game");
    JButton addButton = new JButton("Join Game");
    newGameButtons.add(deleteButton);
    newGameButtons.add(addButton);
    buttonsPanel.add(newGameButtons, BorderLayout.NORTH);
    JButton logoutButton = new JButton("Log Out");
    JPanel logoutButtonBuffer = new JPanel();
    logoutButtonBuffer.add(logoutButton);
    buttonsPanel.add(logoutButtonBuffer, BorderLayout.SOUTH);
    this.add(buttonsPanel, BorderLayout.SOUTH);
  }
}
