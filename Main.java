public class Main
{
    public static void main(String[] args)
    {
        System.out.println("I'm wide awake");

        GUI gui = new GUI();

        GUIProxy proxy = new GUIProxy(8314, Inet.getMyAddress(), 6023);
        gui.attach(proxy);
        try
        {
            proxy.update("h:ello");
        }
        catch(ConnectionFailedException ex)
        {
            ex.printStackTrace();
        }
        
    }
}