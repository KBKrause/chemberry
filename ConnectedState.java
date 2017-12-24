public class ConnectedState extends ConnectionState
{
    private static ConnectedState instance;
    
    private ConnectedState() { }
    
    public static ConnectedState getInstance()
    {
        if (instance == null)
        {
            instance = new ConnectedState();
        }
    
        return instance;
    }

    // TODO For now, when there is a success, do nothing.
    public void success(String msg, ConnectionHandler connectionHandler) { }

    public void error(String msg, ConnectionHandler connectionHandler)
    {
        connectionHandler.changeState(DisconnectedState.getInstance());
        connectionHandler.buffer(msg);
    }

    // TODO For now, do nothing if there is a close.
    public void close(ConnectionHandler connectionHandler) { }
}