import java.net.*;
import java.io.*;

public class ProxyGUI implements Runnable, InstructorSubject
{
    private int myPort;

    private ClientProxy connToIns;

    public ProxyGUI(int p, String insIP, int insPort)
    {
        myPort = p;

        connToIns = new ClientProxy(insIP, insPort);
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
                // accept() blocks until a connection is made between this and ClientProxy.
                Socket serverSocket = welcomeSocket.accept();

                DataOutputStream outToClient = new DataOutputStream(serverSocket.getOutputStream());
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));

                System.out.println("SERVER: Waiting for something to arrive");
                String requestFromClient = inFromClient.readLine();

                // Parse the request to determine what they want.

                System.out.println("SERVER: Client says: " + requestFromClient);

                // Tell the client we have received their message. The ClientProxy is expecting this acknowledgment.
                outToClient.writeBytes("This packet smelled nice, thanks" + '\n');
                serverSocket.close();
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void receiveUpdate(String update)
    {
       connToIns.sendString(update);
    }
}