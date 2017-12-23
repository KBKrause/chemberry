// TODO: getName() did not have to be implemented and compiled. In sTudentpanel, it returned null when called. why?
// This interface will be used by the InstructorGUI.
// The instructor GUI doesn't need to know that the StudentPanel is also a JPanel.
public interface StudentPanelInterface
{
    public void append(String newLine);
    public String getIP();
    public String getName();
    // TODO: is this method too verbose?
    public String getStringOfText();
    public void updateCalculations();
}