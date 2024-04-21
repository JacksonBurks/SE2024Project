package wheelOfFortune;

import java.awt.Color;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
	private Wheel wheel;
	private ArrayList<Player> players = new ArrayList<>();
	private static final int MIN_PLAYERS = 2;
	private static final int MAX_PLAYERS = 4;
	private int maxSpinValue = 0;
	private Player playerWithMaxSpin;
	private String category = "";
	private String word = "";
	private int firstSpins = 0;
	private int playersReady = 0;
	private int turnNumber = 0;

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
	public void setWheel(Wheel wheel) {
		this.wheel = wheel;
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		if (msg instanceof LoginData) {
			handleLogin((LoginData) msg, client);
		} else if (msg instanceof CreateAccountData) {
			handleCreateAccount((CreateAccountData) msg, client);
		} else if (msg instanceof NewGameData) {
			handleNewGame((NewGameData) msg, client);
		} else if (msg instanceof SpinData) {
			handleSpinData((SpinData) msg, client);
		}
		else if (msg instanceof GameData) {
			handleGameData((GameData)msg, client);
		}
		else if (msg instanceof SolveData) {
			handleSolveData((SolveData)msg, client);
		}
	}

	private void handleLogin(LoginData data, ConnectionToClient client) {
		try {
			if (db.verifyAccount(data.getUsername(), data.getPassword())) {
				client.sendToClient("LoginSuccessful");
				log.append("Player " + client.getId() + " successfully logged in as " + data.getUsername() + "\n");
				players.add(new Player((int) client.getId(), data.getUsername(), data.getPassword(), 0));
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
				players.add(new Player((int) client.getId(), data.getUsername(), data.getPassword(), 0));
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

	private void handleSpinData(SpinData data, ConnectionToClient client) {
		if (data.clickedSpin()) {
			wheel.setSelectedPoints(0);
			wheel.spin();
			// if the wheel spins null
			if (wheel.getSelectedPoints() == 0) {
				wheel.selectSlice(); // get another slice
				// if the spinData's type is "First"
				if (data.getSpinType().equals("First")) {
					// loop through the array list of players
					for (Player player : players) {
						// find the one with the same id as the client connected
						if (player.getId() == (int)client.getId()) {
							// set the player's first spin boolean to true
							player.setFirstSpin(true);
							break;
						}
					}
					// if the wheel spun a special slice
					if (wheel.isSpecialSelected()) {
						try { 
							//extract the special that  was selected, either lose turn or bankrupt
							String special = wheel.getSpecialSliceText();
							// update the player's points
							updatePlayerPointsSpun((int)client.getId(), 0);
							client.sendToClient(new SpinResult(special, "First"));
							for (Player player : players) {
								if (player.getId() == client.getId()) {
									log.append(player.getUsername()+ " landed on " + wheel.getSpecialSliceText() + ", 0 points!\n");;
									break;
								}
							}
							wheel.setSpecialSliceText(""); // Reset the special slice text to empty string
						} catch (IOException e) {
							e.printStackTrace();
						}
					} 
					else { // for points
						try {
							updatePlayerPointsSpun((int)client.getId(), wheel.getSelectedPoints());
							client.sendToClient(new SpinResult(String.valueOf(wheel.getSelectedPoints()), "First"));
							for (Player player : players) {
								if (player.getId() == client.getId()) {
									log.append(player.getUsername()+ " landed on " + wheel.getSelectedPoints() + " points!\n");
									break;
								}
							}
							//log.append("Player " + client.getId() + " landed on " + wheel.getSelectedPoints() + " points!\n");
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					for (Player player : players) {
						// find the one with the same id as the client connected
						if (player.getId() == (int) client.getId()) {
							if (player.didFirstSpin()) {
								firstSpins++;
							}
						}
					}

					// Find the player who spun the highest value
					maxSpinValue = 0;
					for (Player player : players) {
						if (player.didFirstSpin() && player.getPointsSpun() > maxSpinValue) {
							maxSpinValue = player.getPointsSpun();
							playerWithMaxSpin = player;
						}
					}
				}
				// Now playerWithMaxSpin holds the player object who spun the highest value
				if (playerWithMaxSpin != null && client.getId() == playerWithMaxSpin.getId()) {
					log.append("Player " + playerWithMaxSpin.getUsername() + " spun the highest value: " + maxSpinValue + " points!\n");

					try {
						client.sendToClient("TakeTurn");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else {
					log.append("Player " + playerWithMaxSpin.getUsername() + " spun the highest value: " + maxSpinValue + " points!\n");
					for (Player player : players) {
						if (player.getId() == client.getId() && player.getPointsSpun() <= playerWithMaxSpin.getPointsSpun()) {
							try {
								client.sendToClient("Wait");
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							break;
						}
					}
				}
			}

			else if (data.getSpinType().equals("Round")) {
				// if the wheel spun a special slice
				if (wheel.isSpecialSelected()) {
					try { 
						//extract the special that  was selected, either lose turn or bankrupt
						String special = wheel.getSpecialSliceText();
						// update the player's points
						updatePlayerPoints((int)client.getId(), 0, special);
						client.sendToClient(new SpinResult(special, "Round"));
						for (Player player : players) {
							if (player.getId() == client.getId()) {
								log.append(player.getUsername()+ " landed on " + wheel.getSpecialSliceText() + "!\n");;
								break;
							}
						}
						wheel.setSpecialSliceText(""); // Reset the special slice text to empty string
					} catch (IOException e) {
						e.printStackTrace();
					}
				} 
				else { // for points
					try {
						updatePlayerPoints((int)client.getId(), wheel.getSelectedPoints(), "None");
						client.sendToClient(new SpinResult(String.valueOf(wheel.getSelectedPoints()), "Round"));
						for (Player player : players) {
							if (player.getId() == client.getId()) {
								log.append(player.getUsername()+ " landed on " + wheel.getSelectedPoints() + " points!\n");
								break;
							}
						}
						//log.append("Player " + client.getId() + " landed on " + wheel.getSelectedPoints() + " points!\n");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}
		}
		pullCatandWord();
		WordData message = new WordData(category, word);
		sendToAllClients(message);
	}

	private void handleGameData(GameData data, ConnectionToClient client) {
		boolean isCorrect = data.isCorrect();
		if (isCorrect) {

			System.out.println("Correct guess received from client: " + client.getId());

		} else {

			System.out.println("Incorrect guess received from client: " + client.getId());

		}
	}
	private void handleSolveData(SolveData data, ConnectionToClient client) {
		boolean isCorrect = data.isCorrect();


		if (isCorrect) {

			System.out.println("Correct solve received from client: " + client.getId());

		} else {

			System.out.println("Incorrect solve received from client: " + client.getId());

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

	public void updatePlayerPoints(int playerId, int pointsToAdd, String special) {
		for (Player player : players) {
			if (player.getId() == playerId) {
				if (special.equals("Bankrupt")) {
					player.setScore(0);
				}
				else if(special.equals("Lose Turn")) {
					player.setScore(player.getScore());
				}
				else if(special.equals("None")){
					player.setScore(player.getScore() + pointsToAdd);
				}
				break;
			}
		}
	}

	public void updatePlayerPointsSpun(int playerId, int pointsToAdd) {
		for (Player player : players) {
			if (player.getId() == playerId) {
				player.setPointsSpun(pointsToAdd);
				break;
			}
		}
	}

	public void pullCatandWord() {
		// Get a random category and word
		category = db.getRandomCategory();
		word = db.getRandomWord(category);
	}


}
