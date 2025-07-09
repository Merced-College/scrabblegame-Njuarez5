/**
 * Word.java
 * Author: Nestor Juarez
 * Date: July 8, 2025
 * 
 * A wrapper class for words that allows comparison and sorting.
 */

public class Word implements Comparable<Word> {

    private String text;

    public Word(String text) {
        this.text = text.toLowerCase();
    }

    public String getText() {
        return text;
    }

    @Override
    public int compareTo(Word other) {
        return this.text.compareTo(other.text);
    }

    @Override
    public String toString() {
        return text;
    }
}

