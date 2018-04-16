import java.net.*;
import java.io.*;

/**
 * A GUIProxy can be used in place the instructor for data transmission. It implements the InstructorInterface as a commonality between
 * it and an actual ProfessorGUI object.
 * 
 * @author      KBKrause
 * @see         InstructorInterface
 * @since       1.8
 */
public class GUIProxy implements InstructorInterface
{
    private int myPort;

    private ClientConnection connToIns;

    /**
     * Creates a proxy for the instructor to be used by a client program.
     * Because the server needs to create a connection to the client, a listening port number must also be provided.
     * 
     * @author      KBKrause
     * @param p the listening port number of this machine - to be used in conjunction with GUIListeningProxy
     * @param insIP the IPv4 address of the server
     * @param insPort the listening port of the server
     * @see GUIListeningProxy
     * @since       1.8
     */
    public GUIProxy(int p, String insIP, int insPort)
    {
        myPort = p;

        connToIns = new ClientConnection(insIP, insPort);
    }

    /**
     * Attempts to send an update to the server using the IP and port provided to the constructor.
     * 
     * @author      KBKrause
     * @param update the tokenized update to be sent to the server
     * @exception ConnectionFailedException if the update fails to reach the server
     * @since       1.8
     */
    @Override
    public void receiveUpdate(String update) throws ConnectionFailedException
    {
       if (connToIns.sendString(update) == false)
       {
           throw new ConnectionFailedException("Unable to connect to instructor");
       }
    }
}