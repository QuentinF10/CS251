/**
 * Lab 3 Inheritance
 * Course : CS 251
 * Name: Quentin FAYE
 */
public class Candy extends Dessert {

    private final double weightInPounds;   // Stores the weight of the candy in pounds
    private final double pricePerPound;    // Stores the price per pound of the candy

    /**
     * Constructs a new candy item.
     */
    public Candy(String name, double weightInPounds, double pricePerPound) {
        // Call the constructor of the parent class (Dessert) with the candy's name
        super(name);
        // Initialize the weightInPounds attribute
        this.weightInPounds = weightInPounds;
        // Initialize the pricePerPound attribute
        this.pricePerPound = pricePerPound;
    }

    /**
     * Calculate and return the total cost of the candy.
     */
    @Override
    public double getPrice() {
        return pricePerPound * weightInPounds;
    }

    /**
     * Get the weight of the candy in pounds.
     */
    public double getWeightInPounds() {
        return weightInPounds;
    }

    /**
     * Get the price per pound of the candy.
     */
    public double getPricePerPound() {
        return pricePerPound;
    }
}
