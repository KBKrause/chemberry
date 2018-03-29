public class StudentGUIDriver
{
    public static void main(String[] args)
    {
        try
        {
            StudentGUI gui = new StudentGUI(true, Inet.getMyAddress());            
        }
        catch(Exception e)
        {
        }
    }
}