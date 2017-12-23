import java.util.HashSet;

public class InstructorProxy extends ServerProxy
{
    private HashSet <String> ipListing;
    private InstructorInterface instructor;

    public InstructorProxy(int p, InstructorInterface ins)
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
        //System.out.println("Received this as a request: " + request);
        String[] tokens = request.split(":");
        //System.out.println("INSProxy tokenized string array: " + tokens.toString());
        //System.out.println("Received " + tokens[0] + ":" + tokens[1]);

        if (tokens[0].equals("h"))
        {
            if (ipListing.contains(clientIP) == false)
            {
                System.out.println(clientIP + " has connected for the first time");
                ipListing.add(clientIP);
                updateGUI("h:" + clientIP + ":" + tokens[2]);
            }
            else
            {
                System.out.println("Client " + clientIP + " has attempted to initialize more than once");
            }
        }
        // "Update case"
        else if (tokens[0].equals("u"))
        {
            String updateRequest = Inet.decodeUpdate(tokens[1]);
            updateGUI("u:" + clientIP + ":" + updateRequest);
        
            //System.out.println(clientIP + " says: " + updateRequest);
        }
        else if (tokens[0].equals("d") && (tokens[1].equals("esync")))
        {
            updateGUI("d:" + clientIP);
            ipListing.remove(clientIP);
        }
    }

    public void updateGUI(String theUpdate)
    {
        try
        {
            instructor.receiveUpdate(theUpdate);
        }
        catch(ConnectionFailedException e)
        {
            // TODO
            // Handle this exception
            e.printStackTrace();
        }
    }
}