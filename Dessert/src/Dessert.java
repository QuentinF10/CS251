/** Base class for Dessert types. */
public abstract class Dessert {

    /** Name of the dessert item.
     *
     * Note that the protected access modifier allows derived classes
     * to read this member variable, while the final modifier prevents
     * it from being reassigned after it is initialized in the dessert
     * constructor.
     */
    protected final String name;

    /**
     * Constructs a new dessert item.
     * @param name Name of the dessert.
     */
    public Dessert(String name) {
        this.name = name;
    }

    /**
     * Get name of the dessert.
     * @return dessert name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the price of the dessert.
     * @return Dessert price
     */
    public abstract double getPrice();
}
