import java.util.Scanner;

/**
 * Lab 05: Postfix Calculator
 * Course: CS 251
 * Name: Quentin FAYE
 */
public class CalcTest {

    /**
     * Reads and evaluates postfix expressions from standard input.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        // Create a scanner from standard input
        Scanner scanner = new Scanner(System.in);

        // Make a new calculator
        PostfixCalc calc = new PostfixCalc();

        // Keep reading tokens until we run out.
        while(scanner.hasNext()) {
            if(scanner.hasNextDouble()) {
                // Next item is a double, so must be an operand
                calc.storeOperand(scanner.nextDouble());
            } else {
                // Otherwise, we have an operator
                calc.evalOperator(scanner.next());
            }
        }
    }
}
