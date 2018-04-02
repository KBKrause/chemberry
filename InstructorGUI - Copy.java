import javax.swing.*;
import java.awt.*;
import javax.swing.UIManager.*;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowListener;
import javax.swing.text.DefaultCaret;
import java.io.PrintWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;

public class InstructorGUI extends JFrame implements InstructorInterface
{
    private Experiment thisExperiment;

    private JTabbedPane studentTabs;

    private JDialog instructorSettings;
    
    private HashMap <String, Boolean> settings;

    public InstructorGUI() throws ChemberryException
    {
        super("Chemberry Instructor");

        String myAddr;
        try
        {
            myAddr = Inet.getMyAddress();
        }
        catch(ChemberryException cbe)
        {
            throw cbe;
        }

        initializeSettings();

        JButton settingsBtn = new JButton("Settings");
        settingsBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                instructorSettings.setVisible(true);
            }
        });

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(3, 1));

        // TODO
        // eventually conceal the IP logic
        JButton sendDataButton = new JButton("Broadcast Experiment");
        sendDataButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                if (thisExperiment != null)
                {
                    try
                    {
                        ClientConnection conn = new ClientConnection(Inet.getMyAddress(), 9648);
                        conn.sendString(Inet.encodeUpdate("exp:" + 
                            thisExperiment.getTitle() + ":" + 
                            thisExperiment.getProcedure() + ":" + 
                            thisExperiment.getMaterials() + ":" + 
                            thisExperiment.getDataTypes()));
                    }
                    catch(Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
            }
        });

        JPanel menu = new JPanel();
        menu.setLayout(new GridLayout(1, 1));

        menu.add(sendDataButton);
        //rightPanel.add(sendDataButton);

        rightPanel.add(menu);
        rightPanel.add(settingsBtn);

        JButton expsetupbtn = new JButton("Experiment Setup");
        expsetupbtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                if (thisExperiment == null)
                {
                    thisExperiment = new Experiment("Default title");
                }
                
                thisExperiment.showSetup();
            }
        });
    }

    private void initializeSettings()
    {
        instructorSettings = new JDialog();
        instructorSettings.setTitle("Settings");

        JTextArea ipAddr = new JTextArea();
        ipAddr.setEditable(false);

        try
        {
            ipAddr.append("Address: " + Inet.getMyAddress());
            ipAddr.append("\nListening port: 6023");
        }
        catch(ChemberryException cbe)
        {
            cbe.printStackTrace();
        }
        instructorSettings.add(ipAddr);

        JPanel panelOfCheckBoxes = new JPanel();
        panelOfCheckBoxes.setLayout(new GridLayout(2, 1));

        // Checkboxes
        JCheckBox chk_autosave = new JCheckBox("Autosave data on desync", false);

        JButton confirmChanges = new JButton("Confirm");
        // Get all of the selected boxes and make the respective changes in the hashmap.
        // This is not a scalable approach.
        confirmChanges.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                HashMap < String, Boolean > changes = new HashMap < String, Boolean >();

                changes.put("autosave", chk_autosave.isSelected());

                // TODO
                // Safe reference copy?
                settings = changes;
                instructorSettings.setVisible(false);
            }
        });

        panelOfCheckBoxes.add(chk_autosave);
        panelOfCheckBoxes.add(confirmChanges);

        instructorSettings.add(panelOfCheckBoxes);

        instructorSettings.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        instructorSettings.setSize(500, 500);
        instructorSettings.setVisible(false);
    }
}