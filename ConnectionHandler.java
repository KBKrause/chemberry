import java.util.ArrayList;

public class ConnectionHandler
{
    private ArrayList < String > updateBuffer;

    private ConnectionState currentState;

    public ConnectionHandler(boolean isInitiallyConnected) 
    {
        updateBuffer = new ArrayList < String >();

        if (isInitiallyConnected)
        {
            changeState(ConnectedState.getInstance());
        }
        else
        {
            changeState(DisconnectedState.getInstance());
        }
    }

    // TODO if msg is to disconnect, handle that as the close method
    public void handle(String msg, boolean success)
    {
        if (success)
        {
            currentState.success(msg, this);
        }
        else
        {
            currentState.error(msg, this);
        }
    }

    public void changeState(ConnectionState newState)
    {
        currentState = newState;
    }

    public void buffer(String msg)
    {
        updateBuffer.add(msg);
    }

    // Send everything in the buffer to the server.
    public void flush()
    {
        
    }
}