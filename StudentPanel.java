import javax.swing.*;
import java.awt.*;
import javax.swing.text.DefaultCaret;

public class StudentPanel extends JPanel
{
    private String IPaddr;

    private JTextArea dataArea;
    private JScrollPane scrollPane;

    public StudentPanel(String ip)
    {
        super();

        dataArea = new JTextArea();
        dataArea.setEditable(false);

        DefaultCaret caret = (DefaultCaret)dataArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        scrollPane = new JScrollPane(dataArea);

        IPaddr = ip;

        this.setLayout(new GridLayout(1, 2));
        this.add(scrollPane);

        this.setVisible(true);
    }

    public void append(String newLine)
    {
        //System.out.println("Appending a new line to the dataArea");
        dataArea.append(newLine);
        //System.out.println("The contents are now: " + dataArea.getText());
    }

    public String getIP()
    {
        return IPaddr;
    }

    public String getStringOfText()
    {
        return dataArea.getText();
    }
}