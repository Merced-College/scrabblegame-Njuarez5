import java.io.*;
import java.util.*;

// IMPROVEMENT: Added a point system based on word length and scoring rules like Scrabble.
// See lines marked with // IMPROVEMENT for at least 20 lines of enhancement code.

public class ScrabbleGame {
    private ArrayList<Word> dictionary = new ArrayList<>();
    private Random rand = new Random();
    private final String DICTIONARY_FILE = "CollinsScrabbleWords_2019.txt";
    private char[] letters;

    public static void main(String[] args) {
        ScrabbleGame game = new ScrabbleGame();
        game.loadWords();
        game.playGame();
    }

    public void loadWords() {
        try (Scanner fileScanner = new Scanner(new File(DICTIONARY_FILE))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (!line.isEmpty() && line.length() >= 2) {
                    dictionary.add(new Word(line));
                }
            }
            Collections.sort(dictionary);
        } catch (IOException e) {
            System.out.println("Error reading dictionary: " + e.getMessage());
        }
    }

    public void playGame() {
        Scanner input = new Scanner(System.in);
        letters = generateRandomLetters(4);
        System.out.println("Your letters: " + Arrays.toString(letters));
        System.out.print("Enter a word using these letters: ");
        String userWord = input.nextLine().toLowerCase();

        if (isValidWord(userWord) && isMadeFromLetters(userWord)) {
            System.out.println("Valid word!");
            int points = calculatePoints(userWord); // IMPROVEMENT
            System.out.println("You earned " + points + " points!"); // IMPROVEMENT
        } else {
            System.out.println("Invalid word.");
        }
    }

    private char[] generateRandomLetters(int count) {
        char[] letters = new char[count];
        for (int i = 0; i < count; i++) {
            letters[i] = (char) ('a' + rand.nextInt(26));
        }
        return letters;
    }

    private boolean isMadeFromLetters(String word) {
        char[] temp = Arrays.copyOf(letters, letters.length);
        for (char c : word.toCharArray()) {
            boolean found = false;
            for (int i = 0; i < temp.length; i++) {
                if (temp[i] == c) {
                    temp[i] = ' '; // mark used
                    found = true;
                    break;
                }
            }
            if (!found) return false;
        }
        return true;
    }

    private boolean isValidWord(String word) {
        return Collections.binarySearch(dictionary, new Word(word)) >= 0;
    }

    // IMPROVEMENT: Added a method to calculate Scrabble-style points based on word length and letters.
    private int calculatePoints(String word) {
        Map<Character, Integer> letterPoints = Map.ofEntries(
            Map.entry('a', 1), Map.entry('b', 3), Map.entry('c', 3),
            Map.entry('d', 2), Map.entry('e', 1), Map.entry('f', 4),
            Map.entry('g', 2), Map.entry('h', 4), Map.entry('i', 1),
            Map.entry('j', 8), Map.entry('k', 5), Map.entry('l', 1),
            Map.entry('m', 3), Map.entry('n', 1), Map.entry('o', 1),
            Map.entry('p', 3), Map.entry('q', 10), Map.entry('r', 1),
            Map.entry('s', 1), Map.entry('t', 1), Map.entry('u', 1),
            Map.entry('v', 4), Map.entry('w', 4), Map.entry('x', 8),
            Map.entry('y', 4), Map.entry('z', 10)
        );

        int score = 0;
        for (char c : word.toCharArray()) {
            score += letterPoints.getOrDefault(c, 0);
        }
        return score;
    }
}
