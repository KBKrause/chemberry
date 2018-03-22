public class GUIListeningProxy extends ServerProxy
{
    private GUIInterface gui;

    public GUIListeningProxy(int p, GUIInterface g)
    {
        super(p);
        gui = g;
    }

    @Override
    public void handleRequest(String request, String clientIP)
    {
        System.out.println("Received this request: " + request);
        String decodedRequest = Inet.decodeUpdate(request);

        String tokens[] = decodedRequest.split(":");
        //System.out.println(tokens);

        if (tokens[0].equals("d") && (tokens[1].equals("esync")))
        {
            gui.appendDebugText("WARNING: The remote server has disconnected");
            //gui.setNetworking(false);
        }
        else if (tokens[0].equals("u"))
        {
            System.out.println("Instructor says: " + tokens[1]);
        }
        else if (tokens[0].equals("bp"))
        {
            System.out.println("Received this broadcasted procedure: " + tokens[1]);
            gui.setProcedure(tokens[1]);
        }
    }
}