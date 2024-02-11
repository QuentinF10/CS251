/**
 * Lab 3 Inheritance
 * Course : CS 251
 * Name: Quentin FAYE
 */
public class IceCream extends Dessert{

    private double price;
    /**
     * Constructs a new dessert item.
     */
    public IceCream(String name, double price) {
        super(name);
        this.price = price;
    }

    @Override
    public double getPrice() {
        return price;
    }
}
