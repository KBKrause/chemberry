import javax.swing.*;
import java.awt.*;
import javax.swing.UIManager.*;
import java.awt.Dimension;

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
        System.out.println("Tokenized string: " + tokens.toString());
        System.out.println("Tokenized update: " + tokens[0] + " " + tokens[1]);

        // Add a tab if it's the first time the student connects.
        if (tokens[0].equals("h"))
        {
            JPanel panel = new JPanel();
            panel.add(new JTextArea());
    
            JScrollPane scrl = new JScrollPane(panel);
    
            studentTabs.addTab(tokens[1], scrl);
        }
        else if (tokens[0].equals("u"))
        {
            JScrollPane textHolder = null;
            // TODO
            // Perhaps make a hashtable of IP to JTextAreas and use that instead of all this garbage.
            for (int i = 0; i < studentTabs.getTabCount(); i++)
            {
                if (studentTabs.getTitleAt(i).equals(tokens[1]));
                {
                    textHolder = (JScrollPane)studentTabs.getComponentAt(i);
                }
            }

            if (textHolder == null)
            {
                System.out.println("Couldn't find the tab with matching IP!!!");
            }
            else
            {
                JPanel textArea = (JPanel)textHolder.getViewport().getComponent(0);
                JTextArea text = (JTextArea)textArea.getComponent(0);
                text.append(tokens[2] + "\n");
            }
        }
    }
}