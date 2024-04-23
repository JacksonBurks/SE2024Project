package wheelOfFortune;

import java.io.Serializable;

public class PointsData implements Serializable {
	private int points;
	
	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public PointsData(int points) {
		this.points = points;

	}


}
