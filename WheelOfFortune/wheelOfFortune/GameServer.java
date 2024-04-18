package wheelOfFortune;

import java.awt.Color;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class GameServer extends AbstractServer {
    private JTextArea log;
    private JLabel status;
    private boolean running = false;
    private Database db;
    private int playersConnected = 0;
    private ArrayList<Player> players = new ArrayList<>();
    private static final int MIN_PLAYERS = 2;
    private static final int MAX_PLAYERS = 4;
    private static final int MIN_POINTS = 300;
    private static final int MAX_POINTS = 1000;
    private static final int INCREMENT = 50;

    public GameServer() {
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

    @Override
    protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
        if (msg instanceof LoginData) {
            handleLogin((LoginData) msg, client);
        } else if (msg instanceof CreateAccountData) {
            handleCreateAccount((CreateAccountData) msg, client);
        } else if (msg instanceof NewGameData) {
            handleNewGame((NewGameData) msg, client);
        }
    }

    private void handleLogin(LoginData data, ConnectionToClient client) {
        try {
            if (db.verifyAccount(data.getUsername(), data.getPassword())) {
                client.sendToClient("LoginSuccessful");
                log.append("Player " + client.getId() + " successfully logged in as " + data.getUsername() + "\n");
                players.add(new Player(data.getUsername(), data.getPassword(), 0));
            } else {
                client.sendToClient(new Error("The username and password are incorrect.", "Login"));
                log.append("Player " + client.getId() + " failed to log in\n");
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private void handleCreateAccount(CreateAccountData data, ConnectionToClient client) {
        try {
            if (!db.accountExists(data.getUsername(), data.getPassword())) {
                client.sendToClient("CreateAccountSuccessful");
                log.append("Player " + client.getId() + " created a new account called " + data.getUsername() + "\n");
                players.add(new Player(data.getUsername(), data.getPassword(), 0));
            } else {
                client.sendToClient(new Error("The username is already in use.", "CreateAccount"));
                log.append("Player " + client.getId() + " failed to create a new account\n");
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private void handleNewGame(NewGameData data, ConnectionToClient client) {
        if (data.isReady()) {
            if (playersConnected >= MIN_PLAYERS && playersConnected <= MAX_PLAYERS) {
                try {
                    client.sendToClient("PlayerReady");
                    log.append("Player " + client.getId() + " is ready\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    client.sendToClient(new Error("Need more players to connect.", "NotReady"));
                    log.append("Need more players to start the game\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected synchronized void clientConnected(ConnectionToClient client) {
        playersConnected++;
        log.append("Player " + client.getId() + " connected\n");
    }

    @Override
    protected synchronized void clientDisconnected(ConnectionToClient client) {
        playersConnected--;
        log.append("Player " + client.getId() + " disconnected\n");
    }

    public void serverStarted() {
        running = true;
        status.setText("Listening");
        status.setForeground(Color.GREEN);
        log.append("Server started\n");
    }

    public void serverStopped() {
        status.setText("Stopped");
        status.setForeground(Color.RED);
        log.append("Server stopped accepting new clients - press Listen to start accepting new clients\n");
    }

    public void serverClosed() {
        running = false;
        status.setText("Close");
        status.setForeground(Color.RED);
        log.append("Server and all current clients are closed - press Listen to restart\n");
    }

    public void listeningException(Throwable exception) {
        running = false;
        status.setText("Exception occurred while listening");
        status.setForeground(Color.RED);
        log.append("Listening exception: " + exception.getMessage() + "\n");
        log.append("Press Listen to restart server\n");
    }

    public int pointsPossible() {
        Random random = new Random();
        return MIN_POINTS + random.nextInt((MAX_POINTS - MIN_POINTS) / INCREMENT) * INCREMENT;
    }

    public String specialSpun() {
        Random random = new Random();
        return random.nextInt(2) == 0 ? "Lose Turn" : "Bankrupt";
    }

    public void callMethodBasedOnProbability() {
        Random random = new Random();
        int randomNumber = random.nextInt(100);
        if (randomNumber < 75) {
            int points = pointsPossible();
            System.out.println("Points possible: " + points);
        } else if (randomNumber < 90) {
            String special = specialSpun();
            System.out.println("Special spun: " + special);
        }
    }
}
