import java.util.List;

/**
 * Name: Quentin FAYE
 * Course: CS251
 * Lab: Snake Game: Part 1 – Game Manager
 */

/**
 * The LevelConfiguration class encapsulates the configuration details of a game level.
 * This includes the dimensions of the game board and the positions of walls within it.
 */
public class LevelConfiguration {
    private static int width;
    private static int height;
    private List<Position> walls;

    /**
     * Constructs a new LevelConfiguration with the specified dimensions and wall positions.
     *
     * @param width  The width of the game board.
     * @param height The height of the game board.
     * @param walls  A list of Position objects representing the locations of walls on the game board.
     */
    public LevelConfiguration(int width, int height, List<Position> walls) {
        this.width = width;
        this.height = height;
        this.walls = walls;
    }

    /**
     * Returns the width of the game board.
     *
     * @return The width as an integer.
     */
    public static int getWidth() {
        return width;
    }

    /**
     * Returns the height of the game board.
     *
     * @return The height as an integer.
     */
    public static int getHeight() {
        return height;
    }

    /**
     * Returns the list of walls on the game board.
     * Each wall is represented as a Position object.
     *
     * @return A list of Position objects representing the walls.
     */
    public List<Position> getWalls() {
        return walls;
    }

}
