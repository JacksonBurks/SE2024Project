package wheelOfFortune;

import java.awt.*;
import javax.swing.*;

import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class ServerGUI extends JFrame
{
	// Data fields.
	private JLabel status;
	private String[] labels = {"Port #", "Timeout"};
	private JTextField[] textFields = new JTextField[labels.length];
	private JTextArea log;
	private JButton listen;
	private JButton close;
	private JButton stop;
	private JButton quit;
	private GameServer server;
	private Wheel wheel;

	// Constructor for the server GUI.
	public ServerGUI()
	{	
		// Create the main variables that will be used.
		JPanel north = new JPanel();
		JPanel center = new JPanel(new BorderLayout());
		JPanel south = new JPanel();
		EventHandler handler = new EventHandler();
		int i = 0;
		this.setBackground(Color.BLUE);
		// Set the title and default close operation.
		this.setTitle("Game Server");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create the status label.
		JLabel statusText = new JLabel("Status:");
		north.add(statusText);
		status = new JLabel("Not Connected");
		status.setForeground(Color.RED);
		north.add(status);

		// Create the grid of text fields.
		JPanel centerNorth = new JPanel(new GridLayout(labels.length, 2, 5, 5));
		for (i = 0; i < textFields.length; i++)
		{
			JLabel label = new JLabel(labels[i], JLabel.RIGHT);
			centerNorth.add(label);
			textFields[i] = new JTextField(10);
			centerNorth.add(textFields[i]);
		}

		// Set some default values for the server.
		textFields[0].setText("8300");
		textFields[1].setText("500");

		// Buffer the grid of text fields and add it to the north part of the center.
		JPanel centerNorthBuffer = new JPanel();
		centerNorthBuffer.add(centerNorth);
		center.add(centerNorthBuffer, BorderLayout.NORTH);

		// Create the server log panel.
		JPanel serverLogPanel = new JPanel(new BorderLayout());
	    JLabel serverLabel = new JLabel("Server Log", JLabel.CENTER);
	    JPanel serverLabelBuffer = new JPanel();
	    serverLabelBuffer.add(serverLabel);
	    serverLogPanel.add(serverLabelBuffer, BorderLayout.NORTH);
	    log = new JTextArea(10, 35);
	    log.setEditable(false);
	    JScrollPane serverLogPane = new JScrollPane(log);
	    JPanel serverLogPaneBuffer = new JPanel();
	    serverLogPaneBuffer.add(serverLogPane);
	    serverLogPanel.add(serverLogPaneBuffer, BorderLayout.SOUTH);

	    // Add the server log panel to the south part of the center.
	    JPanel centerSouth = new JPanel();
	    centerSouth.add(serverLogPanel);
	    center.add(centerSouth, BorderLayout.SOUTH);

	    // Create the Wheel component.
	    wheel = new Wheel();
	    center.add(wheel, BorderLayout.CENTER); // Add the Wheel to the center panel.

		// Create the buttons.
		listen = new JButton("Listen");
		listen.addActionListener(handler);
		south.add(listen);
		close = new JButton("Close");
		close.addActionListener(handler);
		south.add(close);
		stop = new JButton("Stop");
		stop.addActionListener(handler);
		south.add(stop);
		quit = new JButton("Quit");
		quit.addActionListener(handler);
		south.add(quit);

		// Add the north, center, and south JPanels to the JFrame.
		this.add(north, BorderLayout.NORTH);
		this.add(center, BorderLayout.CENTER);
		this.add(south, BorderLayout.SOUTH);
		
		
		// Display the window.
		this.setSize(650, 650);
		this.setVisible(true);
		

		// Set up the chat server object.
		server = new GameServer();
		server.setLog(log);
		server.setStatus(status);
		server.setWheel(wheel);
	}

	// Main function that creates a server GUI when the program is started.
	public static void main(String[] args)
	{
		new ServerGUI();
	}

	// Getters for the important components.
	public JTextField getTextFieldAt(int index)
	{
		return textFields[index];
	}
	public JLabel getStatus()
	{
		return status;
	}
	public JTextArea getLog()
	{
		return log;
	}

	// Class for handling events.
	class EventHandler implements ActionListener
	{
		// Event handler for ActionEvent.
		public void actionPerformed(ActionEvent e)
		{
			// Determine which button was clicked.
			Object buttonClicked = e.getSource();

			// Handle the Listen button.
			if (buttonClicked == listen)
			{
				// Display an error if the port number or timeout was not entered.
				if (textFields[0].getText().equals("") || textFields[1].getText().equals(""))
				{
					log.append("Port number or timeout not entered before pressing Listen\n");
				}

				// Otherwise, tell the server to start listening with the user's settings.
				else
				{
					server.setPort(Integer.parseInt(textFields[0].getText()));
					server.setTimeout(Integer.parseInt(textFields[1].getText()));
					try
					{
						server.listen();
					}
					catch (IOException e1)
					{
						log.append("An exception occurred: " + e1.getMessage() + "\n");
					}
				}
			}

			// Handle the Close button.
			else if (buttonClicked == close)
			{
				// Display an error if the server has not been started.
				if (!server.isRunning())
				{
					log.append("Server not currently started\n");
				}

				// Otherwise, close the server.
				else
				{
					try
					{
						server.close();
					}
					catch (IOException e1)
					{
						log.append("An exception occurred: " + e1.getMessage() + "\n");
					}
				}
			}

			// Handle the Stop button.
			else if (buttonClicked == stop)
			{
				// Display an error if the server is not listening.
				if (!server.isListening())
				{
					log.append("Server not currently listening\n");
				}

				// Otherwise, stop listening.
				else
				{
					server.stopListening();
				}
			}

			// For the Quit button, just stop this program.
			else if (buttonClicked == quit)
			{
				System.exit(0);
			}
		}


		class Wheel extends JPanel implements ActionListener {
			private static final int NUM_SLICES = 17;
			private static final int MIN_POINTS = 300;
			private static final int MAX_POINTS = 1000;
			private static final int POINTS_INCREMENT = 50;
			private static final String[] SPECIAL_SLICES = {"Bankrupt", "Lose Turn"};

			private ArrayList<Slice> slices;
			private Timer timer;
			private int angle = 0;

			private boolean spinning;
			private int selectedPoints;
			private String specialSliceText;

			private boolean specialSelected;

			public boolean isSpecialSelected() {
				return specialSelected;
			}

			public void setSpecialSelected(boolean specialSelected) {
				this.specialSelected = specialSelected;
			}

			public int getSelectedPoints() {
				return selectedPoints;
			}

			public void setSelectedPoints(int selectedPoints) {
				this.selectedPoints = selectedPoints;
			}

			public String getSpecialSliceText() {
				return specialSliceText;
			}

			public void setSpecialSliceText(String specialSliceText) {
				this.specialSliceText = specialSliceText;
			}

			public Wheel() {
				slices = new ArrayList<>();
				initializeSlices();
				timer = new Timer(100, this);
				timer.setRepeats(true);

			}

			private void initializeSlices() {
				Random random = new Random();
				ArrayList<Integer> specialSliceIndices = new ArrayList<>();
				specialSliceIndices.add(random.nextInt(NUM_SLICES)); // First special slice index
				int secondSpecialIndex;
				do {
					secondSpecialIndex = random.nextInt(NUM_SLICES); // Second special slice index
				} while (Math.abs(specialSliceIndices.get(0) - secondSpecialIndex) < 4); // Ensure they are not adjacent

				for (int i = 0; i < NUM_SLICES; i++) {
					if (i == specialSliceIndices.get(0)) {
						slices.add(new Slice(SPECIAL_SLICES[0]));
					} else if (i == secondSpecialIndex) {
						slices.add(new Slice(SPECIAL_SLICES[1]));
					} else {
						slices.add(new Slice());
					}
				}
			}

			public void spin() {
				if (!spinning) {
					timer.start();
					spinning = true;
				}
			}

			@Override
			public void actionPerformed(ActionEvent e) {

				angle += 50; // Adjust speed of spinning here
				if (angle >= 1080) {
					angle = 0;
					timer.stop();
					spinning = false;
					// Determine which slice is selected
					selectSlice();
					/*if (specialSelected) {
					System.out.println("Uh-oh!: " + this.getSpecialSlice());
				}
				else {
					System.out.println("Points won: " + this.getSelectedPoints());
				}*/
				}
				//specialSelected = false;
				repaint();
			}

			private void selectSlice() {
				Random random = new Random();
				int randomIndex = random.nextInt(NUM_SLICES);
				Slice selectedSlice = slices.get(randomIndex);
				if (selectedSlice.isSpecial()) {
					this.setSpecialSelected(true);
					String specialText = selectedSlice.getSpecialText();
					this.setSpecialSliceText(specialText);
				} else {
					this.setSelectedPoints(selectedSlice.getPoints());
				}
			}

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g.create();
				int centerX = getWidth() / 2;
				int centerY = getHeight() / 2;
				int radius = Math.min(centerX, centerY);

				for (int i = 0; i < NUM_SLICES; i++) {
					Slice slice = slices.get(i);
					int adjustedIndex = (i + NUM_SLICES / 2) % NUM_SLICES; // Adjust index to cover the entire wheel
					g2d.setColor(slice.getColor());
					g2d.fillArc(centerX - radius, centerY - radius, radius * 2, radius * 2, angle + adjustedIndex * (360 / NUM_SLICES), 360 / NUM_SLICES);
					g2d.setColor(Color.BLACK);
					g2d.drawArc(centerX - radius, centerY - radius, radius * 2, radius * 2, angle + adjustedIndex * (360 / NUM_SLICES), 360 / NUM_SLICES);
					// Draw point value on the slice
					//drawPointValue(g2d, slice, centerX, centerY, radius, adjustedIndex);
				}

				g2d.dispose();

			}

			/*private void drawPointValue(Graphics2D g2d, Slice slice, int centerX, int centerY, int radius, int sliceIndex) {
				FontMetrics fm = g2d.getFontMetrics();
				int sliceCenterAngle = angle + sliceIndex * (360 / NUM_SLICES) + (360 / NUM_SLICES) / 2;
				double sliceCenterX = centerX + (radius / 2.0) * Math.cos(Math.toRadians(sliceCenterAngle));
				double sliceCenterY = centerY + (radius / 2.0) * Math.sin(Math.toRadians(sliceCenterAngle));

				if (!slice.isSpecial()) {
					String pointValue = String.valueOf(slice.getPoints());

					// Rotate the graphics context to draw the text vertically
					AffineTransform originalTransform = g2d.getTransform();
					AffineTransform rotatedTransform = AffineTransform.getRotateInstance(Math.toRadians(sliceCenterAngle), sliceCenterX, sliceCenterY);
					g2d.setTransform(rotatedTransform);

					// Set font to bold
					Font originalFont = g2d.getFont();
					Font boldFont = new Font(originalFont.getName(), Font.BOLD, originalFont.getSize());
					g2d.setFont(boldFont);

					// Draw the point value
					int stringWidth = fm.stringWidth(pointValue);
					g2d.drawString(pointValue, (int) sliceCenterX - (stringWidth / 2), (int) sliceCenterY);

					// Reset the graphics context transform to its original state
					g2d.setTransform(originalTransform);
					// Reset the font
					g2d.setFont(originalFont);
				} else {
					String specialText = slice.getSpecialText();
					int stringWidth = fm.stringWidth(specialText);
					g2d.drawString(specialText, (int) sliceCenterX - (stringWidth / 2), (int) sliceCenterY);
				}
			}*/

			private class Slice {
				private int points;
				private String specialText;
				private Color color; // Store the color for each slice

				public Slice() {
					Random random = new Random();
					points = MIN_POINTS + random.nextInt((MAX_POINTS - MIN_POINTS) / POINTS_INCREMENT) * POINTS_INCREMENT;
					color = generateRandomRainbowColor(random); // Generate and store color
				}

				public Slice(String specialText) {
					this.specialText = specialText;
					color = Color.LIGHT_GRAY; // Special slices are light gray
				}

				private Color generateRandomRainbowColor(Random random) {
					float hue = random.nextFloat();
					return Color.getHSBColor(hue, 1, 1);
				}

				public int getPoints() {
					return points;
				}

				public boolean isSpecial() {
					return specialText != null;
				}

				public String getSpecialText() {
					return specialText;
				}

				public Color getColor() {
					return color; // Return pre-generated color
				}
			}
		}
	}
}
