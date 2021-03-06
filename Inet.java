import java.net.*;

public abstract class Inet 
{
    public static String getMyAddress() throws ChemberryException
    {
        String addr = "null";

        try 
        {
            addr = InetAddress.getLocalHost().getHostAddress();
        } 
        catch (UnknownHostException uhe) 
        {
            System.out.println("Unknown Host Exception thrown!!!");
            uhe.printStackTrace();
        }

        if (addr.equals("127.0.0.1"))
        {
            throw new ChemberryException("Unable to resolve LAN address - check connection");
        }

        return addr;
    }

    // TODO
    // The only differences between these methods is the fixedTextChar[i].
    // Find a way to condense into a single function.
    public static String encodeUpdate(String text) 
    {
        char[] fixedTextChar = text.toCharArray();

        for (int i = 0; i < fixedTextChar.length; i++) 
        {
            if (fixedTextChar[i] == '\n') 
            {
                fixedTextChar[i] = '`';
            }
        }

        return String.valueOf(fixedTextChar);
    }

    public static String decodeUpdate(String text)
    {
        char[] fixedTextChar = text.toCharArray();
        
        for (int i = 0; i < fixedTextChar.length; i++) 
        {
            if (fixedTextChar[i] == '`') 
            {
               fixedTextChar[i] = '\n';
            }
        }
        
        return String.valueOf(fixedTextChar);
    }

    public static String hash(String text)
    {
        return "";
    }

    public static String retrieve(String text)
    {
        return "";
    }
}