package wheelOfFortune;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class WofClientGUI extends JFrame {
    private JPanel container;
    private NewGamePanel newGamePanel;
    private GameFrame gameFrame;
    private Wrapper game;
    private NewGameControl newGameControl;

    public WofClientGUI(Wrapper game) {
        this.game = game;
        setTitle("Wheel of Fortune");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new CardLayout());
        setSize(800, 600);

        container = new JPanel(new CardLayout());
        newGameControl = new NewGameControl(container, this);
        newGamePanel = new NewGamePanel(newGameControl);
        container.add(newGamePanel, "3");

        add(container);
    }

    public void showNewGamePanel() {
        CardLayout cardLayout = (CardLayout) container.getLayout();
        cardLayout.show(container, "3");
    }

    
}