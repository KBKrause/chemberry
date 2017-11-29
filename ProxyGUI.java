import java.net.*;
import java.io.*;

public class ProxyGUI implements InstructorSubject
{
    private int myPort;

    private ClientProxy connToIns;

    public ProxyGUI(int p, String insIP, int insPort)
    {
        myPort = p;

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