import java.util.Scanner; 

// TODO
// Add back in the InstructorSubject implementation.
// Have it only set up a connection when receiveUpdate() is called.
public class InstructorMain //implements InstructorSubject
{
    public static void main(String[] args)
    {
        System.out.println("Enter the IP address of the GUI");
        Scanner scan = new Scanner(System.in);
        String msg = scan.next();
        scan.close();

       InstructorProxy mySide = new InstructorProxy(6023);
       Thread t = new Thread(mySide);
       t.start();
    }
}