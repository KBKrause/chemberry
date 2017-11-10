import java.util.HashSet;

public class InstructorProxy extends ServerProxy
{
    private HashSet <String> ipListing;
    private InstructorSubject instructor;

    public InstructorProxy(int p, InstructorSubject ins)
    {
        super(p);

        // TODO
        // Assert not null and all that.
        // Why not do that for the rest of these classes' constructors?
        instructor = ins;

        ipListing = new HashSet<String>();
    }

    // TODO
    // Push tokenizing functionality into Inet class or other static abstract class.
    @Override
    public void handleRequest(String request, String clientIP)
    {
        System.out.println("Received this as a request: " + request);
        String[] tokens = request.split(":");
        //System.out.println("INSProxy tokenized string array: " + tokens.toString());
        System.out.println("Received " + tokens[0] + ":" + tokens[1]);

        if ((tokens[0].equals("h")) && (tokens[1].equals("ello")))
        {
            if (ipListing.contains(clientIP) == false)
            {
                System.out.println(clientIP + " has connected for the first time");
                ipListing.add(clientIP);
                updateGUI("h:" + clientIP);
            }
            else
            {
                System.out.println("Client " + clientIP + " has attempted to initialize more than once");
            }
        }
        // "Update case"
        else if (tokens[0].equals("u"))
        {
            // TODO
            // If it is an update, handle it - possibly using the receiveUpdate() functionality from InsSubject interface.
            // TODO
            // Lump together requests by client IP.
            // TODO
            // I think the instructor is not correctly decoding the updates.
            String updateRequest = Inet.decodeUpdate(tokens[1]);
            updateGUI("u:" + clientIP + ":" + updateRequest);
        
            System.out.println(clientIP + " says: " + updateRequest);
        }
    }

    public void updateGUI(String theUpdate)
    {
        instructor.receiveUpdate(theUpdate);
    }

    // TODO
    // Give this method the power to parse request tokens from the GUI.
    // For example: --u update, --n new set, --i new instrument, --c instrument configuration, etc.
}