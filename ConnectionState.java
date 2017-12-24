public abstract class ConnectionState
{
    public abstract void success(String msg, ConnectionHandler connectionHandler);
    public abstract void error(String msg, ConnectionHandler connectionHandler);
    public abstract void close(ConnectionHandler connectionHandler);
}