import java.net.*;
import java.util.ArrayList;
import java.io.*;

/**
 * A ClientConnection is an abstract representation of a TCP client socket that sends and receives data from a server.
 * This class provides very basic functionality to communicate with a <code>ServerProxy</code>.
 * Changing the destination address of the server would require a new instantation of this object.
 * 
 * @author      kevbkraus
 * @see         ServerProxy
 * @since       1.8
 */
public class ClientConnection
{
    private String serverIP;
    private int serverPort;

    /**
     * The constructor for this class requires the IPv4 address and listening port number of a receiving server machine.
     * 
     * @param IP    the IPv4 address of the server, eg. "127.0.0.1"
     * @param port  the port being listened to on the server
     * @since       1.8
     */
    public ClientConnection(String IP, int port)
    {
        serverIP = IP;
        serverPort = port;
    }

    /**
     * Attempts to send a String to the server. The String is written as a sequence of bytes via {@link DataOutputStream#writeBytes(String)}, thus not
     * requiring a newline terminator. Returns <code>true</code> if the server successfully received the String and sent an acknowledgement
     * back to this socket.
     * 
     * @param data  the String being sent to the server
     * @return      <code>true</code> if the server received the String, <code>false</code> if otherwise
     * @since       1.8
     */
    public boolean sendString(String data)
    {
        boolean retval = true;

        try
        {
            String request = data;

            // Create a socket using the serverIP configured to this client's proxy.
            Socket clientSocket = new Socket(serverIP, serverPort);

            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));      

            // writeBytes() takes the string and a newline character to signify when there are no more characters coming.
            outToServer.writeBytes(request + "\n");

            // readLine() is a blocking call that is waiting for an ack from the server.
            inFromServer.readLine();

            //System.out.println("Received response: " + responseFromServer);

            clientSocket.close();
        }
        catch(IOException e)
        {
            // The sockets could not connect.
            retval = false;
        }
            return retval;
    }

    /**
     * Returns the IPv4 address of the server associated with this ClientSocket.
     * 
     * @return      String representation of the server IP
     * @since       1.8
     */
    public String getServerIP()
    {
        return serverIP;
    }

    /**
     * Returns the listening port number of the server associated with this ClientSocket.
     * 
     * @return      port number
     * @since       1.8
     */
    public int getServerPort()
    {
        return serverPort;
    }
}