package wheelOfFortune;

import java.io.Serializable;

public class VowelData implements Serializable {
    private boolean purchased;

    public VowelData(boolean purchased) {
        this.purchased = purchased;
    }

    public boolean bought() {
        return purchased;
    }
}