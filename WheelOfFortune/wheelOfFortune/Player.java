package wheelOfFortune;

import java.io.Serializable;

public class Player implements Serializable 
{
	// Private data fields for the username and password.
	private String username;
	private String password;
	private int score;

	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	// Getters for the username and password.
	public String getUsername()
	{
		return username;
	}
	public String getPassword()
	{
		return password;
	}

	// Setters for the username and password.
	public void setUsername(String username)
	{
		this.username = username;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
	

	// Constructor that initializes the username and password, and score
	public Player(String username, String password, int score)
	{
		setUsername(username);
		setPassword(password);
		setScore(score);
	}
}