public class DisconnectedState extends ConnectionState
{
    private static DisconnectedState instance;

    private DisconnectedState() { }

    public static DisconnectedState getInstance()
    {
        if (instance == null)
        {
            instance = new DisconnectedState();
        }

        return instance;
    }

    public void success(String msg, ConnectionHandler connectionHandler)
    {
        connectionHandler.changeState(ConnectedState.getInstance());
        connectionHandler.flush();
    }

    public void error(String msg, ConnectionHandler connectionHandler)
    {
        connectionHandler.buffer(msg);
    }

    // TODO Decide what to do with the buffered contents, if there are any.
    public void close(ConnectionHandler connectionHandler)
    {

    }
}