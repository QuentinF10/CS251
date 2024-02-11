import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


/**
 * Name: Quentin FAYE
 * Course: CS251
 * Lab: Snake Game: Part 2 – Full Game
 */

/**
 * The {@code SnakeUI} class extends JFrame and is responsible for rendering
 * the user interface of the Snake game. It handles key events for controlling
 * the snake, updating the game state, and rendering the game board.
 */
public class SnakeUI extends JFrame implements GameOver {
    private final int boardWidth, boardHeight; // Dynamically set based on GameManager
    private final int cellSize = 20; // Size of each cell in pixels
    private GameManager gameManager;
    private JPanel gameBoard;
    private JLabel scoreLabel;
    private JButton startPauseButton;
    private Timer gameTimer;
    private boolean isGameRunning = false;

    /**
     * Constructs a new SnakeUI instance, initializing the user interface and game components.
     *
     * @param gameManager The game manager that controls the game logic and state.
     */
    public SnakeUI(GameManager gameManager) {
        this.gameManager = gameManager;
        this.boardWidth = gameManager.getWidth();
        this.boardHeight = gameManager.getHeight(); // Get height from GameManager
        // Set the game over listener
        gameManager.setGameOverListener(this);


        // Initialize the UI
        setTitle("Snake Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize the game board
        gameBoard = new GamePanel();
        gameBoard.setPreferredSize(new Dimension(cellSize * boardWidth, cellSize * boardHeight));

        add(gameBoard, BorderLayout.CENTER);

        // Score Label
        scoreLabel = new JLabel("Score: 0"); // Update score as needed
        add(scoreLabel, BorderLayout.NORTH);
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Start/Pause Button
        startPauseButton = new JButton("Start");
        startPauseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                toggleGame();
            }
        });
        add(startPauseButton, BorderLayout.SOUTH);


        // Initialize the Timer
        gameTimer = new Timer(200, new ActionListener() { // 200ms for example
            @Override
            public void actionPerformed(ActionEvent e) {
                gameManager.updateGameState(); // Method to update the game state
                gameBoard.repaint(); // Repaint the game board
            }
        });

        // Keyboard controls
        setFocusable(true);
        requestFocusInWindow();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Start the game on a specific key press if it's not already running
                if ((!isGameRunning && e.getKeyCode() == KeyEvent.VK_UP )|| (!isGameRunning && e.getKeyCode() == KeyEvent.VK_DOWN)
                        || (!isGameRunning && e.getKeyCode() == KeyEvent.VK_LEFT) || !isGameRunning && e.getKeyCode() == KeyEvent.VK_RIGHT) { // Example: Start with space bar
                    isGameRunning = true;
                    toggleGame();
                }
                System.out.println("Key pressed: " + KeyEvent.getKeyText(e.getKeyCode())); // Debugging
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        gameManager.updateSnakeDirection(GameManager.Direction.UP);
                        break;
                    case KeyEvent.VK_DOWN:
                        gameManager.updateSnakeDirection(GameManager.Direction.DOWN);
                        break;
                    case KeyEvent.VK_LEFT:
                        gameManager.updateSnakeDirection(GameManager.Direction.LEFT);
                        break;
                    case KeyEvent.VK_RIGHT:
                        gameManager.updateSnakeDirection(GameManager.Direction.RIGHT);
                        break;
                }
            }
        });

        // Display the frame
        pack();
        setVisible(true);
    }

    /**
     * Called when the game is over to handle any necessary UI updates or notifications.
     */
    @Override
    public void onGameOver() {
        // Handle game over (show message, reset game, etc.)
        gameOver();
    }

    /**
     * Toggles the game's running state, starting or pausing the game as necessary.
     */
    private void toggleGame() {
        if (isGameRunning && startPauseButton.getText().equals("Start")) {
            gameTimer.start();
            isGameRunning = true;
            startPauseButton.setText("Pause");
        } else if (!isGameRunning && startPauseButton.getText().equals("Start")) {
            JOptionPane.showMessageDialog(SnakeUI.this, "Press a key to start the game.", "Start Game", JOptionPane.INFORMATION_MESSAGE);
            requestFocusInWindow();  // Request focus back to the JFrame
        } else {
            gameTimer.stop();
            JOptionPane.showMessageDialog(this, "The game is paused. Current score is: " + gameManager.getCurrentScore());
            startPauseButton.setText("Start");
            isGameRunning = false;
            requestFocusInWindow();  // Request focus back to the JFrame
        }
    }

    // Method to handle game over condition
    public void gameOver() {
        gameTimer.stop();
        JOptionPane.showMessageDialog(this, "Game Over. Your score was: " + gameManager.getCurrentScore());
        gameManager.resetGame(); // Reset the game state
        startPauseButton.setText("Start");
        isGameRunning = false;
        scoreLabel.setText("Score: 0");
        gameBoard.repaint(); // Repaint the board to reflect the reset state
    }

    /**
     * The {@code GamePanel} class is a private inner class of {@code SnakeUI} used to
     * render the game board. It draws the snake, food, and walls on the panel.
     */
    private class GamePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            setBackground(Color.BLACK);

            // Calculate cell size using floating-point division for accuracy
            double cellWidth = (double) getWidth() / boardWidth;
            double cellHeight = (double) getHeight() / boardHeight;

            // Assuming cellSize is the size of each cell, and no additional gap is needed
            for (int row = 0; row < boardHeight; row++) { // Loop over rows (height)
                for (int col = 0; col < boardWidth; col++) { // Loop over columns (width)
                    Position pos = new Position(col, row);

                    if (gameManager.getWalls().contains(pos)) {
                        g.setColor(Color.GRAY); // Wall color
                    } else if (pos.equals(gameManager.getFoodPosition())) {
                        g.setColor(Color.ORANGE); // Food color
                    } else if (pos.equals(gameManager.getSnakeHeadPosition())) {
                        g.setColor(Color.RED); // Snake head color
                    } else if (gameManager.getSnakeBodyPositions().contains(pos)) {
                        g.setColor(Color.GREEN); // Snake body color
                    } else {
                        continue; // Skip empty cells
                    }
                    g.fillRect((int) (col * cellWidth), (int) (row * cellHeight),
                            (int) cellWidth, (int) cellHeight);
                }
            }
        }

}

    /**
     * Updates the score display on the UI thread.
     *
     * @param newScore The new score to be displayed.
     */
    public void updateScoreDisplay(int newScore) {
        SwingUtilities.invokeLater(() -> {
            scoreLabel.setText("Score: " + newScore);
        });
    }
}
