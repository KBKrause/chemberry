// This ChemberryException is thrown when the client cannot reach or receive a packet from the instructor.
public class ConnectionFailedException extends ChemberryException
{
    public ConnectionFailedException()
    {
        super();
    }
    
    public ConnectionFailedException(String message)
    {
        super(message);
    }
}