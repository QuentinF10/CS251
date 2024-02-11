import java.util.LinkedList;
import java.util.List;

/**
 * Name: Quentin FAYE
 * Course: CS251
 * Lab: Snake Game: Part 2 – Full Game
 */

/**
 * The Snake class represents the snake in the snake game.
 * It maintains a list of segments that represent the snake's body, along with the direction of movement.
 * The class provides methods to move the snake, change its direction, and grow its length.
 */
public class Snake {
    private LinkedList<Position> segments;
    private GameManager.Direction currentDirection;
    private boolean hasEatenFood = false;

    /**
     * Constructs a Snake instance with an initial starting position.
     *
     * @param startPosition The position where the snake's head will start.
     */
    public Snake(Position startPosition) {
        segments = new LinkedList<>();
        segments.add(startPosition);
        //this.currentDirection = GameManager.Direction.RIGHT; // Default direction
    }

    /**
     * Moves the snake in its current direction.
     * Adds a new segment at the front (head) and removes the last segment (tail) unless the snake has eaten food.
     */
    public void move() {
        // Get current head position
        Position head = getHeadPosition();

        // Calculate new head position based on the current direction
        int newX = head.getX();
        int newY = head.getY();
        switch (currentDirection) {
            case UP:
                newY--;
                break;
            case DOWN:
                newY++;
                break;
            case LEFT:
                newX--;
                break;
            case RIGHT:
                newX++;
                break;
        }
        Position newHead = new Position(newX, newY);

        // Add new head at the beginning of the list
        segments.addFirst(newHead);

        if(segments.size()<4){
            grow();
        }
        // Remove the tail segment if the snake has not eaten food
        // A flag is set when the snake eats food
        if (!hasEatenFood) {
            segments.removeLast();
        } else {
            hasEatenFood = false; // Reset the flag after growing
        }
    }

    /**
     * Grows the snake by one segment.
     * This is achieved by not removing the tail segment during the next movement.
     */
    public void grow() {
        // Set flag to indicate that the snake has eaten food
        hasEatenFood = true;
    }

    /**
     * Changes the snake's current direction.
     * The snake cannot reverse direction directly (e.g., cannot change from UP to DOWN directly).
     *
     * @param newDirection The new direction for the snake to move in.
     */
    public void changeDirection(GameManager.Direction newDirection) {
        // Prevent the snake from reversing direction directly
        if ((newDirection == GameManager.Direction.UP && this.currentDirection != GameManager.Direction.DOWN) ||
                (newDirection == GameManager.Direction.DOWN && this.currentDirection != GameManager.Direction.UP) ||
                (newDirection == GameManager.Direction.LEFT && this.currentDirection != GameManager.Direction.RIGHT) ||
                (newDirection == GameManager.Direction.RIGHT && this.currentDirection != GameManager.Direction.LEFT)) {
            this.currentDirection = newDirection;
        }
    }

    /**
     * Returns the current direction of the snake.
     *
     * @return The current direction in which the snake is moving.
     */
    public GameManager.Direction getCurrentDirection() {
        return currentDirection;
    }

    /**
     * Returns the position of the snake's head.
     *
     * @return The Position object representing the head of the snake.
     */
    public Position getHeadPosition() {
        return segments.getFirst();
    }

    /**
     * Returns a list of the snake's segments.
     * Each segment represents a part of the snake's body.
     *
     * @return A list of Position objects representing the snake's segments.
     */
    public List<Position> getSegments() {
        return segments;
    }
}
