/**
 * This file tests your BankAccount implementation to make sure it
 * throws an exception when it should.
 */
public class BankingTest {

    /**
     * Print account number and balance for a given bank account.
     * @param b The account of interest.
     */
    private static void printBalance(BankAccount b) {
        // Displaying only 2 places after decimal, since it's dollars & cents
        System.out.printf("Account %d balance = $%.2f%n",
                b.getAccountNumber(), b.getBalance());
    }

    /**
     * Takes the initial deposit amount from command line and plays with a bank account.
     */
    public static void main(String[] args) throws InsufficientFundsException {

        double depositAmount = 50.00;
        if(args.length > 0) {
            try {
                depositAmount = Double.parseDouble(args[0]);
            } catch (NumberFormatException ex) {
                // argument wasn't valid number, so stick with original value.
                System.out.println("Ignoring invalid argument: " +  args[0]);
            }
        }

        BankAccount b1 = new BankAccount(101);

        // Put some money in there!
        b1.deposit(depositAmount);

        printBalance(b1);


        // Make a second account to test for inappropriate sharing
        final double testAmount = 100;
        BankAccount b2 = new BankAccount(42);
        b2.deposit(testAmount);

        if(b1.getAccountNumber() != 101 || b2.getAccountNumber() != 42) {
            System.out.println("Something isn't right with the account numbers!");
        }

        try {
            // Late night infomercial is selling widgets for 3 easy
            // payments of $19.95, what a deal! Let's take out money to buy one.
            double amount = 19.95;
            for(int i = 1; i<=3; ++i) {
                System.out.println("Withdraw payment " + i + " of $" + amount);
                b1.withdraw(amount);
                printBalance(b1);
            }
            System.out.println("Finished payments");
            printBalance(b1);
        } catch (InsufficientFundsException ex) {
            // get shortfall amount from exception and print message
            System.out.printf("Sorry, but you are short $%.2f%n",
                    ex.getShortfall());

            // Print exception's message and stack trace on standard error.
            ex.printStackTrace();
        } finally {
            System.out.println("Done banking for now");
            printBalance(b1);
        }

        // Make sure messing with b1 didn't change b2 balance.
        // This shouldn't happen if you implemented BankAccount properly.
        if(b2.getBalance() != testAmount) {
            System.out.println("Unexpected change in second account!");
            printBalance(b2);
        }

        // Make sure we throw exception if we try to deposit negative amount
        double balanceBeforeDeposit = b1.getBalance();
        boolean exceptionForDeposit = false;

        try {
            b1.deposit(-12.3);
        } catch (IllegalArgumentException ex) {
            exceptionForDeposit = true;
        }

        if(!exceptionForDeposit) {
            System.out.println("Negative deposit did not throw exception!");
        }
        if(b1.getBalance() != balanceBeforeDeposit) {
            System.out.println("Balance changed after negative deposit!");
            printBalance(b1);
        }

        // Make sure we throw exception if we try to withdraw negative amount
        double balanceBeforeWithdraw = b1.getBalance();
        boolean exceptionForWithdraw = false;

        try {
            b1.withdraw(-32.1);
        } catch (IllegalArgumentException ex) {
            exceptionForWithdraw = true;
        } catch (InsufficientFundsException ex) {
            System.out.println("Wrong exception thrown for negative withdraw!");
            ex.printStackTrace();
        }

        if(!exceptionForWithdraw) {
            System.out.println("Negative withdraw did not throw exception!");
        }
        if(b1.getBalance() != balanceBeforeWithdraw) {
            System.out.println("Balance changed after negative withdraw!");
            printBalance(b1);
        }

    }
}
