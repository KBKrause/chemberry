public class StudentGUIDriver
{
    public static void main(String[] args)
    {
        boolean enableNetworking = true;

        try
        {
            StudentGUI gui = new StudentGUI(enableNetworking, Inet.getMyAddress());    

            if (enableNetworking)
            {
                GUIListeningProxy server = new GUIListeningProxy(9648, gui);
                Thread t = new Thread(server);
                t.start();
            }        
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}