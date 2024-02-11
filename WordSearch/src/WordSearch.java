import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Course: CS 251
 * Name: Quentin FAYE
 * Lab 7: Word Search Solver
 */

/**
 * Represents the utility class to solve a word search puzzle.
 */
public class WordSearch {

    // Map that associates direction names with sets of possible directions
    private static final Map<String, Set<String>> DIRECTION_MAP = Map.of(
            "ALL", Set.of("E", "SE", "S", "SW", "W", "NW", "N", "NE"),
            "HORIZVERT", Set.of("E", "W", "S", "N"),
            "DIAGONAL", Set.of("SE", "SW", "NW", "NE"),
            "FORWARD", Set.of("NE", "E", "SE", "S")
    );

    /**
     * Loads lines from a specified file and returns them as a list of strings.
     *
     * @param fileName the name of the file to load.
     * @return a list of strings containing the lines from the file.
     * @throws IOException if an error occurs while reading the file.
     */

    public static List<String> loadFile(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName));
    }

    /**
     * The main method to start the word search solver. Requires three command line arguments:
     * the name of the word file, the name of the puzzle file, and the direction.
     *
     * @param args the command line arguments.
     */
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Provide the word file, puzzle file, and direction.");
            return;
        }

        //get value from command line args
        String wordFile = args[0];
        String puzzleFile = args[1];
        String directionArg = args[2].toUpperCase();

        // Create a Puzzle object to work with
        Puzzle puzzle = new Puzzle();

        try {
            // Load the words from the word file and the puzzle from the puzzle file
            puzzle.loadWords(loadFile(wordFile));
            puzzle.loadPuzzle(loadFile(puzzleFile));
        } catch (IOException e) {
            System.out.println("Error reading files: " + e.getMessage());
            return;
        }

        // Get the set of directions based on the direction argument
        Set<String> directions = DIRECTION_MAP.get(directionArg);

        // Find all words in the puzzle in the specified directions
        List<String[]> results = puzzle.findAllWords(directions);

        // Sort the results based on word, row, column, and direction
        results.sort((a, b) -> {
            int wordCompare = a[0].compareTo(b[0]);
            if (wordCompare != 0) return wordCompare;

            int rowCompare = Integer.compare(Integer.parseInt(a[1]), Integer.parseInt(b[1]));
            if (rowCompare != 0) return rowCompare;

            int colCompare = Integer.compare(Integer.parseInt(a[2]), Integer.parseInt(b[2]));
            if (colCompare != 0) return colCompare;

            List<String> order = Arrays.asList("E", "SE", "S", "SW", "W", "NW", "N", "NE");
            return Integer.compare(order.indexOf(a[3]), order.indexOf(b[3]));
        });

        // Print the sorted results
        for (String[] result : results) {
            System.out.println(String.join(" ", result));
        }
    }
}
