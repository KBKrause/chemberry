import javax.swing.JOptionPane;
import java.awt.GridLayout;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.*;
import javax.swing.event.*;

public class Main
{
    private static String username = "";

    public static void main(String[] args)
    {
        GUISubject gui = new GUI();

        if (args[0].equals("-online"))
        {
            GUIProxy proxy = new GUIProxy(8314, Inet.getMyAddress(), 6023);
            //gui.attach(proxy);
            
            try
            {
                proxy.update("h:ello");
            }
            catch(ConnectionFailedException ex)
            {
                ex.printStackTrace();
            }
        }     
    }

    private static void initialize()
    {
        JDialog initDialog = new JDialog();
        initDialog.setTitle("Chemberry Initialization");

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new GridLayout(1, 2));

        JLabel name = new JLabel("Username:");
        JTextField nameArea = new JTextField("");

        textPanel.add(name);
        textPanel.add(nameArea);

        JPanel btnsPanel = new JPanel();
        btnsPanel.setLayout(new GridLayout(2, 1));

        // Make a local actionlistener/function to set networkingAllowed.
        JButton online = new JButton("Online");
        online.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                if (!nameArea.getText().equals(""))
                {
                    username = nameArea.getText();
                    initDialog.dispose();
                }
            }
        });

        JButton offline = new JButton("Offline");
        online.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                if (!nameArea.getText().equals(""))
                {
                    username = nameArea.getText();
                    initDialog.dispose();
                }
            }
        });

        btnsPanel.add(online);
        btnsPanel.add(offline);

        initDialog.setSize(500, 500);
        initDialog.setLayout(new GridLayout(2, 1));

        initDialog.add(textPanel);
        initDialog.add(btnsPanel);
        
        initDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        initDialog.setVisible(true);
    }
}
