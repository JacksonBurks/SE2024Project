package wheelOfFortune;

import java.io.Serializable;

public class SpinData implements Serializable {
	
	private boolean spun;
	private String spinType;

	public String getSpinType() {
		return spinType;
	}

	public void setSpinType(String spinType) {
		this.spinType = spinType;
	}

	public boolean clickedSpin() {
		return spun;
	}

	public void setSpun(boolean spun) {
		this.spun = spun;
	}
	
}