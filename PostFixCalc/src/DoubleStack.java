import java.util.EmptyStackException;
import java.util.LinkedList;

/**
 * Lab 05: Postfix Calculator
 * Course: CS 251
 * Name: Quentin FAYE
 */
public class DoubleStack implements Stack<Double> {


    private LinkedList<Double> stackList;

    /**
     * Constructs an empty DoubleStack.
     */
    public DoubleStack() {
        stackList = new LinkedList<>();
    }

    /**
     * Checks if the stack is empty.
     *
     * @return true if the stack is empty, false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return stackList.isEmpty();
    }

    /**
     * Pushes a value onto the top of this stack.
     *
     * @param val the value to be pushed onto this stack.
     */
    @Override
    public void push(Double val) {
        stackList.addFirst(val);
    }

    /**
     * Removes and returns the value at the top of this stack.
     *
     * @return the value at the top of this stack.
     * @throws EmptyStackException if this stack is empty.
     */
    @Override
    public Double pop() {
        if(isEmpty()) {
            throw new EmptyStackException();
        }
        return stackList.removeFirst();
    }

    /**
     * Looks at the value at the top of this stack without removing it.
     *
     * @return the value at the top of this stack.
     * @throws EmptyStackException if this stack is empty.
     */
    @Override
    public Double peek() {
        if(isEmpty()) {
            throw new EmptyStackException();
        }
        return stackList.getFirst();
    }
}
