import javax.swing.*;
import java.awt.*;
import javax.swing.UIManager.*;
import java.awt.Dimension;
import javax.swing.text.DefaultCaret;

public class InstructorGUI extends JFrame implements InstructorSubject
{
    private JTabbedPane studentTabs;

    public InstructorGUI()
    {
        super("Instructor");

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
            StudentPanel textHolder = null;

            for (int i = 0; i < studentTabs.getTabCount(); i++)
            {
                // TODO
                // Eventually, tabs should have names of students.
                // Then the if statement below should become
                // if (((StudentPanel)studentTabs.getComponentAt(i)).getIP().equals(tokens[1]))
                if (studentTabs.getTitleAt(i).equals(tokens[1]));
                {
                    textHolder = (StudentPanel)studentTabs.getComponentAt(i);
                }
            }

            if (textHolder == null)
            {
                System.out.println("Couldn't find the tab with matching IP!!!");
            }
            else
            {
                textHolder.append(tokens[2] + "\n");
                //System.out.println("Text contained in object: " + textHolder.getStringOfText());

                // After the text has been appended to, update its calculations.
                
            }
        }
    }
}