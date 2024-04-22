package wheelOfFortune;

import java.io.Serializable;

public class Player implements Serializable 
{
	// Private data fields for the username and password.
	private int id;
	private String username;
	private String password;
	private int score;
	private int pointsSpun;
	private boolean hasSpun;
	private boolean firstSpin;
	
	public int getPointsSpun() {
		return pointsSpun;
	}
	public void setPointsSpun(int pointsSpun) {
		this.pointsSpun = pointsSpun;
	}
	
	public boolean didFirstSpin() {
		return firstSpin;
	}
	public void setFirstSpin(boolean firstSpin) {
		this.firstSpin = firstSpin;
	}
	public boolean hasSpun() {
		return hasSpun;
	}
	public void setHasSpun(boolean hasSpun) {
		this.hasSpun = hasSpun;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

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
	public Player(int id, String username, String password, int score)
	{
		setId(id);
		setUsername(username);
		setPassword(password);
		setScore(score);
	}
}