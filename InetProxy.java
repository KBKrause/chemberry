import java.net.*;

public abstract class InetProxy
{
    public static String getMyAddress()
    {
        String addr = "null";

        try
        {
            addr = InetAddress.getLocalHost().getHostAddress();
        }
        catch(UnknownHostException uhe)
        {
            System.out.println("Unknown Host Exception thrown!!!");
            uhe.printStackTrace();
        }

        return addr;
    }
}