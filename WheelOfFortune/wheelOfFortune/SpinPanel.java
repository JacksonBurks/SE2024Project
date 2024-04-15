package wheelOfFortune;

import java.awt.*;
import javax.swing.*;

public class SpinPanel extends JPanel
{
  // Constructor for the contacts panel.
  public SpinPanel(SpinControl sc)
  {

    // Use BorderLayout to lay out the components in this panel.
    this.setLayout(new BorderLayout());

    // Create the contacts label in the north.
    JLabel label = new JLabel("Spin The Wheel", JLabel.CENTER);
    this.add(label, BorderLayout.NORTH);    


    // Create the buttons in the south.
    JPanel buttonsPanel = new JPanel(new BorderLayout());
    JPanel newGameButtons = new JPanel();
    JButton spinButton = new JButton("Spin");
    spinButton.addActionListener(sc);
    newGameButtons.add(spinButton);
    buttonsPanel.add(newGameButtons, BorderLayout.NORTH);
    JButton logoutButton = new JButton("Log Out");
    logoutButton.addActionListener(sc);
    JPanel logoutButtonBuffer = new JPanel();
    logoutButtonBuffer.add(logoutButton);
    buttonsPanel.add(logoutButtonBuffer, BorderLayout.SOUTH);
    this.add(buttonsPanel, BorderLayout.SOUTH);
  }
}
