package wheelOfFortune;

import java.io.Serializable;

public class WordData implements Serializable {
    private String category;
    private String word;

    public WordData(String category, String word) {
        this.category = category;
        this.word = word;
    }

    public String getCategory() {
        return category;
    }

    public String getWord() {
        return word;
    }
    
    
}