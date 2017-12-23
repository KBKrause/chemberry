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
        String tokens[] = request.split(":");
        System.out.println(tokens);

        if (tokens[0].equals("d") && (tokens[1].equals("esync")))
        {
            gui.appendDebugText("WARNING: The remote server has disconnected");
            //gui.setNetworking(false);
        }
    }
}