import java.util.ArrayList;

public class Main
{
    public static void main(String[] args)
    {
        boolean enableNetworking = true;
        ArrayList <String> argList = new ArrayList <String>();

        for (String arg : args)
        {
            argList.add(arg);
        }
        
        if (args.length > 0)
        {
            if (argList.contains("-help") || (argList.contains("-h")))
            {
                // TODO
                // Account for a command being entered twice (such as "-offline" and "-of" in the same instance)
                System.out.println("Commands for Chemberry.Main:");
                System.out.println("-help , -h     displays this menu");
                System.out.println("-offline, -of  disables networking");
                System.out.println("-rpi           enables rpi resolution");
                System.out.println("-server        connect to a specific IP address");
            }

            if (argList.contains("-offline") || (argList.contains("-of")))
            {
                enableNetworking = false;
            }
        }
        
        // If the user does not want to display
        if (!(argList.contains("-help") || (argList.contains("-h"))))
        {
            GUIInterface gui = null;
            String addr = null;

            if ((argList.contains("-of")) || (argList.contains("-offline")))
            {
                addr = "127.0.0.1";
            }
            else
            {
                if ((argList.contains("-server")) || (argList.contains("-s")))
                {
                    addr = argList.get(argList.indexOf("-server") + 1);
                }
                else
                {
                    try
                    {
                        addr = Inet.getMyAddress();
                    }
                    catch(ChemberryException cbe)
                    {
                        cbe.printStackTrace();
                    }
                }
            }
                
            try
            {
                System.out.println("Creating the gui");
                gui = new GUI(enableNetworking, addr);
            }
            catch(ChemberryException cbe)
            {
                System.out.println("Could not resolve LAN address. Please check connection.");
                System.exit(0);
            }
            

            // TODO
            // Eventually, the instructor will multicast or broadcast themselves to the client.
            // This IP address will be used instead of localhost.
            // THEN, the try-catch might change.


            if (argList.contains("-rpi"))
            {
                gui.setScreenDimensions(800, 480);
            }

            if (enableNetworking)
            {
                GUIListeningProxy server = new GUIListeningProxy(9648, gui);
                Thread t = new Thread(server);
                t.start();
            }
        }

    }

    public static void processArgs(String[] args)
    {

    }
}