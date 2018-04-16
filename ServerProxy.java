import java.net.*;
import java.io.*;

/**
 * <code>ServerProxy</code> is a base class used for opening and listening on server sockets. It provides the method {@link #handleRequest(String, String)}
 * so that concrete instantiations of the class can react to data differently.
 * 
 * @author      KBKrause
 * @since       1.8
 */
public abstract class ServerProxy implements Runnable
{
    private int myPort;

    /**
     * Provides a means for child classes of ServerProxy to react differently to client requests while still maintaining basic server socket
     * functionality.
     * 
     * @param request the request forwarded by the client, tokenized by colon :
     * @param clientIP the IP of the client who sent this request
     * @since       1.8
     */
    public abstract void handleRequest(String request, String clientIP);

    /**
     * Creates a ServerProxy with the specified port. The user needs to be aware of which port they have assigned to this object, as it cannot be used
     * by any other ServerProxy or application. The constructor does not begin listening and accepting requests; to do this, {@link #run()} must be called.
     * TODO
     * Provide a means of preventing the problem specified above.
     * 
     * @since       1.8
     */
    public ServerProxy(int port)
    {
        myPort = port;
    }

    /**
     * Returns the port number of this server socket.
     * TODO
     * The port can be an unsigned short instead of an int.
     * 
     * @return the listening port of this server socket
     * @since       1.8
     */
    public int getPort()
    {
        return myPort;
    }

    /**
     * This is the overridden method for {@link Runnable#run()}. The server will indefinitely wait and accept connections to this machine's IP and designated port.
     * Once a request is received, as a <code>String</code>, it is handled by {@link #handleRequest(String, String)}. Afterwards, the socket is closed
     * and not kept open. This thread will run indefinitely and must be terminated or killed by another thread.
     * This method is expected to work in unison with {@link ClientConnection#sendString(String)}.
     * 
     * @see         ClientConnection
     * @see         Runnable
     * @since       1.8
     */
    @Override
    public void run()
    {
        try
        {
            System.out.println("I am now waiting for requests on " + myPort);

            // Create a server socket using this proxy's port.
            // The server socket waits for requests instead of sending them.
            ServerSocket welcomeSocket = new ServerSocket(myPort);

            while (true)
            {
                // accept() blocks until a connection is made between this and a client.
                Socket serverSocket = welcomeSocket.accept();

                DataOutputStream outToClient = new DataOutputStream(serverSocket.getOutputStream());
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));

                String requestFromClient = inFromClient.readLine();

                // TODO
                // Is this the true address? serverSocket.getInetAdress().getHostAddress()
                handleRequest(requestFromClient, serverSocket.getInetAddress().getHostAddress());

                // Tell the client we have received their message. The ClientConnection is expecting this acknowledgment.
                outToClient.writeBytes("This packet smelled nice, thanks" + '\n');
                serverSocket.close();
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}