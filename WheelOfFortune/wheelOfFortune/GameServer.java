package wheelOfFortune;

import java.awt.*;
import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

public class GameServer extends AbstractServer
{
	// Data fields for this chat server.
	private JTextArea log;
	private JLabel status;
	private boolean running = false;
	private Database db;
	private Object newResult;
	private Object createResult;
	private Object readyResult;
	private final int MIN_PLAYERS = 2;
	private final int MAX_PLAYERS = 4;
	private int playersConnected = 0;

	// Constructor for initializing the server with default settings.
	public GameServer()
	{
		super(12345);
		this.setTimeout(500);
		db = new Database();
	}

	// Getter that returns whether the server is currently running.
	public boolean isRunning()
	{
		return running;
	}

	public void setDatabase(Database db) {
		this.db = db;
	}

	// Setters for the data fields corresponding to the GUI elements.
	public void setLog(JTextArea log)
	{
		this.log = log;
	}
	public void setStatus(JLabel status)
	{
		this.status = status;
	}

	// When the server starts, update the GUI.
	public void serverStarted()
	{
		running = true;
		status.setText("Listening");
		status.setForeground(Color.GREEN);
		log.append("Server started\n");
	}

	// When the server stops listening, update the GUI.
	public void serverStopped()
	{
		status.setText("Stopped");
		status.setForeground(Color.RED);
		log.append("Server stopped accepting new clients - press Listen to start accepting new clients\n");
	}

	// When the server closes completely, update the GUI.
	public void serverClosed()
	{
		running = false;
		status.setText("Close");
		status.setForeground(Color.RED);
		log.append("Server and all current clients are closed - press Listen to restart\n");
	}

	// When a client connects or disconnects, display a message in the log.
	public void clientConnected(ConnectionToClient client)
	{
		log.append("Player " + client.getId() + " connected\n");
		playersConnected++;
	}

	// When a message is received from a client, handle it.
	public void handleMessageFromClient(Object arg0, ConnectionToClient arg1)
	{
		// If we received LoginData, verify the account information.
		if (arg0 instanceof LoginData)
		{
			// Check the username and password with the database.
			LoginData data = (LoginData)arg0;
			//Object result;
			try {
				if (db.verifyAccount(data.getUsername(), data.getPassword()))
				{
					newResult = "LoginSuccessful";
					log.append("Player " + arg1.getId() + " successfully logged in as " + data.getUsername() + "\n");
				}
				else
				{
					newResult = new Error("The username and password are incorrect.", "Login");
					log.append("Player " + arg1.getId() + " failed to log in\n");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Send the result to the client.
			try
			{
				arg1.sendToClient(newResult);
			}
			catch (IOException e)
			{
				return;
			}
		}

		// If we received CreateAccountData, create a new account.
		else if (arg0 instanceof CreateAccountData)
		{
			// Try to create the account.
			CreateAccountData data = (CreateAccountData)arg0;
			try {
				if (!db.accountExists(data.getUsername(), data.getPassword()))
				{
					createResult = "CreateAccountSuccessful";
					log.append("Player " + arg1.getId() + " created a new account called " + data.getUsername() + "\n");
				}
				else
				{
					createResult = new Error("The username is already in use.", "CreateAccount");
					log.append("Player " + arg1.getId() + " failed to create a new account\n");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Send the result to the client.
			try
			{
				arg1.sendToClient(createResult);
			}
			catch (IOException e)
			{
				return;
			}
		}
		else if (arg0 instanceof NewGameData)
		{
			// Try to create the new Game.
			NewGameData data = (NewGameData)arg0;

			if (data.isReady())
			{
				if (playersConnected >= MIN_PLAYERS && playersConnected <= MAX_PLAYERS) {
					readyResult = "PlayerReady";
					log.append("Player " + arg1.getId() + " is ready\n");
				}
				else
				{
					readyResult = new Error("Need more players to connect.", "NotReady");
					log.append("Need more players to start the game\n");
				}
			}

			// Send the result to the client.
			try
			{
				arg1.sendToClient(readyResult);
			}
			catch (IOException e)
			{
				return;
			}
		}
	}

	// Method that handles listening exceptions by displaying exception information.
	public void listeningException(Throwable exception) 
	{
		running = false;
		status.setText("Exception occurred while listening");
		status.setForeground(Color.RED);
		log.append("Listening exception: " + exception.getMessage() + "\n");
		log.append("Press Listen to restart server\n");
	}
}
