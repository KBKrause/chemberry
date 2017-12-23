import java.net.*;
import java.util.ArrayList;
import java.io.*;

public class ClientConnection
{
    private String serverIP;
    private int serverPort;

    public ClientConnection(String IP, int port)
    {
        serverIP = IP;
        serverPort = port;
    }

    // TODO
    // In the future, the boolean return value should be hidden from users.
    public boolean sendString(String data)
    {
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
            String responseFromServer = inFromServer.readLine();

            //System.out.println("Received response: " + responseFromServer);

            clientSocket.close();

            return true;
        }
        catch(IOException e)
        {
            // The sockets could not connect.
            return false;
        }
    }

    public String getServerIP()
    {
        return serverIP;
    }

    public int getServerPort()
    {
        return serverPort;
    }
}