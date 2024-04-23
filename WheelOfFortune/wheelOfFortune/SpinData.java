package wheelOfFortune;

import java.io.Serializable;

public class SpinData implements Serializable {
	
	private String spinType;
	private boolean spun;

	public boolean clickedSpin() {
		return spun;
	}

	public void setSpun(boolean spun) {
		this.spun = spun;
	}

	public String getSpinType() {
		return spinType;
	}

	public void setSpinType(String spinType) {
		this.spinType = spinType;
	}

}