/**
 * Lab 3 Inheritance
 * Course: CS 251
 * Name: Quentin FAYE
 */
public class Sundae extends IceCream {
    // Stores the topping dessert for the sundae
    private final Dessert topping;

    /**
     * Constructs a new Sundae
     */
    public Sundae(IceCream iceCream, Dessert topping) {
        // Call the constructor of the parent class with the ice cream's name and price
        super(iceCream.getName(), iceCream.getPrice());
        // Initialize the topping attribute
        this.topping = topping;
    }

    /**
     * Calculate and return the total cost of the sundae.(includes the price of the base ice cream and the topping)
     */
    @Override
    public double getPrice() {
        // Sum the prices of the base ice cream and the topping
        return super.getPrice() + topping.getPrice();
    }

    /**
     * Get the name of the sundae.
     */
    @Override
    public String getName() {
        // Combine the names of the base ice cream and the topping
        return super.getName() + " topped with " + topping.getName();
    }
}
