import java.awt.GridLayout;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.*;
import javax.swing.event.*;
import java.net.*;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.event.DocumentListener;
import javax.swing.UIManager.*;

// TODO
// Use the observer pattern to update the instructor when more data has been added
// Just test this with console before adding instructor GUI

// TODO
// Account for which thread of execution (instructor or student) begins first.
// TODO
// Account for when one side closes the connection - do not hang up on a function!
// TODO
// Have only 1 popup menu, but change its contents depending on the matching popup.
public class GUI extends JFrame implements GUISubject, DocumentListener
{
    // Config screen and settings screen
    private JDialog GUIconfig;
    private JDialog GUIsettings;
    private JTextArea dataTextArea;
    private JTextArea debugTextArea;

    private JPanel controlPanel;
    private JButton measurementBtn;

    private AbstractSensor currentSensor;

    private InstructorSubject proxy;

    private JPopupMenu clearDataPopup;

    // TODO
    // Add a "Load -> Confirmation" screen when user elects to open a locally saved file.

    public GUI()
    {
        super("Chemberry");
        
        try 
        { 
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        
        initializeTextAreas();
        initializeConfig();
        initializeSettings();
        initializeProxy();
        initializeOther();

        // The rest of the constructor designs the main screen of the GUI.
        this.setSize(1000, 1000);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(100, 100);

        JButton saveData = new JButton();
        saveData.setText("Save");
        saveData.addActionListener(new ActionListener()
        {   
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JFileChooser jfc = new JFileChooser();
                int retVal = jfc.showSaveDialog(null);

                if (retVal == JFileChooser.APPROVE_OPTION)
                {
                    try
                    {
                        PrintWriter writer = new PrintWriter(jfc.getSelectedFile());

                        char[] arr = dataTextArea.getText().toCharArray();

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

                        appendDebugText("Your data has been saved at " + jfc.getSelectedFile().toString());
                    }
                    catch(IOException ex)
                    {
                        // TODO
                        // Perhaps do something else here
                        ex.printStackTrace();
                        appendDebugText("ERROR: Unable to save data");
                    }
                }
            }
        });

        JButton openData = new JButton();
        openData.setText("Open");
        openData.addActionListener(new ActionListener()
        {   
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JFileChooser jfc = new JFileChooser();
                int retVal = jfc.showOpenDialog(null);

                if (retVal == JFileChooser.APPROVE_OPTION)
                {
                    try
                    {         
                        BufferedReader bfr = new BufferedReader(new FileReader(jfc.getSelectedFile()));
                        // TODO
                        // Prompt to make sure user wants to clear text area.
                        dataTextArea.setText("");

                        String content;
                        
                        while ((content = bfr.readLine()) != null)
                        {
                            dataTextArea.append(content);
                            dataTextArea.append("\n");
                        }

                        appendDebugText("Data file successfully opened");
                        bfr.close();
                    }
                    catch(IOException ioe)
                    {
                        appendDebugText("Error opening the file");
                        ioe.printStackTrace();
                    }
                }
            }
        });

        JButton clearData = new JButton();
        clearData.setText("Clear");
        clearData.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                clearDataPopup.setVisible(true);
                // TODO
                // Add the "No input device has been selected" message, or if one has, display what is connected
            }
        });

        JPanel mainRow = new JPanel();
        mainRow.setLayout(new GridLayout(1, 3));

        // TODO
        // Make the scroll panes responsive - have them move as more and more text is added
        JScrollPane datascrl = new JScrollPane(dataTextArea);
        JScrollPane debugscrl = new JScrollPane(debugTextArea);

        mainRow.add(datascrl);
        
        controlPanel = new JPanel();
        measurementBtn = new JButton("Measure");
        controlPanel.setLayout(new GridLayout(3, 1));

        controlPanel.add(new JLabel("Measurement Interface"));
        controlPanel.add(new JLabel("Current instrument: None"));
        controlPanel.add(measurementBtn);

        mainRow.add(controlPanel);
        mainRow.add(debugscrl);

        JPanel bottomRow = new JPanel();
        bottomRow.setLayout(new GridLayout(1, 3));

        bottomRow.add(saveData);
        bottomRow.add(openData);
        bottomRow.add(clearData);

        JPanel topRow = new JPanel();
        topRow.setLayout(new GridLayout(1, 2));

        JButton configBtn = new JButton();
        configBtn.setText("Configuration");
        configBtn.addActionListener(new ActionListener()
        {   
            @Override
            public void actionPerformed(ActionEvent e)
            {
                GUIconfig.setVisible(true);
            }
        });

        JButton settingsBtn = new JButton();
        settingsBtn.setText("Settings");
        settingsBtn.addActionListener(new ActionListener()
        {   
            @Override
            public void actionPerformed(ActionEvent e)
            {
                GUIsettings.setVisible(true);
            }
        });

        // Add all the buttons to the panel
        //topRow.add(dataBtn);
        topRow.add(configBtn);
        topRow.add(settingsBtn);

        // Set the layout for GUI with 3 rows and 0 columns.
        // The GUI can append one component per row-column intersection.
        this.setLayout(new GridLayout(3, 1));
        this.add(topRow);
        this.add(mainRow);
        this.add(bottomRow);

        setVisible(true);
        appendDebugText("Initialization complete");
    }

    private void initializeTextAreas()
    {
        dataTextArea = new JTextArea();
        debugTextArea = new JTextArea();

        // TODO
        // Reset this to false.
        // Find a way to detect the latest change.
        //dataTextArea.setEditable(false);
        debugTextArea.setEditable(false);

        dataTextArea.append(">> No input device has been selected");

        dataTextArea.getDocument().addDocumentListener(this);
    }

    private void initializeOther()
    {
        clearDataPopup = new JPopupMenu("Clear Data?");

        clearDataPopup.setLayout(new GridLayout(2,2));
        clearDataPopup.setPopupSize(500, 500);
        clearDataPopup.setLocation(500, 500);

        JButton yes = new JButton("Yes");
        yes.addActionListener(new ActionListener()
        {   
            @Override
            public void actionPerformed(ActionEvent e)
            {
                dataTextArea.setText("");
                appendDebugText("Data cleared");
                clearDataPopup.setVisible(false);
            }
        });

        JButton no = new JButton("No");
        no.addActionListener(new ActionListener()
        {   
            @Override
            public void actionPerformed(ActionEvent e)
            {
                clearDataPopup.setVisible(false);
            }
        });

        // TODO
        // Clean up this popup.
        clearDataPopup.add(new JLabel("Clear all data?"));
        clearDataPopup.add("");
        clearDataPopup.add(yes);
        clearDataPopup.add(no);

        // TODO
        // Add inet address to settings dialog
        // Add server port to settings dialog
        // Add my port to settings dialog

        // Main Frame init
        String addr = Inet.getMyAddress();

        if (addr != "null")
        {
            appendDebugText("IPv4 address retrieved: " + addr);
        }
        else
        {
            System.out.println("ERROR: Unknown Host Exception");
            appendDebugText("ERROR: Unable to resolve IPv4 address");
        }
    }

    private void initializeConfig()
    {
        GUIconfig = new JDialog();
        GUIconfig.setSize(1000, 1000);
        GUIconfig.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        GUIconfig.setLocation(100, 100);

        JPanel selectionPanel = new JPanel();
        selectionPanel.setLayout(new GridLayout(2, 3));

        // TODO
        // Maybe condense all this down to one function, pass as parameters the new sensor and the debugText
        JButton btnPH = new JButton("pH Probe");
        btnPH.addActionListener(new ActionListener()
        {   
            @Override
            public void actionPerformed(ActionEvent e)
            {
                currentSensor = new pHSensor();
                appendDebugText("Configured sensor: pH probe");
                JLabel label = (JLabel)controlPanel.getComponent(1);
                label.setText("Current Sensor: pH probe");
            }
        });

        JButton btnTemp = new JButton("Temperature Sensor");
        btnTemp.addActionListener(new ActionListener()
        {   
            @Override
            public void actionPerformed(ActionEvent e)
            {
                currentSensor = new TemperatureSensor();
                appendDebugText("Configured sensor: Temperature sensor");
                JLabel label = (JLabel)controlPanel.getComponent(1);
                label.setText("Current Sensor: Temperature");
            }
        });

        JButton btnConduct = new JButton("Conductivity Probe");
        btnConduct.addActionListener(new ActionListener()
        {   
            @Override
            public void actionPerformed(ActionEvent e)
            {
                currentSensor = new ConductivitySensor();
                appendDebugText("Configured sensor: Potentiometer");
                JLabel label = (JLabel)controlPanel.getComponent(1);
                label.setText("Current Sensor: Conductivity");
            }
        });

        selectionPanel.add(btnPH);
        selectionPanel.add(btnTemp);
        selectionPanel.add(btnConduct);

        GUIconfig.add(selectionPanel);
    }

    private void initializeSettings()
    {
        // Settings init
        // TODO
        // Set up layout manager for GUIsettings
        GUIsettings = new JDialog();
        GUIsettings.setSize(1000, 1000);
        GUIsettings.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        GUIsettings.setLocation(100, 100);

        JTextField IPaddr = new JTextField("Address: " + Inet.getMyAddress());
        IPaddr.setEditable(false);

        GUIsettings.add(IPaddr);
    }

    private void initializeProxy()
    {
        proxy = new ProxyGUI(8314, "10.200.170.157", 6023);
        appendDebugText("Set instructor IP");
    }

    public void appendDebugText(String s)
    {
        debugTextArea.append(">> " + s + '\n');
    }

    @Override
    public void insertUpdate(DocumentEvent e)
    {
        update();
    }

    @Override
    public void removeUpdate(DocumentEvent e)
    {
        // TODO
        // I am not sure what would go here.
        // If the GUI is doing a different reading, update the instructor.
        // The instructor's screen will need to keep the old data and be prepared for a new data set.
    }

    @Override
    public void changedUpdate(DocumentEvent e)
    {
        // TODO
        // Not sure what would go here either.
        // See removeUpdate().
        update();
    }

    @Override
    public void update()
    {
        String textUpdate = "u:" + Inet.encodeUpdate(dataTextArea.getText());

        proxy.receiveUpdate(textUpdate);
    }
}