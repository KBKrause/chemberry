import java.net.*;
import java.io.*;

public abstract class ServerProxy implements Runnable
{
    private int myPort;

    public abstract void handleRequest(String request, String clientIP);

    public ServerProxy(int port)
    {
        myPort = port;
    }

    public int getPort()
    {
        return myPort;
    }

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