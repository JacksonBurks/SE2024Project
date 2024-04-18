package wheelOfFortune;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JPanel;

public class GuessControl implements ActionListener{

	private JPanel container;
	private GameClient client;
	private GuessPanel guessPanel;
	public void setGuessPanel(GuessPanel GuessPanel) {
		this.guessPanel = guessPanel;
	}

    public GuessControl(JPanel container, GameClient client) {
        this.client = client;
        this.container = container;       
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		
		
	}
}



       
   