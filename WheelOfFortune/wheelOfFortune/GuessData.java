package wheelOfFortune;

import java.io.Serializable;

public class GuessData implements Serializable {
    private boolean isCorrect;

    public GuessData(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public boolean isCorrect() {
        return isCorrect;
    }
}