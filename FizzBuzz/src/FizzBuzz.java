/**
 * Name : Quentin FAYE
 * Class : CS251
 * Lab01 :  FizzBuzz
 */
public class FizzBuzz {

    public void methodA () {
        System . out . println ( " A " );
    }
    public void methodB () {
        System . out . println ( " B " );
    }
    //public static void main(String[] args) {


        /**String test = "abcd";
        System.out.println(""+ test.length());
        // verify if arguments are integers
        for (String arg : args) {
            if (!robustCode(arg)) {
                System.out.println("Arguments must be Integer ");
            }
        }
        //3 arguments needed, else error
        if (args.length != 3) {
            System.err.println("Expected 3 arguments, but got " + args.length);
        } else {
            //defining my 3 variables taken from the command lines arguments
            int firstArg = Integer.parseInt(args[0]);
            int secondArg = Integer.parseInt(args[1]);
            int thirdArg = Integer.parseInt(args[2]);

            //loop checking if the number i is multiple of one argument or both
            for (int i = 1; i <= thirdArg; i++) {
                if (i % firstArg == 0 && i % secondArg != 0) {
                    System.out.println("Fizz");
                } else if (i % secondArg == 0 && i % firstArg != 0) {
                    System.out.println("Buzz");
                } else if (i % firstArg == 0 && i % secondArg == 0) {
                    System.out.println("FizzBuzz");
                } else {
                    System.out.println(i);
                }
            }
        }
    }*/

    /**function to check if arguments are integers
    public static boolean robustCode(String arg) {
        try {
            Integer.parseInt(arg);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }*/
}
