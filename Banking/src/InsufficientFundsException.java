import java.util.Collection;
import java.util.TreeSet;

/**
 * Name: Quentin FAYE
 * Course : CS 251
 * Lab 04 : Exceptions
 */
public class InsufficientFundsException extends Exception {
    //amount of the shortfall variable
    private double shortfall;




    public InsufficientFundsException(double shortfall) {
        //see the message in the stacktrace
        super("You need more money!");
        this.shortfall = shortfall;
    }

    /**
     * access the shortfall amount given in the constructor
     * @return shortfall
     */
    public double getShortfall() {

        return shortfall;
    }
}

