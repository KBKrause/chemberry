public class ChemberryException extends Exception
{ 
    public ChemberryException()
    {
        super();
    }
    
    public ChemberryException(String message)
    {
        super(message);
    }

    public void printMessage()
    {
        System.out.println(this.getMessage());
    }
}