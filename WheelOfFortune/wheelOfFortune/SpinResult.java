package wheelOfFortune;

import java.io.Serializable;

public class SpinResult implements Serializable
{
	// Data field for storing the error message and type.
	private String result;
	private String type;

	// Getters for the error message and type.
	public String getResult()
	{
		return result;
	}
	public String getType()
	{
		return type;
	}

	// Setters for the error message and type.
	public void setResult(String result)
	{
		this.result = result;
	}
	public void setType(String type)
	{
		this.type = type;
	}

	// Constructor for creating a new Error object with a message and type.
	public SpinResult(String result, String type)
	{
		setResult(result);
		setType(type);
	}
}

