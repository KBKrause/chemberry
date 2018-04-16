import javax.swing.*;
import java.awt.*;
import javax.swing.text.DefaultCaret;
import java.util.ArrayList;
import java.lang.Number;

/**
 * A StudentPanel is a swing component that  encapsulates the data and information related to one student. It contains a text area with all of 
 * the measurements taken by the student as well as a panel with some basic statistics calculated from the data.
 * This class implements StudentPanelInterface to prevent other classes from seeing its inheritance from JPanel.
 * 
 * @author      KBKrause
 * @see StudentPanelInterface
 * @since       1.8
 */
public class StudentPanel extends JPanel implements StudentPanelInterface
{
    private String IPaddr;
    private String name;

    private JTextArea dataArea;
    private JScrollPane scrollPane;

    private JLabel averageLabel;
    private JLabel deviationLabel;

    /**
     * Creates a StudentPanel that receives data from the specified IP and can be referred to by the specified name.
     * 
     * @author      KBKrause
     * @param ip the IPv4 address of the student sending data to this StudentPanel
     * @param name a unique specifier for this StudentPanel
     * @since       1.8
     */
    public StudentPanel(String ip, String name)
    {
        super();

        dataArea = new JTextArea();
        dataArea.setEditable(false);

        averageLabel = new JLabel("Average: ");
        deviationLabel = new JLabel("Standard Deviation: ");

        DefaultCaret caret = (DefaultCaret)dataArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        scrollPane = new JScrollPane(dataArea);

        IPaddr = ip;
        this.name = name;

        this.setLayout(new GridLayout(1, 2));

        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new GridLayout(3, 1));
        sidePanel.add(averageLabel);
        sidePanel.add(deviationLabel);

        this.add(scrollPane);
        this.add(sidePanel);

        this.setVisible(true);
    }

    /**
     * Returns the name of this StudentPanel.
     * 
     * @author      KBKrause
     * @return the name, or "title," of this StudentPanel
     * @since       1.8
     */
    @Override
    public String getName()
    {
        return name;
    }

    /**
     * Adds a single row of text to the bottom of the text area in this StudentPanel.
     * 
     * @author      KBKrause
     * @param newLine the text to be added to the text area
     * @since       1.8
     */
    @Override
    public void append(String newLine)
    {
        //System.out.println("Appending a new line to the dataArea");
        dataArea.append(newLine);
        //System.out.println("The contents are now: " + dataArea.getText());
    }

    /**
     * Returns the string representation of the IPv4 address related to this StudentPanel.
     * 
     * @author      KBKrause
     * @return the IP address of the connected client
     * @since       1.8
     */
    @Override
    public String getIP()
    {
        return IPaddr;
    }

    // TODO
    // Rename this method

    /**
     * Returns a single string containing all the characters in the text area. If the text area is empty, returns null.
     * 
     * @author      KBKrause
     * @return the text within the text area of this StudentPanel
     * @since       1.8
     */
    @Override
    public String getStringOfText()
    {
        return dataArea.getText();
    }

    /**
     * Calculates the average and standard deviation of the data contained in the text area. This method expects each line of the text area
     * to contain any number of characters describing the data type, followed by ' >> ' and then the numeric value of the measurement.
     * These values are parsed as Doubles. The resulting statistics are labeled on the right side of the StudentPanel.
     * 
     * @author      KBKrause
     * @see Compute
     * @since       1.8
     */
    @Override
    public void updateCalculations()
    {
        String[] text = dataArea.getText().split("\n");

        ArrayList < Double > numbers = new ArrayList < Double >();

        for (String s : text)
        {
            numbers.add(Double.parseDouble(s.substring(s.indexOf(">>") + 2, s.length())));
        }

        averageLabel.setText("Average: " + Compute.average(numbers));
        deviationLabel.setText("Deviation: " + Compute.std(numbers));
    }
}