/**
 * ScrabbleGame.java
 * Author: Nestor Juarez
 * Date: July 8, 2025
 * 
 * A simple Scrabble-like game that allows the user to form a word 
 * from 4 random letters and calculates a score based on Scrabble rules.
 */

import java.io.*;
import java.util.*;

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

    /**
     * Loads the dictionary file into an ArrayList and sorts it.
     */
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

    /**
     * Main game loop: show letters, accept user input, and score the word.
     */
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

    /**
     * Generates a random array of lowercase letters.
     */
    private char[] generateRandomLetters(int count) {
        char[] letters = new char[count];
        for (int i = 0; i < count; i++) {
            letters[i] = (char) ('a' + rand.nextInt(26));
        }
        return letters;
    }

    /**
     * Checks if the word can be formed from the given random letters.
     */
    private boolean isMadeFromLetters(String word) {
        char[] temp = Arrays.copyOf(letters, letters.length);
        for (char c : word.toCharArray()) {
            boolean found = false;
            for (int i = 0; i < temp.length; i++) {
                if (temp[i] == c) {
                    temp[i] = ' '; // mark as used
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the word exists in the loaded dictionary.
     */
    private boolean isValidWord(String word) {
        return Collections.binarySearch(dictionary, new Word(word)) >= 0;
    }

    /**
     * IMPROVEMENT: Calculates score based on Scrabble letter values.
     */
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
