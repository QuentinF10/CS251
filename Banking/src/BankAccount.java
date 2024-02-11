/**
 * Name: Quentin FAYE
 * Course : CS 251
 * Lab 04 : Exceptions
 */
public class BankAccount {
    //account number variable
    private int accountNumber;
    //balance variable
    private double balance;

    public BankAccount(int accountNumber) {
        this.accountNumber = accountNumber;

        //initially balance of 0$
        this.balance = 0.0;
    }

    /**
     * method to get account number
     * @return int account number
     */
    public int getAccountNumber() {

        return accountNumber;
    }

    /**
     * method to get balance
     * @return double balance
     */
    public double getBalance() {

        return balance;
    }

    /**
     * method to make a deposit
     * @param amount
     */
    public void deposit(double amount) {
        //if the amount is negative throw an exception
        if (amount < 0) {
            throw new IllegalArgumentException("Attempted to deposit a negative amount : " + amount);
        }
        //add amount to the balance value
        balance += amount;
    }

    /**
     * method to withdraw money
     */
    public void withdraw(double amount) throws InsufficientFundsException {
       //if I try to withdraw a negative amount, throw an exception
        if (amount < 0) {
            throw new IllegalArgumentException("Cannot withdraw a negative amount.");
        }
        //if I try to withdraw an amount higher than my balance, throw an exception
        if (amount > balance) {
            throw new InsufficientFundsException(amount - balance);
        }
        //reduce the amount from the balance
        balance -= amount;
    }
}
