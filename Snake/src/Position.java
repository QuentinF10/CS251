import java.util.Objects;

/**
 * Name: Quentin FAYE
 * Course: CS251
 * Lab: Snake Game: Part 2 – Full Game
 */

/**
 * The Position class represents a coordinate with x and y values.
 * It is used to track positions on a game board, such as the location of walls, the snake, and food in the game.
 */
public class Position {
    private int x;
    private int y;

    /**
     * Constructs a Position instance with specified x and y coordinates.
     *
     * @param x The x-coordinate of the position.
     * @param y The y-coordinate of the position.
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x-coordinate of the position.
     *
     * @return The x-coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y-coordinate of the position.
     *
     * @return The y-coordinate.
     */
    public int getY() {
        return y;
    }

    /**
     * Compares this Position to the specified object for equality.
     * The result is true if and only if the argument is not null and is a Position object that has the same x and y values as this object.
     *
     * @param obj The object to compare this Position against.
     * @return true if the given object represents a Position equivalent to this position, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Position position = (Position) obj;
        return x == position.x && y == position.y;
    }

    /**
     * Returns a hash code value for the position.
     *
     * @return A hash code value for this position.
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
