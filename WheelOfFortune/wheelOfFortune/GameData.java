package wheelOfFortune;

import java.io.Serializable;

public class GameData implements Serializable {
    private boolean isCorrect;

    public GameData(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public boolean isCorrect() {
        return isCorrect;
    }
}