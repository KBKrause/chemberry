// This is used by InstructorProxy to prevent it from accessing any of the JFrame methods that InstructorGUI inherited.
public interface InstructorInterface
{
    public void receiveUpdate(String update) throws ConnectionFailedException;
}