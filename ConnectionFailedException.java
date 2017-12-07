// This ChemberryException is thrown when the client cannot reach or receive a packet from the instructor.
public class ConnectionFailedException extends ChemberryException
{
    private String remoteIP;
    private int remotePort;

    public ConnectionFailedException(String ip, int port)
    {
        super();

        remoteIP = ip;
        remotePort = port;
    }

    public String getRemoteIP()
    {
        return remoteIP;
    }

    public int getRemotePort()
    {
        return remotePort;
    }
}