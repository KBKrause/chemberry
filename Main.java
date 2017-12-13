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
                System.out.println("Commands for Chemberry.Main:");
                System.out.println("-help , -h     displays this menu");
                System.out.println("-offline, -of  disables networking");
            }

            if (argList.contains("-offline") || (argList.contains("-of")))
            {
                enableNetworking = false;
            }
        }
        
        if (!(argList.contains("-help") || (argList.contains("-h"))))
        {
            GUIInterface gui = new GUI(enableNetworking);

            if (enableNetworking)
            {
                GUIListeningProxy server = new GUIListeningProxy(9648, gui);
                Thread t = new Thread(server);
                t.start();
            }
        }

    }
}