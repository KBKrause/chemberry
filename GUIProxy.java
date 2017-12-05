import java.net.*;
import java.io.*;

public class GUIProxy implements InstructorObserver
{
    private int myPort;

    private ClientProxy connToIns;

    public GUIProxy(int p, String insIP, int insPort)
    {
        myPort = p;

        connToIns = new ClientProxy(insIP, insPort);
    }

    @Override
    public void update(String theUpdate) throws ConnectionFailedException
    {
        if (connToIns.sendString(theUpdate) == false)
        {
            throw new ConnectionFailedException(connToIns.getServerIP(), connToIns.getServerPort());
        }
    }
}