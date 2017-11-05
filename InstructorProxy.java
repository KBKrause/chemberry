import java.util.HashSet;

public class InstructorProxy extends ServerProxy
{
    private HashSet <String> ipListing;

    public InstructorProxy(int p)
    {
        super(p);

        ipListing = new HashSet<String>();
    }

    @Override
    public void handleRequest(String request, String clientIP)
    {
        // TODO
        // Lump together requests by client IP.
        if (ipListing.contains(clientIP) == false)
        {
            ipListing.add(clientIP);
        }
        // TODO
        // If it is an update, handle it - possibly using the receiveUpdate() functionality from InsSubject interface.
        request = Inet.decodeUpdate(request);
        
        System.out.println(clientIP + " says: " + request);
    }

    // TODO
    // Give this method the power to parse request tokens from the GUI.
    // For example: --u update, --n new set, --i new instrument, --c instrument configuration, etc.
}