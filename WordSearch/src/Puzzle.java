import java.util.*;
import java.io.*;

/**
 * Course: CS 251
 * Name: Quentin FAYE
 * Lab 7: Word Search Solver
 *
 */

/**
 * Represents a puzzle. Contains utilities to insert words,
 * load the puzzle grid, and find words in the puzzle.
**/

public class Puzzle {

    //Map of directions associated to int values
    private static final Map<String, int[]> DIRECTIONS = Map.of(
            "E", new int[]{0, 1},
            "SE", new int[]{1, 1},
            "S", new int[]{1, 0},
            "SW", new int[]{1, -1},
            "W", new int[]{0, -1},
            "NW", new int[]{-1, -1},
            "N", new int[]{-1, 0},
            "NE", new int[]{-1, 1}
    );

    /**
     * I've decided to use Trie data structure to resolve this Lab.
     * I've had many difficulties trying to resolve it without the Trie, my program would take minutes to compile. That's why when researching for a solution, I found
     * Tries. According to the api, Tries can be used to sort a large dataset efficiently, as they support fast search and insertion operations.
     *
     *  To overcome my issues, the use of Trie allowed my program to be more efficient, compiling in less than 15secs (except for dictionary and bigPuzz2 that takes 33secs approx)
     */


    /**
     * Represents a node within the Trie data structure.
     * It contains a mapping of characters to child nodes and
     * an optional word that ends at this node.
     */
    public class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        String word = null;
    }

    // The root of the Trie data structure.
    private TrieNode root;
    // Set of words to be searched for in the puzzle.
    private Set<String> words;
    // 2D char array representing the puzzle grid.
    private char[][] puzzle;
    // Dimensions of the puzzle grid.
    private int rows, cols;

    /**
     * Initializes a new puzzle instance with an empty trie root and word set.
     */
    public Puzzle() {
        this.root = new TrieNode();
        this.words = new HashSet<>();
    }

    /**
     * Inserts a word into the trie structure.
     *
     * @param word the word to insert.
     */
    private void insert(String word) {
        // Start from the root node
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
            // If the character doesn't exist, create a new node
            node.children.putIfAbsent(ch, new TrieNode());
            // Navigate to the next node
            node = node.children.get(ch);
        }
        // Mark the end of the word at the current node
        node.word = word;
    }

    /**
     * Loads a list of words into the puzzle.
     *
     * @param wordList the list of words to load.
     */
    public void loadWords(List<String> wordList) {
        this.words.addAll(wordList);
        for (String word : wordList) {
            insert(word);
        }
    }

    /**
     * Loads the puzzle grid from a list of string data. The first line
     * of the list specifies the dimensions of the grid.
     *
     * @param puzzleData the list of string data containing the puzzle grid.
     */
    public void loadPuzzle(List<String> puzzleData) {
        String[] dimensions = puzzleData.get(0).split(" ");
        this.rows = Integer.parseInt(dimensions[0]);
        this.cols = Integer.parseInt(dimensions[1]);

        this.puzzle = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            puzzle[i] = puzzleData.get(i + 1).toCharArray();
        }
    }

    /**
     * Helper method to check if a given position (x,y) is within the bounds of the puzzle grid.
     *
     * @param x the row index.
     * @param y the column index.
     * @return true if the position is valid, false otherwise.
     */
    private boolean isValidPosition(int x, int y) {
        return x >= 0 && x < rows && y >= 0 && y < cols;
    }

    /**
     * A  search method to search for words in the grid starting from a specific position and moving in a specific direction.
     * If a word from the word set is found, its details are added to the foundPositions list.
     * This is the method I've had many issues with it, the main one being the efficiency.
     *
     * @param x current row index.
     * @param y current column index.
     * @param node current TrieNode.
     * @param direction direction to search in.
     * @param foundPositions list to store found words and their details.
     * @param startX starting row index.
     * @param startY starting column index.
     */
    private void searchWord(int x, int y, TrieNode node, String direction, List<String[]> foundPositions, int startX, int startY) {
        // Base condition: if the current position is out of grid bounds or the character doesn't exist in the Trie, return
        if (!isValidPosition(x, y) || node.children.get(puzzle[x][y]) == null) {
            return;
        }

        // Fetch the character at the current position
        char currentChar = puzzle[x][y];
        // Navigate to the next node in the trie
        node = node.children.get(currentChar);

        // If the current node marks the end of a word, add the word details to the results list
        if (node.word != null) {
            foundPositions.add(new String[]{node.word, Integer.toString(startX), Integer.toString(startY), direction});
        }

        // Get the offsets for the specified direction
        int[] dirOffsets = DIRECTIONS.get(direction);
        int dx = dirOffsets[0];
        int dy = dirOffsets[1];

        // Temporarily mark the current cell to avoid revisiting it
        puzzle[x][y] = '#';
        // Recursive DFS search in the specified direction
        searchWord(x + dx, y + dy, node, direction, foundPositions, startX, startY);
        // Reset the cell back to its original character (backtrack)
        puzzle[x][y] = currentChar;
    }

    /**
     * Main method to find all words in the puzzle grid.
     * It iterates over each cell in the grid and searches in the specified directions.
     *
     * @param directions set of directions to search in.
     * @return a list of found words and their details (word, starting row, starting column, direction).
     */
    public List<String[]> findAllWords(Set<String> directions) {
        // List to store the found words along with their positions and direction
        List<String[]> results = new ArrayList<>();

        // Iterate through each cell of the puzzle grid
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {

                // For each cell, search in all specified directions
                for (String direction : directions) {
                    searchWord(x, y, root, direction, results, x, y);
                }
            }
        }
        return results;
    }

}