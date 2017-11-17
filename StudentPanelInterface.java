// This interface will be used by the InstructorGUI.
// The instructor GUI doesn't need to know that the StudentPanel is actually a JPanel.
public interface StudentPanelInterface
{
    public void append(String newLine);
    public String getIP();
    public String getStringOfText();
    public void updateCalculations();
}