package wheelOfFortune;

import java.io.Serializable;

public class SolveData implements Serializable {
    private boolean isCorrect;

    public SolveData(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public boolean isCorrect() {
        return isCorrect;
    }
}