public interface GUIInterface
{
    // Change this to "update" or something like that.
    public void setNetworking(boolean b);
    // Set the dimensions of the screen. Everything will adjust.
    public void setScreenDimensions(int height, int width);
    public void appendDebugText(String s);
}