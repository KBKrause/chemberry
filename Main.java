import java.util.ArrayList;
import java.util.Scanner; 

public class Main
{
    public static void main(String[] args)
    {
        System.out.println("I'm wide awake");

        listen();

        ArrayList <Integer> myVec = new ArrayList <Integer>();
        myVec.add(5);
        myVec.add(3);
        myVec.add(2);

        ClientProxy connection = new ClientProxy("127.0.0.1", 5001);
        connection.sendVector(myVec);
        System.out.println("Sent the vector");
    }

    static void listen()
    {
        Thread t = new Thread(new ServerProxy(5001));
        t.start();
    }

    static void sendString()
    {
        Scanner scan = new Scanner(System.in);
        String msg = scan.next();
        ClientProxy pxy = new ClientProxy("127.0.0.1", 5001);
        pxy.sendString(msg);
        scan.close();
    }
}