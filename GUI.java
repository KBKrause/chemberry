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
import javax.swing.text.BadLocationException;
import javax.swing.UIManager.*;
import javax.swing.text.DefaultCaret;
import java.awt.Window;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class GUI extends JFrame implements DocumentListener, ChangeListener, GUIInterface
{
    // Config screen and settings "screens."
    private JDialog GUIconfig;
    private JDialog GUIsettings;

    // Main screen components.
    private JTextArea dataTextArea;
    private JTextArea debugTextArea;
    private JPanel controlPanel;
    private JButton measurementBtn;

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

    public GUI(boolean networking, String instructorIP) throws ChemberryException
    {
        super("Chemberry - Main");
    
        try 
        { 
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }

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
                        // Confirm opening. This will need to be changed.
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

        JScrollPane datascrl = new JScrollPane(dataTextArea);
        JScrollPane debugscrl = new JScrollPane(debugTextArea);

        mainRow.add(datascrl);
        
        controlPanel = new JPanel();
        measurementBtn = new JButton("Measure");
        measurementBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (currentSensor == null)
                {
                    appendDebugText("ERROR: No sensor has been configured");
                }
                else
                {
                    // If the current sensor is set to instantaneous measurements, just do a one time measurement.
                    if (currentSensor.isMeasuringInstantly())
                    {
                        dataTextArea.append(currentSensor.instantMeasure().toString() + "\n");
                    }
                    else
                    {
                        // Do not run a multimeasure if one is currently running. This falls under two cases.
                        // 1) No multimeasure has ever been run, so multimeasure is null.
                        // 2) Multimeasure is not null and has been instantiated. It must be a dead thread to start a new one.
                        if ((multiMeasure == null) || (multiMeasure.isAlive() == false))
                        {
                            appendDebugText(currentSensor.toString() + " measuring for " + durationSlider.getValue() + "s with " + intervalSlider.getValue() + "s intervals");
                            
                            multiMeasure = new Thread()
                            {
                                @Override
                                public void run()
                                {
                                    for (int i = 0; i < durationSlider.getValue(); i++)
                                    {
                                        dataTextArea.append(currentSensor.instantMeasure().toString() + "\n");
                            
                                        try
                                        {
                                            Thread.sleep(intervalSlider.getValue() * 1000);
                                        }
                                        catch(Exception e)
                                        {
                                            e.printStackTrace();
                                        }
                                    }
                            
                                    appendDebugText("Continuous measurement finished");
                                }
                            };

                            multiMeasure.start();
                        }
                        else
                        {
                            appendDebugText("ERROR: A continuous measurement is already being ran!");
                        }
                    }
                }
            }
        });
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

    // This method may eventually be removed
    /* 
    private void initialize()
    {
        String username = "";

        JDialog initDialog = new JDialog();
        initDialog.setTitle("Chemberry Initialization");
        JTextField name = new JTextField("Username:");

        JPanel btnsPanel = new JPanel();
        btnsPanel.setLayout(new GridLayout(2, 1));

        // Make a local actionlistener/function to set networkingAllowed.
        JButton online = new JButton("Online");
        online.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                if (!name.getText().equals(""))
                {
                    networkingAllowed = true;
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
                if (!name.getText().equals(""))
                {
                    networkingAllowed = false;
                    initDialog.dispose();
                }
            }
        });

        btnsPanel.add(online);
        btnsPanel.add(offline);

        initDialog.setSize(500, 500);
        initDialog.setLayout(new GridLayout(2, 1));
        initDialog.add(btnsPanel);
        initDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        initDialog.setVisible(true);
    }
    */

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

    private void initializeOther()
    {
        clearDataPopup = new JPopupMenu("Clear Data?");

        clearDataPopup.setLayout(new GridLayout(2,2));
        clearDataPopup.setPopupSize(500, 500);
        clearDataPopup.setLocation(150, 150);

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
    }

    private void initializeConfig()
    {
        GUIconfig = new JDialog();
        GUIconfig.setTitle("Chemberry - Sensor Configuration");
        GUIconfig.setSize(1000, 1000);
        GUIconfig.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        GUIconfig.setLocation(150, 150);

        JPanel selectionPanel = new JPanel();
        selectionPanel.setLayout(new GridLayout(3, 3));

        JPanel confirmPanel = new JPanel();
        confirmPanel.setLayout(new GridLayout(3, 1));

        JLabel instrumentLabel = new JLabel("Chosen Instrument: Not selected");
        logtypeLabel = new JLabel("Measurement type: Instantaneous");

        JButton confirmButton = new JButton("Confirm");

        confirmPanel.add(instrumentLabel);
        confirmPanel.add(logtypeLabel);
        confirmPanel.add(confirmButton);

        String[] probeTypes = {"pH probe", "Temperature sensor", "Conductivity probe"};

        for (String s : probeTypes)
        {
            JButton newSensorButton = new JButton(s);

            newSensorButton.addActionListener(new ActionListener()
            {   
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    instrumentLabel.setText("Chosen Instrument: " + s);
                }
            });

            selectionPanel.add(newSensorButton);
        }

        JPanel intervalPanel = new JPanel();
        intervalPanel.setLayout(new GridLayout(3, 2));

        intervalPanel.add(new JLabel("Continuous Measurement Duration"));
        intervalPanel.add(new JLabel("Continuous Measurement Interval"));

        durationSlider = new JSlider();
        intervalSlider = new JSlider();

        durationSlider.setMinimum(0);
        durationSlider.setMaximum(60);
        durationSlider.addChangeListener(this);

        intervalSlider.setMinimum(0);
        intervalSlider.setMaximum(60);
        intervalSlider.addChangeListener(this);

        intervalPanel.add(durationSlider);
        intervalPanel.add(intervalSlider);

        durationLabel = new JLabel("Duration : " + durationSlider.getValue());
        intervalLabel = new JLabel("Interval: " + intervalSlider.getValue());

        intervalPanel.add(durationLabel);
        intervalPanel.add(intervalLabel);

        // Initially hide the interval panel unless the user wants to do an interval measurement.
        intervalPanel.setVisible(false);

        ButtonGroup buttonsList = new ButtonGroup();

        // TODO
        // Make the intervalPanel visibility change gradual/more pretty.
        JRadioButton instantButton = new JRadioButton("Instantaneous Measurements");
        instantButton.addActionListener(new ActionListener()
        {   
            @Override
            public void actionPerformed(ActionEvent e)
            {
                logtypeLabel.setText("Measurement type: Instantaneous");
                intervalPanel.setVisible(false);
            }
        });

        JRadioButton intervalButton = new JRadioButton("Interval Measurements");
        intervalButton.addActionListener(new ActionListener()
        {   
            @Override
            public void actionPerformed(ActionEvent e)
            {
                logtypeLabel.setText("Measurement type: Continuous for " + durationSlider.getValue() + "s at " + intervalSlider.getValue() + "s intervals");
                intervalPanel.setVisible(true);
            }
        });

        // TODO
        // Add debug error message somewhere
        confirmButton.addActionListener(new ActionListener()
        {   
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String typeOfInstrument = instrumentLabel.getText().substring(19);

                // Not selected
                // Check status of thread here. Make a "finishedSensorReading() method or something."
                // Thread must be null or dead to configure a new sensor.
                if ((!(typeOfInstrument.equals("Not selected"))) && (((multiMeasure == null) || (multiMeasure.isAlive() == false))))
                {
                    if (typeOfInstrument.equals("pH probe"))
                    {
                        currentSensor = new pHSensor();
                    }
                    else if (typeOfInstrument.equals("Temperature sensor"))
                    {
                        currentSensor = new TemperatureSensor();
                    }
                    else
                    {
                        currentSensor = new ConductivitySensor();
                    }
    
                    appendDebugText("Configured sensor: " + currentSensor.toString());
                    JLabel label = (JLabel)controlPanel.getComponent(1);
                    label.setText("Current Sensor: " + currentSensor.toString());

                    if (intervalButton.isSelected())
                    {
                        currentSensor.setMeasuringToInstant(false);
                        label.setText(label.getText() + " measuring for " + durationSlider.getValue() + "s at " + intervalSlider.getValue() + "s intervals");
                    }
    
                    GUIconfig.setVisible(false);

                    // Reset the instrument selection menu.
                    instrumentLabel.setText("Chosen Instrument: Not selected");
                    instantButton.setSelected(true);
                    intervalPanel.setVisible(false);
                }
                else if ((multiMeasure != null) && (multiMeasure.isAlive()))
                {
                    appendDebugText("ERROR: An interval measurement is being taken");
                }
                else
                {
                    appendDebugText("ERROR: Attempted to configure a null instrument");
                }
            }
        });

        instantButton.setSelected(true);

        buttonsList.add(instantButton);
        buttonsList.add(intervalButton);
        
        JPanel buttonsContainer = new JPanel(new GridLayout(1, 2));
        buttonsContainer.add(intervalButton);
        buttonsContainer.add(instantButton);

        intervalPanel.add(buttonsContainer);

        selectionPanel.add(buttonsContainer);
        selectionPanel.add(intervalPanel);

        selectionPanel.add(confirmPanel);

        GUIconfig.add(selectionPanel);
    }

    private void initializeSettings()
    {
        // TODO
        // Set up layout manager for GUIsettings
        GUIsettings = new JDialog();
        GUIsettings.setTitle("Chemberry - Settings");
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
            IPaddr.setText("Networking disabled");
            port.setText("Networking disabled");
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
    public void appendDebugText(String s)
    {
        debugTextArea.append(">> " + s + '\n');
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
            e.printStackTrace();
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
}