public class ProfessorMain
{
    public static void main(String[] args)
    {
        try
        {
            ProfessorGUI gui = new ProfessorGUI();
                   
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