package wheelOfFortune;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JPanel;

public class GameControl implements ActionListener{

	private JPanel container;
	private GameClient client;
	
	
    public GameControl(JPanel container, GameClient client) {
        this.client = client;
        this.container = container;       
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		
		
	}
}



       
   
