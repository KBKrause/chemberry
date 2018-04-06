/**
 * All exceptions specific to Chemberry inherit from <code>ChemberryException</code>.
 * 
 * @author      KBKrause
 * @see         Exception
 * @since       1.8
 */
public class ChemberryException extends Exception
{ 
     /** 
     * This constructor calls the default constructor of <code>Exception</code>.
     * 
     * @see             Exception#Exception()
     * @since           1.8
     */
    public ChemberryException()
    {
        super();
    }
    
     /** 
     * This constructor calls the constructor of <code>Exception</code> with a message.
     * 
     * @param message   the message or detail associated with this exception
     * @see             Exception#Exception(String)
     * @since           1.8
     */
    public ChemberryException(String message)
    {
        super(message);
    }

     /** 
     * Prints to standard out the message associated with this exception.
     * 
     * @see             Exception#getMessage()
     * @since           1.8
     */
    public void printMessage()
    {
        System.out.println(this.getMessage());
    }
}