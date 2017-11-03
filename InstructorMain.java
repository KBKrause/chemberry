import java.util.Scanner; 

public class InstructorMain
{
    public static void main(String[] args)
    {
        System.out.println("Enter the IP address of the GUI");
        Scanner scan = new Scanner(System.in);
        String msg = scan.next();
        scan.close();

        ClientProxy cp = new ClientProxy(msg, 8314);
        cp.sendString("Here is some text");
    }
}