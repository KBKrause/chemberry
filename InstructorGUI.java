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
        // Update to existing conn:  u:text
        // Desync:                   r:addr
        String[] tokens = update.split(":");
        System.out.println("Update msg received by GUI: " + update);
        System.out.println("Tokenized update: " + tokens[0] + " " + tokens[1]);

        // Add a tab if it's the first time the student connects.
        if (tokens[0].equals("h"))
        {
            System.out.println("Adding a new tab for the new connection");
            /*
            JPanel newClientTab = new JPanel();
            JLabel addr = new JLabel(tokens[1]);
            newClientTab.setLayout(new GridLayout(1, 1));

            newClientTab.add(addr);

            studentTabs.addTab(tokens[1], null, newClientTab, "Does nothing");
            */

            // TODO
            // Why is this firing null pointer exceptions?
            studentTabs.addTab(tokens[1], new JTextArea());
        }
        else if (tokens[0].equals("u"))
        {

        }
    }
}