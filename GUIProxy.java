import java.net.*;
import java.io.*;

public class GUIProxy implements InstructorInterface
{
    private int myPort;

    private ClientConnection connToIns;

    public GUIProxy(int p, String insIP, int insPort)
    {
        myPort = p;

        connToIns = new ClientConnection(insIP, insPort);
    }

    @Override
    public void receiveUpdate(String update) throws ConnectionFailedException
    {
       if (connToIns.sendString(update) == false)
       {
           throw new ConnectionFailedException("Unable to connect to instructor");
       }
    }
}