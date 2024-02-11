import java.util.*;

/**
 * Lab 05: Postfix Calculator
 * Course: CS 251
 * Name: Quentin FAYE
 */
public class PostfixCalc {

    /** Stack to store operands in the postfix expression. */
    private Stack<Double> operands;

    /** Map to associate string representations of operators to their respective implementation. */
    private Map<String, Operator> operatorMap;

    /**
     * Constructs a new PostfixCalc with initialized operator implementations.
     */
    public PostfixCalc() {
        operands = new DoubleStack();
        operatorMap = new HashMap<>();

        // Initialize the operator map
        operatorMap.put("+", new AdditionOperator());
        operatorMap.put("add", new AdditionOperator());
        operatorMap.put("-", new SubtractionOperator());
        operatorMap.put("sub", new SubtractionOperator());
        operatorMap.put("*", new MultiplicationOperator());
        operatorMap.put("mult", new MultiplicationOperator());
        operatorMap.put("/", new DivisionOperator());
        operatorMap.put("div", new DivisionOperator());
        operatorMap.put("=", new PrintOperator());
        operatorMap.put("print", new PrintOperator());
    }

    /**
     * Store a operand in the calculator.
     *
     * @param value The operand to store.
     */
    public void storeOperand(double value) {
        operands.push(value);
    }

    /**
     * Evaluates the given operator with appropriate operands.
     *
     * @param operator The string representation of the operator.
     * @throws IllegalArgumentException if the provided operator is unknown.
     */
    public void evalOperator(String operator) {
        Operator op = operatorMap.get(operator);
        if(op == null) {
            throw new IllegalArgumentException("Unknown operator: " + operator);
        }

        List<Double> args = new ArrayList<>();
        for(int i = 0; i < op.numArgs(); i++) {
            args.add(operands.pop());
        }

        // Reverse the args since they were popped off the stack
        Collections.reverse(args);

        double result = op.eval(args);
        operands.push(result);
    }

    // Nested operator classes

    /**
     * Nested class for the addition operation.
     */
    private class AdditionOperator implements Operator {
        public int numArgs() { return 2; }
        public double eval(List<Double> args) { return args.get(0) + args.get(1); }
    }

    /**
     * Nested class for the subtraction operation.
     */
    private class SubtractionOperator implements Operator {
        public int numArgs() { return 2; }
        public double eval(List<Double> args) { return args.get(0) - args.get(1); }
    }

    /**
     * Nested class for the multiplication operation.
     */
    private class MultiplicationOperator implements Operator {
        public int numArgs() { return 2; }
        public double eval(List<Double> args) { return args.get(0) * args.get(1); }
    }

    /**
     * Nested class for the division operation.
     */
    private class DivisionOperator implements Operator {
        public int numArgs() { return 2; }
        public double eval(List<Double> args) { return args.get(0) / args.get(1); }
    }

    /**
     * Nested class for the print operation.
     */
    private class PrintOperator implements Operator {
        public int numArgs() { return 1; }
        public double eval(List<Double> args) {
            System.out.println(args.get(0));
            return args.get(0);
        }
    }
}
