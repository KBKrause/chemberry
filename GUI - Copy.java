import java.awt.GridLayout;
import java.awt.LayoutManager;
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
import javax.swing.text.BadLocationException;
import javax.swing.UIManager.*;
import javax.swing.text.DefaultCaret;
import java.awt.Window;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class GUI extends JFrame implements DocumentListener, ChangeListener, GUIInterface
{
    private ArrayList <Number> measurements;
    private TypeOfMeasurement currenttom;
    // Config screen and settings "screens."
    private JDialog GUIconfig;
    private JDialog GUIsettings;

    // Main screen components.
    private JTextArea dataTextArea;
    private JTextArea debugTextArea;
    private JPanel controlPanel;
    private JButton measurementBtn;
    private JLabel currentReading;

    private InstructorInterface proxy;

    private JPopupMenu clearDataPopup;

    // Config screen components.
    private JSlider intervalSlider;
    private JSlider durationSlider;
    private JLabel intervalLabel;
    private JLabel durationLabel;
    private JLabel instrumentLabel;
    private JLabel logtypeLabel;

    // Miscellaneous private members.
    // The current sensor, which could be any of a variety.
    private AbstractSensor currentSensor;
    // This thread runs when an interval measurement is being taken.
    private Thread multiMeasure;
    // if statements evaluate if networking aspects of code need to be ran.
    // TODO
    // This will be removed once the proxy is removed out of the scope of this class.
    private boolean networkingAllowed;
    
    // arduino is the interface to the Arduino Uno connected to the RPi.
    private SerialConnection arduino;

    private JTextArea procedureText;

    private Experiment currentExperiment;

    public GUI(boolean networking, String instructorIP) throws ChemberryException
    {

        networkingAllowed = networking;
        //initialize();
        initializeConfig();
        initializeTextAreas();
        initializeSettings();

        if (networkingAllowed)
        {
            try
            {
                initializeProxy(instructorIP);
            }
            catch(ChemberryException cbe)
            {
                throw cbe;
            }
        }

        initializeOther();

        // The rest of the constructor designs the main screen of the GUI.
        this.setSize(1000, 1000);
        this.setLocation(100, 100);

        JPanel btmCtrlPanel = new JPanel();
        btmCtrlPanel.setLayout(new GridLayout(1, 2));
        btmCtrlPanel.add(measurementBtn);
        btmCtrlPanel.add(cancelMeasureButton);

        topControlRow.add(currentReading);

        controlPanel.add(topControlRow);
        controlPanel.add(new JLabel("Current instrument: None"));
        controlPanel.add(btmCtrlPanel);
        //controlPanel.add(measurementBtn);

        mainRow.add(controlPanel);
        mainRow.add(debugscrl);

        JPanel bottomRow = new JPanel();
        bottomRow.setLayout(new GridLayout(1, 3));

        bottomRow.add(saveData);
        bottomRow.add(openData);
        bottomRow.add(clearData);

        JPanel topRow = new JPanel();
        topRow.setLayout(new GridLayout(1, 3));

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

        JDialog procedureDialog = new JDialog(this);
        procedureDialog.setLayout(new GridLayout(1, 1));
        procedureDialog.setSize(400, 400);
        procedureDialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);

        procedureText = new JTextArea();
        JScrollPane procScrl = new JScrollPane(procedureText);
    
        procedureText.setEditable(false);

        procedureDialog.add(procScrl);

        JButton procedureBtn = new JButton("Experiment Details");
        procedureBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                if (currentExperiment != null)
                {
                    currentExperiment.studentDisplay();
                }
                else
                {
                    appendDebugText("No experiment has been forwarded by the instructor");
                }
            }
        });
        topRow.add(procedureBtn);

        // Set the layout for GUI with 3 rows and 0 columns.
        // The GUI can append one component per row-column intersection.
        this.setLayout(new GridLayout(3, 1));
        this.add(topRow);
        this.add(mainRow);
        this.add(bottomRow);

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() 
        {
            @Override
            public void windowClosing(WindowEvent event) 
            {
                closeFrame();
            }
        });

        setVisible(true);
    }

    private void initializeTextAreas()
    {
        dataTextArea = new JTextArea();
        DefaultCaret caret = (DefaultCaret)dataTextArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        
        debugTextArea = new JTextArea();

        debugTextArea.setEditable(false);
        dataTextArea.setEditable(false);

        dataTextArea.append(">> No input device has been selected\n");

        if (networkingAllowed)
        {
            dataTextArea.getDocument().addDocumentListener(this);
        }
    }

    private void initializeSettings()
    {
        // TODO
        // Set up layout manager for GUIsettings
        GUIsettings = new JDialog();
        GUIsettings.setTitle("Chemberry - Settings");
        GUIsettings.setLayout(new GridLayout());
        GUIsettings.setSize(1000, 1000);
        GUIsettings.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        GUIsettings.setLocation(150, 150);

        // TODO
        // Fix this so that it is not null.
        JTextField IPaddr = new JTextField();
        JTextField port = new JTextField();

        if (networkingAllowed)
        {
            port = new JTextField("Listening Port: 9648");

            try
            {
                IPaddr.setText("Address: " + Inet.getMyAddress());
            }
            catch(ChemberryException cbe)
            {
                inetError();
                closeFrame();
            }
        }
        else
        {
            IPaddr.setText("IPv4 address disabled");
            port.setText("Port disabled");
        }
        
        IPaddr.setEditable(false);
        port.setEditable(false);

        GUIsettings.setLayout(new GridLayout(2, 1));
        GUIsettings.add(IPaddr);
        GUIsettings.add(port);
    }

    private void initializeProxy(String instructorIP) throws ChemberryException
    {
        String name = JOptionPane.showInputDialog(null, "Enter your name: ");

        JDialog configuration = new JDialog();
        configuration.setTitle("Chemberry - Connecting to remote");
        configuration.setSize(500, 500);
        configuration.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        configuration.setLocation(150, 150);
        configuration.setLayout(new GridLayout(2, 1));

        JLabel statusText = new JLabel("Attempting to connect to remote ...");
        JButton crashButton = new JButton("Ok");
        crashButton.addActionListener(new ActionListener()
        {   
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        });

        configuration.add(statusText);
        configuration.setVisible(true);

        try
        {
            String addr = Inet.getMyAddress();
            appendDebugText("IPv4 address retrieved: " + addr);
        }
        catch(ChemberryException cbe)
        {
            throw cbe;
        }
        
        // TODO
        // Error checking here. Do not allow flow to continue if address cannot be resolved.
        try
        {
            proxy = new GUIProxy(8314, instructorIP, 6023);
            proxy.receiveUpdate("h:" + Inet.getMyAddress() + ":" + name);
            appendDebugText("Set instructor IP");
            configuration.setVisible(false);
        }
        catch(ConnectionFailedException e)
        {
            configuration.add(crashButton);
            statusText.setText("Could not establish connection.");
            // Use this to pause the flow of execution.
            Scanner s = new Scanner(System.in);
            s.next();
            s.close();
        }
        catch(ChemberryException cbe)
        {
            cbe.printStackTrace();
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e)
    {
        try
        {
            String updateText = dataTextArea.getText(e.getOffset(), e.getLength());
            update("u:" + updateText);
        }
        catch(BadLocationException ble)
        {
            ble.printStackTrace();
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e)
    {
        // TODO
        // I am unsure what needs to go here.
    }

    @Override
    public void changedUpdate(DocumentEvent e)
    {
        // TODO
        // I am unsure what needs to go here also!
        try
        {
            String updateText = dataTextArea.getText(e.getOffset(), e.getLength());
            update("u:" + updateText);
        }
        catch(BadLocationException ble)
        {
            ble.printStackTrace();
        }
    }

    private void update(String update)
    {
        //String updateWithHeader = "u:" + update;
        try
        {
            proxy.receiveUpdate(update);
        }
        catch(ConnectionFailedException e)
        {
            // TODO Handle this exception
            e.printMessage();
        }
    }

    @Override
    public void stateChanged(ChangeEvent e)
    {
        // TODO logtypeLabel change can be condensed into one "function"
        if (e.getSource() == intervalSlider)
        {
            intervalLabel.setText("Interval: " + intervalSlider.getValue());
            logtypeLabel.setText("Measurement type: Continuous for " + durationSlider.getValue() + "s at " + intervalSlider.getValue() + "s intervals");
        }
        else if (e.getSource() == durationSlider)
        {
            durationLabel.setText("Duration: " + durationSlider.getValue());   
            logtypeLabel.setText("Measurement type: Continuous for " + durationSlider.getValue() + "s at " + intervalSlider.getValue() + "s intervals");
        }
    }

    private void closeFrame()
    {
        appendDebugText("Shutting down ...");
        if (networkingAllowed)
        {
            update("d:esync");
        }
        // Free this JFrame at the end of the function. Once this function call is popped off the stack, the JFrame is gone.
        this.dispose();
        System.exit(0);
    }
    
    @Override
    public void setNetworking(boolean b)
    {
        networkingAllowed = b;
        appendDebugText("Networking change detected: " + networkingAllowed);
    }

    // Method primarily used for the raspberry pi's small screen.
    @Override
    public void setScreenDimensions(int height, int width)
    {
        Double newWidth = width * 0.9;

        // The setSize function only accepts int parameters.
        this.setSize(height, Math.round(newWidth.floatValue()));
        
        // Set the config and settings menu to overlay the main screen.
        GUIconfig.setSize(height, Math.round(newWidth.floatValue()));
        GUIsettings.setSize(height, Math.round(newWidth.floatValue()));

        this.setLocation(0, 0);
        GUIconfig.setLocation(0, 0);
        GUIsettings.setLocation(0, 0);
        
        // Other JComponents to adjust:
        // initDialog
        // configuration
    }

    private void inetError()
    {
        System.out.println("This happened because Inet.getMyAddress() threw an exception");
    }

    @Override
    public void setExperiment(Experiment e)
    {
        currentExperiment = e;
        appendDebugText("Your instructor has sent you a new experiment: " + e.getTitle());
    }
}
