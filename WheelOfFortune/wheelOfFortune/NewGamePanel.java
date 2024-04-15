package wheelOfFortune;

import java.awt.*;
import javax.swing.*;

public class NewGamePanel extends JPanel
{
  // Constructor for the contacts panel.
  public NewGamePanel(NewGameControl ngc)
  {

    // Use BorderLayout to lay out the components in this panel.
    this.setLayout(new BorderLayout());

    // Create the contacts label in the north.
    JLabel label = new JLabel("New Game", JLabel.CENTER);
    this.add(label, BorderLayout.NORTH);    


    // Create the buttons in the south.
    JPanel buttonsPanel = new JPanel(new BorderLayout());
    JPanel newGameButtons = new JPanel();
    JButton startButton = new JButton("Start New Game");
    JButton joinButton = new JButton("Join Game");
    startButton.addActionListener(ngc);
    joinButton.addActionListener(ngc);
    newGameButtons.add(joinButton);
    newGameButtons.add(startButton);
    buttonsPanel.add(newGameButtons, BorderLayout.NORTH);
    JButton logoutButton = new JButton("Log Out");
    logoutButton.addActionListener(ngc);
    JPanel logoutButtonBuffer = new JPanel();
    logoutButtonBuffer.add(logoutButton);
    buttonsPanel.add(logoutButtonBuffer, BorderLayout.SOUTH);
    this.add(buttonsPanel, BorderLayout.SOUTH);
  }
}
