import javax.swing.*;
import java.awt.*;
import javax.swing.text.DefaultCaret;
import java.util.ArrayList;
import java.lang.Number;

public class StudentPanel extends JPanel implements StudentPanelInterface
{
    private String IPaddr;

    private JTextArea dataArea;
    private JScrollPane scrollPane;

    private JLabel averageLabel;
    private JLabel deviationLabel;

    public StudentPanel(String ip)
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

        this.setLayout(new GridLayout(1, 2));

        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new GridLayout(3, 1));
        sidePanel.add(averageLabel);
        sidePanel.add(deviationLabel);

        this.add(scrollPane);
        this.add(sidePanel);

        this.setVisible(true);
    }

    @Override
    public void append(String newLine)
    {
        //System.out.println("Appending a new line to the dataArea");
        dataArea.append(newLine);
        //System.out.println("The contents are now: " + dataArea.getText());
    }

    @Override
    public String getIP()
    {
        return IPaddr;
    }

    // TODO
    // Is this method used, anywhere?
    @Override
    public String getStringOfText()
    {
        return dataArea.getText();
    }

    @Override
    public void updateCalculations()
    {
        String[] text = dataArea.getText().split("\n");

        ArrayList < Double > numbers = new ArrayList < Double >();

        for (String s : text)
        {
            numbers.add(Double.parseDouble(s.substring(s.indexOf(">> ") + 2, s.length() - 1)));
        }

        averageLabel.setText("Average: " + Compute.average(numbers));
        deviationLabel.setText("Deviation: " + Compute.std(numbers));
    }
}