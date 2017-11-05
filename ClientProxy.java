import java.net.*;
import java.util.ArrayList;
import java.io.*;

// TODO
// Rename to ClientConnection.
public class ClientProxy
{
    private String serverIP;
    private int serverPort;

    public ClientProxy(String IP, int port)
    {
        serverIP = IP;
        serverPort = port;
    }

    public void sendString(String data)
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

            System.out.println("Received response: " + responseFromServer);

            clientSocket.close();
        }
        catch(IOException e)
        {
            System.out.println("ERROR: Failed to connect to: " + serverIP + ":" + serverPort);
            e.printStackTrace();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void sendVector(ArrayList <Integer> vec)
    {
        try
        {

            String request = vec.toString();

            Socket clientSocket = new Socket(serverIP, serverPort);

            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));         

            System.out.println("CLIENT: Writing vector to the stream");
            outToServer.writeBytes(request + "\n");

            String responseFromServer = inFromServer.readLine();

            System.out.println("CLIENT: Received response: " + responseFromServer);

            clientSocket.close();
        }
        catch(ConnectException e)
        {
            e.printStackTrace();
        }
        catch(Exception e)
        {
            e.printStackTrace();
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