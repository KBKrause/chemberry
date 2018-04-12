/**
 * This exception is thrown when a message cannot reach the instructor or student.
 * 
 * 
 * @author      KBKrause
 * @see         ChemberryException
 * @since       1.8
 */
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