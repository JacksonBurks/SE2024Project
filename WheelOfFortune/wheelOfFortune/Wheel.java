package wheelOfFortune;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Random;

public class Wheel extends JPanel implements ActionListener {
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
		}
		repaint();
	}


	public void selectSlice() {
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
			drawPointValue(g2d, slice, centerX, centerY, radius, adjustedIndex);
		}

		g2d.dispose();

	}

	private void drawPointValue(Graphics2D g2d, Slice slice, int centerX, int centerY, int radius, int sliceIndex) {
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
	}


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


