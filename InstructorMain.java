import java.util.Scanner; 

// TODO
// Add back in the InstructorSubject implementation.
// Have it only set up a connection when receiveUpdate() is called.
public class InstructorMain //implements InstructorSubject
{
    public static void main(String[] args)
    {
       InstructorGUI gui = new InstructorGUI();

        // TODO
        // Have InstructorProxy implement the GUI subject.
        InstructorProxy server = new InstructorProxy(6023, gui);
        Thread t = new Thread(server);
        t.start();
    }
}