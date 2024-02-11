/**
 * Name: Quentin FAYE
 * Course: CS251
 * Lab: Snake Game: Part 2 – Full Game
 */

/**
 * The {@code Nibbles} class serves as the entry point for the Nibbles game,
 * handling initial setup and launch. It manages game configurations and initializes
 * the game environment based on provided or default settings.
 */
public class Nibbles {

    /**
     * The main method serves as the entry point for the Snake game.
     * It reads level configuration from a file (if provided) or uses a default configuration.
     * It then initializes the game manager with this configuration and starts the game UI.
     *
     * @param args Command line arguments. If provided, the first argument is expected
     *             to be the path to a level configuration file.
     */
    public static void main(String[] args) {
        LevelConfiguration config;


        if (args.length > 0) {
            // Parse level configuration from the provided file
            config = GameManager.parseLevelFile(args[0]);
        } else {
            // Parse level configuration from a default file
            config = GameManager.parseLevelFile("maze-simple.txt");
        }

        // Create an instance of GameManager with the parsed configuration
       GameManager gameManager = new GameManager(config.getWidth(), config.getHeight(), config.getWalls());

        // Run the UI in the Event Dispatch Thread
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SnakeUI(gameManager); // Pass gameManager to SnakeUI
            }
        });
    }
}
