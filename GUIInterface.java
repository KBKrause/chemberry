/**
 * <code>GUIInterface</code> is implemented by <code>GUI</code> so that other classes may interact with and display text to the screen. This helps to separate the logic
 * of the GUI from the logic of any proxies involved.
 * 
 * @author      kevbkraus
 * @see         GUI
 * @since       1.8
 */
public interface GUIInterface
{
    /** 
     * Changes the networking status of the GUI.
     * 
     * @param b the boolean value of the online status
     * @since          1.8
     */
    public void setNetworking(boolean b);

    /** 
     * Changes the width and height of the frame containing the UI.
     * 
     * @param height the new height, in pixels, of the screen
     * @param width the new width, in pixels, of the screen
     * @since          1.8
     */
    public void setScreenDimensions(int height, int width);

    /** 
     * Adds an error message to the debugging window of the GUI.
     * 
     * @param s the debugging message
     * @since          1.8
     */
    public void appendDebugText(String s);

    /** 
     * Sets the experiment of the student.
     * 
     * @param e the new experiment that is being sent to the students
     * @since          1.8
     */
    public void setExperiment(ExperimentGUI e);
}