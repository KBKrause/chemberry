import java.net.*;
import java.io.*;

// TODO
// If GUI never needs to receive anything, remove ServerProxy functionality from this class.
public class ProxyGUI implements InstructorSubject
{
    private int myPort;

    private ClientProxy connToIns;

    public ProxyGUI(int p, String insIP, int insPort)
    {
        myPort = p;

        // TODO
        // Perhaps instantiate AFTER we know the instructor is set up.
        connToIns = new ClientProxy(insIP, insPort);
    }

    @Override
    public void receiveUpdate(String update) throws ConnectionFailedException
    {
       if (connToIns.sendString(update) == false)
       {
           throw new ConnectionFailedException();
       }
    }
}