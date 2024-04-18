package wheelOfFortune;

import java.io.Serializable;

public class SpinData implements Serializable {
	
	private boolean spun;

	public boolean clickedSpin() {
		return spun;
	}

	public void setSpun(boolean spun) {
		this.spun = spun;
	}
	
}