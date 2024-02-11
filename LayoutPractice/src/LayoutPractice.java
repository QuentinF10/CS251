import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;

/**
 * Name: Quentin FAYE
 * Course: CS 251
 * Lab 8 : GUI Layout Practice
 */

/**
 * The main class shows the use of Swing components to create
 * the GUI app with different shapes and a clickable button. It includes image loading,
 * button click handling, and radio buttons for shape selection.
 */
public class LayoutPractice {
    private int buttonClickCount = 0;
    private String shapeToDraw = "Rectangle"; // Default shape
    private ImageIcon circleImage, rectangleImage, roundRecImage;
    JButton button;

    /**
     * The main method to run the application.
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LayoutPractice().createAndShowGUI());
    }

    /**
     * Initializes and displays the main GUI components.
     * It sets up the frame, image panel, control panel, and shapes panel.
     */
    private void createAndShowGUI() {
        // Main frame setup
        JFrame frame = new JFrame("Layout Practice");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);

        // Image Panel
        CustomImagePanel imagePanel = new CustomImagePanel();
        frame.add(imagePanel, BorderLayout.CENTER);

        // Main container panel
        JPanel mainContainer = new JPanel();
        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));

        // Panel for Button, Label, and Radio Buttons
        JPanel controlPanel = new JPanel();
        JPanel shapePanel = new JPanel();

        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));

        shapePanel.setLayout(new BoxLayout(shapePanel, BoxLayout.Y_AXIS));
        shapePanel.setBorder(BorderFactory.createTitledBorder("Shape"));

        button = new JButton("Click for dialog");
        JLabel clickCountLabel = new JLabel("Clicks: 0");

        // Load the image of a circle, I had to use URL class since the ImageIcon wasn't working for me
        try {
            URL imageUrl = getClass().getResource("circle.png");
            if (imageUrl != null) {
                circleImage = new ImageIcon(imageUrl);
            } else {
                System.out.println("Resource not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading image");
        }
        //rectangle
        try {
            URL imageUrl = getClass().getResource("rectangle.png");
            if (imageUrl != null) {
                rectangleImage = new ImageIcon(imageUrl);
            } else {
                System.out.println("Resource not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading image");
        }
        //round rectangle
        try {
            URL imageUrl = getClass().getResource("round_rectangle.png");
            if (imageUrl != null) {
                roundRecImage = new ImageIcon(imageUrl);
            } else {
                System.out.println("Resource not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading image");
        }

        //Action listener to the dialog button
        button.addActionListener(e -> {
            buttonClickCount++;
            clickCountLabel.setText("Clicks: " + buttonClickCount);
            // Show dialog
            JOptionPane.showMessageDialog(frame, "Button clicked " + buttonClickCount + " times");
        });

        // Radio Buttons for shape selection
        JRadioButton circleButton = new JRadioButton("Circle");
        JRadioButton rectangleButton = new JRadioButton("Rectangle", true);//default shape
        JRadioButton roundRectButton = new JRadioButton("Round Rectangle");

        // Group the radio buttons
        ButtonGroup group = new ButtonGroup();

        group.add(circleButton);
        group.add(rectangleButton);
        group.add(roundRectButton);

        // Add action listeners to radio buttons
        ActionListener shapeListener = e -> {
            shapeToDraw = e.getActionCommand();
            imagePanel.repaint(); // Repaint the panel to update the shape
        };

        //commands sent when a button is clicked
        circleButton.setActionCommand("Circle");
        rectangleButton.setActionCommand("Rectangle");
        roundRectButton.setActionCommand("Round Rectangle");

        //action listeners for each button
        circleButton.addActionListener(shapeListener);
        rectangleButton.addActionListener(shapeListener);
        roundRectButton.addActionListener(shapeListener);

        // Add components to panels, and panels to main panel
        controlPanel.add(button);
        controlPanel.add(clickCountLabel);

        shapePanel.add(circleButton);
        shapePanel.add(rectangleButton);
        shapePanel.add(roundRectButton);

        mainContainer.add(controlPanel);
        mainContainer.add(shapePanel);

        // Adding the control panel to the right
        frame.add(controlPanel, BorderLayout.EAST);

        // Adding the shape panel to the bottom
        frame.add(shapePanel, BorderLayout.SOUTH);

        // Finalize and show frame
        frame.pack();
        frame.setVisible(true);
    }


    /**
     * This inner class is used for displaying the different shapes based on the selected radio button.
     * It extends JPanel and overrides the paintComponent method for custom drawing.
     */
    class CustomImagePanel extends JPanel {
        //constructor
        CustomImagePanel() {

            setPreferredSize(new Dimension(500, 480));
        }

        /**
         * Paints the selected shape image onto this panel.
         * @param g The Graphics object to be used for painting.
         */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            //what is done when a radiobutton is clicked
            switch (shapeToDraw) {
                case "Circle":
                    // Draw the loaded image for the circle case
                        g.drawImage(circleImage.getImage(), 0, 0, this);
                    break;
                case "Rectangle":
                    g.drawImage(rectangleImage.getImage(), 0, 0, this);
                    break;
                case "Round Rectangle":
                    g.drawImage(roundRecImage.getImage(), 0, 0, this);
                    break;
            }
        }
    }}
