import javax.swing.*;
import java.io.File;
import java.io.InputStream;
import java.util.*;

/**
 * Name: Quentin FAYE
 * Course: CS251
 * Lab: Snake Game: Part 2 – Full Game
 */

/**
 * The GameManager class manages the state and logic of a snake game.
 * It tracks the game dimensions, walls, snake position, food position, and game logic.
 */
public class GameManager {
    private int width, height,score;
    private List<Position> walls;
    private Snake snake;
    private Position food;
    private Random random;
    private GameOver gameOverListener;

     // Represents possible directions the snake can move in the game.
    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    /**
     * Constructor for GameManager.
     * Initializes the game board with specified dimensions and initial walls.
     *
     * @param width         Width of the game board.
     * @param height        Height of the game board.
     * @param initialWalls  List of initial wall positions.
     */
    public GameManager(int width, int height, List<Position> initialWalls) {
        this.width = width;
        this.height = height;
        this.walls = new ArrayList<>(initialWalls); // Initialize with the provided walls
        this.random = new Random(); // Fixed seed for reproducibility
        score = 0; // Initialize score

        // Ensure that the initial food position is not on a wall
        do {
            food = findUnoccupiedPosition();
        } while (walls.contains(food));

        // Ensure that the initial snake position is not on a wall or food
        Position snakeStart;
        do {
            snakeStart = findUnoccupiedPosition();
        } while (walls.contains(snakeStart) || snakeStart.equals(food));

        this.snake = new Snake(snakeStart);
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    /**
     * Finds an unoccupied position on the game board.
     *
     * @return A Position object that is not occupied by walls, the snake, or food.
     */
    private Position findUnoccupiedPosition() {
        Position position;
        do {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            position = new Position(x, y);
        } while (isOccupied(position));
        return position;
    }

    /**
     * Checks if a given position is occupied by walls, the snake, or food.
     *
     * @param position The position to check.
     * @return True if the position is occupied, false otherwise.
     */
    private boolean isOccupied(Position position) {
        // Check for walls
        if (walls.contains(position)) return true;

        // Check if position is occupied by the snake
        if (snake != null && snake.getSegments().contains(position)) return true;

        // Check if position is already occupied by food
        if (position.equals(food)) return true;

        // Check if at least one adjacent cell is not a wall
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                // Skip the center cell
                if (dx == 0 && dy == 0) continue;

                Position adjacent = new Position(position.getX() + dx, position.getY() + dy);
                if (isInBounds(adjacent) && !walls.contains(adjacent)) {
                    return false; // Adjacent cell is not a wall, thus the space is unoccupied
                }
            }
        }

        // If all adjacent cells are walls, then the space is considered occupied
        return true;
    }

    // Helper method to check if a position is within game bounds
    private boolean isInBounds(Position position) {
        // Assuming you have defined the bounds of your game area
        // e.g., minX, maxX, minY, maxY
        return !isOutOfBounds(position);
    }


    /**
     * Places food at a random unoccupied position on the game board.
     *
     * @return The position where the food is placed.
     */
    private Position placeFood() {
        return findUnoccupiedPosition();
    }

    /**
     * Updates the direction of the snake's movement.
     *
     * @param newDirection The new direction for the snake to move in.
     */
    public void updateSnakeDirection(Direction newDirection) {

        // Check if new direction is not directly opposite to current direction to prevent the snake from reversing onto itself
        if ((newDirection == Direction.UP && snake.getCurrentDirection() != Direction.DOWN) ||
                (newDirection == Direction.DOWN && snake.getCurrentDirection() != Direction.UP) ||
                (newDirection == Direction.LEFT && snake.getCurrentDirection() != Direction.RIGHT) ||
                (newDirection == Direction.RIGHT && snake.getCurrentDirection() != Direction.LEFT)) {

            snake.changeDirection(newDirection);
        }
    }

    /**
     * Updates the state of the game, including moving the snake and checking for collisions.
     */
    public void updateGameState() {

        snake.move();

        // Check for boundary conditions
        if (isOutOfBounds(snake.getHeadPosition())) {
            // Notify about game over
            if (gameOverListener != null) {
                gameOverListener.onGameOver();
            }
            System.out.println("Snake has moved out of bounds.");
            return; // gameOver logic to be decided for next time
        }
        if (checkCollisionWithFood()) {
            snake.grow();
            food = placeFood();
            score++;
            // Notify the UI to update the score
            if (gameOverListener != null && gameOverListener instanceof SnakeUI) {
                ((SnakeUI) gameOverListener).updateScoreDisplay(score);
            }
            System.out.println("Snake eats food.");
        } else if (checkCollisionWithWall()) {
            System.err.println("Collision with wall detected.");

        } else if (checkCollisionWithSelf()) {
            System.err.println("Collision with self detected.");
        }
        if (checkCollisionWithWall() || checkCollisionWithSelf()) {
            // Notify about game over
            if (gameOverListener != null) {
                gameOverListener.onGameOver();
            }
        }
    }

    /**
     * Sets a game over listener for the game.
     * This listener is notified when the game ends through a loss condition.
     *
     * @param listener The {@code GameOver} listener to be set for handling game over events.
     */
    public void setGameOverListener(GameOver listener) {
        this.gameOverListener = listener;
    }

    /**
     * Retrieves the current score of the game.
     * The score increases as the snake eats food.
     *
     * @return The current score of the game.
     */
    public int getCurrentScore() {
        return score;
    }

    /**
     * Resets the game to its initial state.
     * This involves repositioning the snake and the food, and resetting the score.
     * The snake is placed at a random unoccupied position, and the food is also placed
     * at a random position, ensuring it doesn't overlap with the snake's starting position.
     */
    public void resetGame() {
        // Place the snake at a random unoccupied position
        Position snakeStart = findUnoccupiedPosition();
        snake = new Snake(snakeStart);

        // Place food at a random unoccupied position
        food = findUnoccupiedPosition();

        // Reset score
        score = 0;

        // Make sure the food is not placed where the snake is
        while (food.equals(snakeStart)) {
            food = findUnoccupiedPosition();
        }
    }


    /**
     * Checks if a position is out of the game board's bounds.
     *
     * @param position The position to check.
     * @return True if the position is out of bounds, false otherwise.
     */
    private boolean isOutOfBounds(Position position) {
        return position.getX() < 0 || position.getX() > width-1 ||
                position.getY() < 0 || position.getY() > height-1;
    }

    /**
     * Checks if the snake has collided with the food.
     *
     * @return True if the snake's head position is the same as the food's position, false otherwise.
     */
    boolean checkCollisionWithFood() {
        return snake.getHeadPosition().equals(food);
    }

    /**
     * Checks if the snake has collided with a wall.
     *
     * @return True if the snake's head position is on a wall, false otherwise.
     */
    boolean checkCollisionWithWall() {
        Position head = snake.getHeadPosition();
        return walls.contains(head);
    }

    // Method to get the list of wall positions
    public List<Position> getWalls() {
        return walls;
    }

    // Method to get the food position
    public Position getFoodPosition() {
        return food;
    }

    // Method to get the snake's head position
    public Position getSnakeHeadPosition() {
        if (snake != null && !snake.getSegments().isEmpty()) {
            return snake.getSegments().get(0);
        }
        return null; // or handle this case as per your logic
    }

    // Method to get the snake's body positions (excluding the head)
    public List<Position> getSnakeBodyPositions() {
        List<Position> body = new ArrayList<>();
        if (snake != null) {
            // Start from 1 to skip the head
            for (int i = 1; i < snake.getSegments().size(); i++) {
                body.add(snake.getSegments().get(i));
            }
        }
        return body;
    }

    /**
     * Parses the specified level file to create a LevelConfiguration object.
     * This method reads from a file and extracts the level dimensions (width and height)
     * and the positions of walls within the level.
     *
     * @param levelFile The path to the level configuration file.
     * @return A {@code LevelConfiguration} object containing the parsed width, height,
     *         and wall positions of the level. If there is an error reading the file,
     *         the returned LevelConfiguration may be incomplete or empty.
     */
    public static LevelConfiguration parseLevelFile(String levelFile) {
        int width = 0, height = 0;
        List<Position> walls = new ArrayList<>();

        try (InputStream is = GameManager.class.getClassLoader().getResourceAsStream(levelFile);
             Scanner scanner = new Scanner(is)){
            if (scanner.hasNextInt()) {
                width = scanner.nextInt();
                if (scanner.hasNextInt()) {
                    height = scanner.nextInt();
                }
            }

            while (scanner.hasNextLine()) {
                scanner.nextLine();
                if (scanner.hasNextInt()) {
                    int x1 = scanner.nextInt();
                    int x2 = scanner.nextInt();
                    int y1 = scanner.nextInt();
                    int y2 = scanner.nextInt();

                    for (int x = x1; x <= x2; x++) {
                        for (int y = y1; y <= y2; y++) {
                            walls.add(new Position(x, y));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error reading level configuration file: " + levelFile);
            return null; // Return null or handle appropriately
        }

        if (width <= 0 || height <= 0) {
            System.err.println("Invalid width or height in level file. Width: " + width + ", Height: " + height);
            return null; // Return null or handle appropriately
        }

        return new LevelConfiguration(width, height, walls);
    }




    /**
     * Checks if the snake has collided with itself.
     *
     * @return True if the snake's head position matches any of its body segment positions, false otherwise.
     */
    boolean checkCollisionWithSelf() {
        Position head = snake.getHeadPosition();
        // Check if the head's position matches any of the body segment positions
        // Skip the first segment since it's the head itself
        for (int i = 1; i < snake.getSegments().size(); i++) {
            if (head.equals(snake.getSegments().get(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a string representation of the game board.
     *
     * @return A string depicting the current state of the game board.
     */
    @Override
    public String toString() {
        char[][] board = new char[height][width];

        // Initialize the game board with empty spaces
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                board[i][j] = '.'; // Use '.' for empty space
            }
        }

        // Place walls on the board
        for (Position wall : walls) {
            board[wall.getY()][wall.getX()] = 'X'; // Use '#' for walls
        }

        // Place the snake's body on the board
        List<Position> snakeSegments = snake.getSegments();
        for (int i = 1; i < snakeSegments.size(); i++) {
            Position segment = snakeSegments.get(i);
            board[segment.getY()][segment.getX()] = 's'; // Use 's' for the snake's body
        }

        // Place the head of the snake on the board
        if (!snakeSegments.isEmpty()) {
            Position head = snakeSegments.get(0);
            board[head.getY()][head.getX()] = 'S'; // Use 'S' for the head of the snake
        }

        // Place the food on the board
        if (food != null) {
            board[food.getY()][food.getX()] = 'f'; // Use 'F' for food
        }

        // Convert the board into a string
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                sb.append(board[i][j]);
            }
            sb.append('\n'); // New line at the end of each row
        }

        return sb.toString();
    }

}
