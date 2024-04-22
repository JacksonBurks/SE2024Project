package wheelOfFortune;

import java.io.Serializable;

public class NewGameData implements Serializable 
{
	// Private data fields for the username and password.
	private boolean ready;
	private int id;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isReady() {
		return ready;
	}
	
	public void setReady(boolean ready) {
		this.ready = ready;
	}
	// Constructor that initializes the ready
	public NewGameData(boolean ready)
	{
		setReady(ready);
	}
}
