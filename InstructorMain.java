public class InstructorMain
{
    public static void main(String[] args)
    {
       InstructorGUI gui = new InstructorGUI();

        // TODO
        // Have InstructorProxy implement the GUI subject. (?)
        InstructorProxy server = new InstructorProxy(6023, gui);
        Thread t = new Thread(server);
        t.start();
    }
}