/**
 * <code>GUIInterface</code> is implemented by <code>GUI</code> so that other classes may interact and display text to the screen. This helps to separate the logic
 * of the GUI from the logic of any proxies involved.
 * 
 * @author      kevbkraus
 * @see         GUI
 * @since       1.8
 */
public interface GUIInterface
{
    // Change this to "update" or something like that.
    public void setNetworking(boolean b);
    // Set the dimensions of the screen. Everything will adjust.
    public void setScreenDimensions(int height, int width);
    public void appendDebugText(String s);
}