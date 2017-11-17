import javax.swing.*;
import java.awt.*;
import javax.swing.UIManager.*;
import java.awt.Dimension;
import javax.swing.text.DefaultCaret;
import java.io.PrintWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;

public class InstructorGUI extends JFrame implements InstructorSubject
{
    private JTabbedPane studentTabs;

    private JDialog instructorSettings;
    
    private HashMap <String, Boolean> settings;

    public InstructorGUI()
    {
        super("Instructor");

        initializeSettings();

        try 
        { 
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }

        // Initialize main frame
        this.setSize(1000, 1000);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(100, 100);
        this.setLayout(new GridLayout(3,3));

        studentTabs = new JTabbedPane();

        this.add(studentTabs);

        //this.validate();

        this.setVisible(true);
    }

    // TODO
    // Add this to the actual instructor screen.
    private void initializeSettings()
    {
        instructorSettings = new JDialog();
        settings = new HashMap<String, Boolean>();

        // Default instructor settings.
        settings.put("autosave", false);
    }

    @Override
    public void receiveUpdate(String update)
    {
        // TODO
        // Finish "detokenizing."
        // Updates received:
        // New connection:           h:addr
        // Update to existing conn:  u:text:update
        // Desync:                   r:addr
        String[] tokens = update.split(":");
        System.out.println("Update msg received by GUI: " + update);
        //System.out.println("Tokenized string: " + tokens.toString());
        System.out.println("Tokenized update: " + tokens[0] + " " + tokens[1]);

        if (tokens[0].equals("h"))
        {
            studentTabs.addTab(tokens[1], new StudentPanel(tokens[1]));
        }
        else if (tokens[0].equals("u"))
        {
            StudentPanelInterface textHolder = getStudentPanelAtIndex(getIndexOfPanelWithIdentifier(tokens[1]));

            if (textHolder == null)
            {
                System.out.println("Couldn't find the tab with matching IP!!!");
            }
            else
            {
                textHolder.append(tokens[2] + "\n");
                textHolder.updateCalculations();
            }
        }
        else if (tokens[0].equals("d"))
        {
            if (settings.get("autosave").equals(true))
            {
                // TODO
                // Since this is used elsewhere, maybe condense to a function.
                try
                {
                    LocalDateTime date = LocalDateTime.now();
                    String append = "";

                    append = append.concat(String.valueOf(date.getMonthValue()));
                    append = append.concat("-" + String.valueOf(date.getDayOfMonth()));
                    append = append.concat("-" + String.valueOf(date.getHour()));
                    append = append.concat("-" + String.valueOf(date.getMinute()));

                    PrintWriter writer = new PrintWriter("./out/" + tokens[1] + "-" + append + ".dat");

                    StudentPanelInterface panel = getStudentPanelAtIndex(getIndexOfPanelWithIdentifier(tokens[1]));

                    char[] arr = panel.getStringOfText().toCharArray();

                    for (char ch : arr)
                    {
                        if (ch != '\n')
                        {
                            writer.print(ch);
                        }
                        else
                        {
                            writer.println();
                        }
                    }

                    writer.close();
                }
                catch(IOException ex)
                {
                    ex.printStackTrace();
                }
            }
            
            System.out.println(tokens[1] + " has disconnected");
            studentTabs.remove(getIndexOfPanelWithIdentifier(tokens[1]));
        }
    }

    private StudentPanelInterface getStudentPanelAtIndex(int index)
    {
        StudentPanelInterface retval = null;

        retval = (StudentPanel)studentTabs.getComponentAt(index);

        return retval;
    }

    private int getIndexOfPanelWithIdentifier(String ident)
    {
        int retval = 0;
        
        for (int i = 0; i < studentTabs.getTabCount(); i++)
        {
            // TODO
            // Eventually, tabs should have names of students.
            // Then the if statement below should become
            // if (((StudentPanel)studentTabs.getComponentAt(i)).getIP().equals(tokens[1]))
            if (studentTabs.getTitleAt(i).equals(ident));
            {
                retval = i;
            }
        }
        
        return retval;
    }
}