public class InstructorMain
{
    public static void main(String[] args)
    {
        try
        {
            InstructorGUI gui = new InstructorGUI();
                   
            InstructorProxy server = new InstructorProxy(6023, gui);
            Thread t = new Thread(server);
            t.start();
        }
        catch(ChemberryException cbe)
        {
            cbe.printMessage();
        }
    }
}