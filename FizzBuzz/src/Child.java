public class Child extends FizzBuzz {
    public void methodA () { }
    public void methodB ( int n ) { }
    public void methodC () { }
    public static void main ( String [] args ) {
        FizzBuzz p = new FizzBuzz ();
        FizzBuzz c1 = new Child ();
        Child c2 = new Child ();
// What works here ?
       
    }
}