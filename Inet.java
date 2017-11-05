import java.net.*;

public abstract class Inet 
{
    public static String getMyAddress() 
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
}