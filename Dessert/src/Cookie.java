/**
 * Lab 3 Inheritance
 * Course: CS 251
 * Name: Quentin FAYE
 */
public class Cookie extends Dessert {

    // Stores the number of cookies
    private int numberOfCookies;
    // Stores the price per dozen cookies
    private double pricePerDozen;

    /**
     * Constructs a new cookie item
     */
    public Cookie(String name, int numberOfCookies, double pricePerDozen) {
        super(name);
        // Initialize the numberOfCookies attribute
        this.numberOfCookies = numberOfCookies;
        // Initialize the pricePerDozen attribute
        this.pricePerDozen = pricePerDozen;
    }

    /**
     * Calculate and return the total cost of the cookies
     */
    @Override
    public double getPrice() {
        return (numberOfCookies / 12.0) * pricePerDozen;
    }

    /**
     * Get the number of cookies in the order.
     */
    int getItemCount() {
        return numberOfCookies;
    }

    /**
     * Get the price per dozen cookies.
     */
    double getPricePerDozen() {
        return pricePerDozen;
    }
}
