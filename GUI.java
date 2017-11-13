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

// TODO
// Use the observer pattern to update the instructor when more data has been added
// Just test this with console before adding instructor GUI

// TODO
// Account for which thread of execution (instructor or student) begins first.
// TODO
// Account for when one side closes the connection - do not hang up on a function!
// TODO
// Have only 1 popup menu, but change its contents depending on the matching popup.
// Add sounds for measurement button
public class GUI extends JFrame implements DocumentListener, ChangeListener
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

    private JSlider intervalSlider;
    private JSlider durationSlider;
    private JLabel intervalLabel;
    private JLabel durationLabel;

    private JLabel instrumentLabel;
    private JLabel logtypeLabel;

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
        measurementBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                // TODO
                // If they try to configure a new sensor while the current one has taken a measurement, do something about it
                if (currentSensor == null)
                {
                    appendDebugText("ERROR: No sensor has been configured");
                }
                else
                {
                    // TODO
                    // dataTextArea append function can be simplified
                    if (currentSensor.isMeasuringInstantly())
                    {
                        dataTextArea.append(currentSensor.instantMeasure().toString() + "\n");
                    }
                    else
                    {
                        appendDebugText(currentSensor.toString() + " measuring for " + durationSlider.getValue() + "s with " + intervalSlider.getValue() + "s intervals");

                        Thread multiMeasure = new Thread()
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
        dataTextArea.setEditable(false);

        dataTextArea.append(">> No input device has been selected\n");

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
        selectionPanel.setLayout(new GridLayout(3, 3));

        JPanel confirmPanel = new JPanel();
        confirmPanel.setLayout(new GridLayout(3, 1));

        JLabel instrumentLabel = new JLabel("Chosen Instrument: Not selected");
        logtypeLabel = new JLabel("Measurement type: Instantaneous");

        JButton confirmButton = new JButton("Confirm");

        confirmPanel.add(instrumentLabel);
        confirmPanel.add(logtypeLabel);
        confirmPanel.add(confirmButton);

        // TODO
        // Maybe condense all this down to one function, pass as parameters the new sensor and the debugText
        JButton btnPH = new JButton("pH Probe");
        btnPH.addActionListener(new ActionListener()
        {   
            @Override
            public void actionPerformed(ActionEvent e)
            {
                instrumentLabel.setText("Chosen Instrument: pH probe");
            }
        });

        JButton btnTemp = new JButton("Temperature Sensor");
        btnTemp.addActionListener(new ActionListener()
        {   
            @Override
            public void actionPerformed(ActionEvent e)
            {
                instrumentLabel.setText("Chosen Instrument: Temperature sensor");
            }
        });

        JButton btnConduct = new JButton("Conductivity Probe");
        btnConduct.addActionListener(new ActionListener()
        {   
            @Override
            public void actionPerformed(ActionEvent e)
            {
                instrumentLabel.setText("Chosen Instrument: Conductivity probe");
            }
        });

        selectionPanel.add(btnPH);
        selectionPanel.add(btnTemp);
        selectionPanel.add(btnConduct);

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
                logtypeLabel.setText("Measurement type: Continous for " + durationSlider.getValue() + "s at " + intervalSlider.getValue() + " intervals");
                intervalPanel.setVisible(true);
            }
        });

        confirmButton.addActionListener(new ActionListener()
        {   
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String typeOfInstrument = instrumentLabel.getText().substring(19);

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

                if (intervalButton.isSelected())
                {
                    currentSensor.setMeasuringToInstant(false);
                }

                appendDebugText("Configured sensor: " + currentSensor.toString());
                JLabel label = (JLabel)controlPanel.getComponent(1);
                label.setText("Current Sensor: " + currentSensor.toString());

                GUIconfig.setVisible(false);
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
        proxy = new ProxyGUI(8314, Inet.getMyAddress(), 6023);
        appendDebugText("Set instructor IP");
    }

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
            update(updateText);
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
        // I am not sure what would go here.
        // If the GUI is doing a different reading, update the instructor.
        // The instructor's screen will need to keep the old data and be prepared for a new data set.
    }

    // TODO
    // When is this function called?
    @Override
    public void changedUpdate(DocumentEvent e)
    {
        try
        {
            String updateText = dataTextArea.getText(e.getOffset(), e.getLength());
            update(updateText);
        }
        catch(BadLocationException ble)
        {
            ble.printStackTrace();
        }
    }

    private void update(String update)
    {
        String updateWithHeader = "u:" + update;
        proxy.receiveUpdate(updateWithHeader);
    }

    @Override
    public void stateChanged(ChangeEvent e)
    {
        // TODO logtypeLabel change can be condensed into one "function"
        if (e.getSource() == intervalSlider)
        {
            intervalLabel.setText("Interval: " + intervalSlider.getValue());
            logtypeLabel.setText("Measurement type: Continous for " + durationSlider.getValue() + "s at " + intervalSlider.getValue() + " intervals");
        }
        else if (e.getSource() == durationSlider)
        {
            durationLabel.setText("Duration: " + durationSlider.getValue());   
            logtypeLabel.setText("Measurement type: Continous for " + durationSlider.getValue() + "s at " + intervalSlider.getValue() + " intervals");
        }
    }
}