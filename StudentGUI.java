/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.text.DefaultCaret;
import javax.swing.JFrame;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import java.awt.event.WindowAdapter;
import java.awt.Window;
import java.awt.event.WindowListener;
import java.awt.event.*;
import javax.swing.event.*;
import java.net.*;
import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import java.awt.event.WindowEvent;
/**
 *
 * @author Kevin
 */
public class StudentGUI extends JFrame implements DocumentListener, GUIInterface
{
    public StudentGUI(boolean networking, String instructorIP) throws ChemberryException
    {
        super("Chemberry Student GUI");

        networkingAllowed = networking;

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(StudentGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StudentGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StudentGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StudentGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        initComponents();
        changeComponents();
        addActionListeners();

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

        String os = System.getProperty("os.name");
        measurements = new ArrayList <Number>();

        try
        {
            arduino = new SerialConnection();
            arduino.beginMeasuring(os);
        }
        catch(SerialConnectionException e)
        {
            e.printMessage();
            System.exit(1);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        //</editor-fold>
        //</editor-fold>

        Thread displayReadingThread = new Thread()
        {
            @Override
            public void run()
            {
                while (true)
                {
                    try
                    {
                        Thread.sleep(1500);
                        displayCurrentSensorReading();
                    }
                    catch(InterruptedException ie)
                    {
                        System.out.println("displayReadingThread interrupted!");
                        //ie.printStackTrace();
                    }
                }
            }
        };
        displayReadingThread.start();

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() 
        {
            @Override
            public void windowClosing(WindowEvent event) 
            {
                System.out.println("Attempting to close");
                closeFrame();
            }
        });
        
        this.setVisible(true);
    }

    private void addActionListeners()
    {
        buttonInstrumentFinish.addActionListener(new java.awt.event.ActionListener()
        {
            @Override 
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                buttonInstrumentFinishActionPerformed(evt);
            }
        });

        buttonInstrumentCancel.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                buttonInstrumentCancelActionPerformed(evt);
            }
        });

        toggleButtonIntervals.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                toggleButtonIntervalsActionPerformed(evt);
            }
        });

        buttonConfigure.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                buttonConfigureActionPerformed(evt);
            }
        });

        buttonMeasure.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                buttonMeasureActionPerformed(e);
            }
        });

        buttonVisualize.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                buttonVisualizeActionPerformed(e);
            }
        });

        buttonSaveData.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                buttonSaveDataActionPerformed(e);
            }
        });

        buttonOpenData.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                buttonOpenDataActionPerformed(evt);
            }
        });

        buttonStopMeasure.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                buttonStopMeasureActionPerformed(e);
            }
        });

        buttonClearData.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                buttonClearDataActionPerformed(e);
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents()
    {

        dialogConfiguration = new javax.swing.JDialog();
        rbuttonpH = new javax.swing.JRadioButton();
        rbuttonCond = new javax.swing.JRadioButton();
        rbuttonTemp = new javax.swing.JRadioButton();
        labelTextInstrument = new javax.swing.JLabel();
        buttonInstrumentFinish = new javax.swing.JButton();
        buttonInstrumentCancel = new javax.swing.JButton();
        sliderDuration = new javax.swing.JSlider();
        sliderInterval = new javax.swing.JSlider();
        toggleButtonIntervals = new javax.swing.JToggleButton();
        labelTextToggle = new javax.swing.JLabel();
        labelTextInterval = new javax.swing.JLabel();
        labelTextDurationSlider = new javax.swing.JLabel();
        valueOfInterval = new javax.swing.JLabel();
        valueOfDuration = new javax.swing.JLabel();
        instrumentButtons = new javax.swing.ButtonGroup();
        measurementTypeButtons = new javax.swing.ButtonGroup();
        buttonConfigure = new javax.swing.JButton();
        buttonSettings = new javax.swing.JButton();
        panelDataDisplay = new javax.swing.JPanel();
        buttonMeasure = new javax.swing.JButton();
        buttonStopMeasure = new javax.swing.JButton();
        buttonVisualize = new javax.swing.JButton();
        splitpaneDataDisplay = new javax.swing.JSplitPane();
        scrollpaneDataPoints = new javax.swing.JScrollPane();
        textAreaDataPoints = new javax.swing.JTextArea();
        scrollpaneCurrentReading = new javax.swing.JScrollPane();
        textAreaCurrentReading = new javax.swing.JTextPane();
        buttonSaveData = new javax.swing.JButton();
        buttonOpenData = new javax.swing.JButton();
        scrollpaneDebug = new javax.swing.JScrollPane();
        textAreaDebug = new javax.swing.JTextArea();
        buttonClearData = new javax.swing.JButton();
        buttonExperimentDetails = new javax.swing.JButton();
        labelTextDataCollection = new javax.swing.JLabel();
        labelTextLiveReading = new javax.swing.JLabel();
        labelTextChemberry = new javax.swing.JLabel();
        separatorTitle = new javax.swing.JSeparator();

        dialogConfiguration.setSize(new java.awt.Dimension(600, 350));

        instrumentButtons.add(rbuttonpH);
        rbuttonpH.setText("pH");

        instrumentButtons.add(rbuttonCond);
        rbuttonCond.setText("Voltage / conductivity");

        instrumentButtons.add(rbuttonTemp);
        rbuttonTemp.setText("Temperature");

        labelTextInstrument.setText("Instrument Selection:");

        buttonInstrumentFinish.setText("Finish");

        buttonInstrumentCancel.setText("Cancel");

        sliderDuration.setMajorTickSpacing(10);
        sliderDuration.setMaximum(60);
        sliderDuration.setMinimum(5);
        sliderDuration.setMinorTickSpacing(5);
        sliderDuration.setPaintLabels(true);
        sliderDuration.setPaintTicks(true);
        sliderDuration.setSnapToTicks(true);

        sliderInterval.setMajorTickSpacing(2);
        sliderInterval.setMaximum(10);
        sliderInterval.setMinorTickSpacing(1);
        sliderInterval.setPaintLabels(true);
        sliderInterval.setPaintTicks(true);
        sliderInterval.setSnapToTicks(true);

        toggleButtonIntervals.setText("Enable Intervals");

        labelTextToggle.setText("Toggle for interval measurements:");

        labelTextInterval.setText("Interval (seconds):");

        labelTextDurationSlider.setText("Duration (seconds):");

        javax.swing.GroupLayout dialogConfigurationLayout = new javax.swing.GroupLayout(dialogConfiguration.getContentPane());
        dialogConfiguration.getContentPane().setLayout(dialogConfigurationLayout);
        dialogConfigurationLayout.setHorizontalGroup(
            dialogConfigurationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogConfigurationLayout.createSequentialGroup()
                .addComponent(labelTextInstrument)
                .addGap(51, 51, 51)
                .addComponent(labelTextToggle)
                .addGap(25, 25, 25)
                .addComponent(labelTextInterval))
            .addGroup(dialogConfigurationLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(rbuttonpH)
                .addGap(131, 131, 131)
                .addComponent(toggleButtonIntervals)
                .addGap(40, 40, 40)
                .addComponent(sliderInterval, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(dialogConfigurationLayout.createSequentialGroup()
                .addGap(480, 480, 480)
                .addComponent(valueOfInterval))
            .addGroup(dialogConfigurationLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(rbuttonCond))
            .addGroup(dialogConfigurationLayout.createSequentialGroup()
                .addGap(398, 398, 398)
                .addComponent(labelTextDurationSlider))
            .addGroup(dialogConfigurationLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(rbuttonTemp)
                .addGap(62, 62, 62)
                .addComponent(buttonInstrumentCancel)
                .addGap(18, 18, 18)
                .addComponent(buttonInstrumentFinish)
                .addGap(18, 18, 18)
                .addComponent(sliderDuration, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        dialogConfigurationLayout.setVerticalGroup(
            dialogConfigurationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogConfigurationLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(dialogConfigurationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelTextInstrument, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(dialogConfigurationLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(labelTextToggle))
                    .addGroup(dialogConfigurationLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(labelTextInterval)))
                .addGap(7, 7, 7)
                .addGroup(dialogConfigurationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rbuttonpH)
                    .addComponent(toggleButtonIntervals)
                    .addComponent(sliderInterval, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addComponent(valueOfInterval)
                .addGap(12, 12, 12)
                .addComponent(rbuttonCond)
                .addGap(3, 3, 3)
                .addComponent(labelTextDurationSlider)
                .addGap(15, 15, 15)
                .addGroup(dialogConfigurationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dialogConfigurationLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(rbuttonTemp))
                    .addGroup(dialogConfigurationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(sliderDuration, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(buttonInstrumentFinish)
                        .addComponent(buttonInstrumentCancel)))
                .addGap(19, 19, 19))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(800, 480));

        buttonConfigure.setText("Configuration");

        buttonSettings.setText("Settings");

        buttonMeasure.setText("Measure");

        buttonStopMeasure.setText("Stop");

        buttonVisualize.setText("Visualize");

        textAreaDataPoints.setEditable(false);
        textAreaDataPoints.setColumns(20);
        textAreaDataPoints.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        textAreaDataPoints.setRows(5);
        scrollpaneDataPoints.setViewportView(textAreaDataPoints);

        splitpaneDataDisplay.setLeftComponent(scrollpaneDataPoints);

        textAreaCurrentReading.setEditable(false);
        textAreaCurrentReading.setContentType("text/html"); // NOI18N
        textAreaCurrentReading.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        textAreaCurrentReading.setText("<html>\r\n  <head>\r\n\r\n  </head>\r\n  <body>\r\n    No instrument selected\n  </body>\r\n</html>\r\n");
        textAreaCurrentReading.setToolTipText("");
        scrollpaneCurrentReading.setViewportView(textAreaCurrentReading);

        splitpaneDataDisplay.setRightComponent(scrollpaneCurrentReading);

        javax.swing.GroupLayout panelDataDisplayLayout = new javax.swing.GroupLayout(panelDataDisplay);
        panelDataDisplay.setLayout(panelDataDisplayLayout);
        panelDataDisplayLayout.setHorizontalGroup(
            panelDataDisplayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDataDisplayLayout.createSequentialGroup()
                .addGroup(panelDataDisplayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDataDisplayLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(buttonMeasure)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(buttonStopMeasure, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(buttonVisualize))
                    .addGroup(panelDataDisplayLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(splitpaneDataDisplay)))
                .addContainerGap())
        );
        panelDataDisplayLayout.setVerticalGroup(
            panelDataDisplayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDataDisplayLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(splitpaneDataDisplay, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDataDisplayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonVisualize, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonStopMeasure, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonMeasure, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        buttonSaveData.setText("Save");
        buttonSaveData.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                buttonSaveDataActionPerformed(evt);
            }
        });

        buttonOpenData.setText("Open");

        textAreaDebug.setEditable(false);
        textAreaDebug.setColumns(20);
        textAreaDebug.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        textAreaDebug.setRows(5);
        textAreaDebug.setText("Welcome to Chemberry.\n\nThis is the information box, where\nmessages will appear as you interact\nwith the program.\n\nError messages and other useful\ninformation will periodically appear\nhere.\n\nTo get started, configure your lab\ninstrument with \"Configuration.\"\nAfter your instructor has sent you an\nexperiment, it will appear under\n\"Experiment Details.\"\n");
        scrollpaneDebug.setViewportView(textAreaDebug);

        buttonClearData.setText("Clear");

        buttonExperimentDetails.setText("Experiment Details");

        labelTextDataCollection.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        labelTextDataCollection.setText("Data Points Collected");

        labelTextLiveReading.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        labelTextLiveReading.setText("Live Reading");

        labelTextChemberry.setFont(new java.awt.Font("Tahoma", 3, 26)); // NOI18N
        labelTextChemberry.setText("Chemberry v0.1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(buttonConfigure, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(9, 9, 9)
                                .addComponent(buttonSettings, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(scrollpaneDebug, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(buttonSaveData, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buttonOpenData)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buttonClearData, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonExperimentDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(231, 231, 231)
                        .addComponent(panelDataDisplay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(separatorTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 760, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(labelTextChemberry)
                                .addGap(44, 44, 44)
                                .addComponent(labelTextDataCollection)
                                .addGap(123, 123, 123)
                                .addComponent(labelTextLiveReading)))
                        .addGap(0, 16, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTextChemberry)
                    .addComponent(labelTextDataCollection)
                    .addComponent(labelTextLiveReading))
                .addGap(1, 1, 1)
                .addComponent(separatorTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(buttonConfigure, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonSettings, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addComponent(scrollpaneDebug))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(buttonSaveData, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonOpenData, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(panelDataDisplay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(buttonExperimentDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonClearData, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>    

    private void buttonClearDataActionPerformed(ActionEvent e)
    {
        textAreaDataPoints.setText("");
        appendDebugText("Data cleared");
        measurements.clear();
    }

    private void buttonStopMeasureActionPerformed(ActionEvent e)
    {
        if ((multiMeasure == null) || (!(multiMeasure.isAlive())))
        {
            appendDebugText("ERROR: Multimeasure not running!");
        }       
        else
        {
            multiMeasure.stop();
            appendDebugText("Multimeasure stopped");
        }
    }

    private void buttonMeasureActionPerformed(ActionEvent evt)
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
                performInstantMeasurement();
            }
            else
            {
                // Do not run a multimeasure if one is currently running. This falls under two cases.
                // 1) No multimeasure has ever been run, so multimeasure is null.
                // 2) Multimeasure is not null and has been instantiated. It must be a dead thread to start a new one.
                if ((multiMeasure == null) || (multiMeasure.isAlive() == false))
                {
                    appendDebugText(currentSensor.toString() + " measuring for " + sliderDuration.getValue() + "s with " + sliderInterval.getValue() + "s intervals");
                    
                    multiMeasure = new Thread()
                    {
                        @Override
                        public void run()
                        {
                            for (int i = 0; i < sliderDuration.getValue(); i++)
                            {
                                try
                                {
                                    Measurement measure = currentSensor.instantMeasure(arduino);
                                    measurements.add(measure.getValue());
                                    textAreaDataPoints.append(measure.toString() + "\n");
                                }
                                catch(Exception e)
                                {
                                    e.printStackTrace();
                                    //System.exit(1);
                                }
                    
                                try
                                {
                                    Thread.sleep(sliderInterval.getValue() * 1000);
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

    private void initializeProxy(String instructorIP) throws ChemberryException
    {
        String name = JOptionPane.showInputDialog(null, "Enter your name: ");

        try
        {
            String addr = Inet.getMyAddress();
            appendDebugText("IPv4 address retrieved: " + addr);
        }
        catch(ChemberryException cbe)
        {
            System.out.println(cbe.getMessage());
            throw cbe;
        }
        
        // TODO
        // Error checking here. Do not allow flow to continue if address cannot be resolved.
        try
        {
            proxy = new GUIProxy(8314, instructorIP, 6023);
            proxy.receiveUpdate("h:" + Inet.getMyAddress() + ":" + name);
            appendDebugText("Set instructor IP");
        }
        catch(ConnectionFailedException e)
        {
            System.out.println("Unable to connect; exiting");
            System.exit(1);
        }
        catch(ChemberryException cbe)
        {
            cbe.printStackTrace();
        }
    }

    private void performInstantMeasurement()
    {
        // TODO THIS MUST CHANGE for each sensor.
        //dataTextArea.append(currentSensor.toString() + " >> " + arduino.getData() + "\n");
        try
        {
            Measurement measure = currentSensor.instantMeasure(arduino);
            textAreaDataPoints.append(measure.toString() + "\n");
            measurements.add(measure.getValue());
        }
        catch(Exception ex)
        {
            ex.printStackTrace();

            //System.exit(1);
        }
    }

    private void buttonVisualizeActionPerformed(ActionEvent e)
    {
        CBLineGraph graph = new CBLineGraph("Chemberry Line Graph", currenttom.toString(), currenttom, measurements);
        graph.displayChart();
    }

    private void buttonConfigureActionPerformed(java.awt.event.ActionEvent evt)                                                
    {                                                    
        dialogConfiguration.setVisible(true);
    }                                               

    private void toggleButtonIntervalsActionPerformed(java.awt.event.ActionEvent evt)                                                      
    {                                                          
        labelTextDurationSlider.setVisible(!(labelTextDurationSlider.isVisible()));
        labelTextInterval.setVisible(!(labelTextInterval.isVisible()));
        sliderInterval.setVisible(!(sliderInterval.isVisible()));
        sliderDuration.setVisible(!(sliderDuration.isVisible()));
    }                                                     

    private void buttonInstrumentCancelActionPerformed(java.awt.event.ActionEvent evt)                                                       
    {                                                           
        dialogConfiguration.setVisible(false);
    }                                                      

    private void buttonInstrumentFinishActionPerformed(java.awt.event.ActionEvent evt)
    {
        String typeOfInstrument = "Not selected";

        if (rbuttonpH.isSelected())
        {
            typeOfInstrument = "pH probe";
        }
        else if (rbuttonCond.isSelected())
        {
            typeOfInstrument = "Conductivity probe";
        }
        else if (rbuttonTemp.isSelected())
        {
            typeOfInstrument = "Temperature sensor";
        }
        
        // Not selected
        // Check status of thread here. Make a "finishedSensorReading() method or something."
        // Thread must be null or dead to configure a new sensor.
        if ((!(typeOfInstrument.equals("Not selected"))) && (((multiMeasure == null) || (multiMeasure.isAlive() == false))))
        {
            if (typeOfInstrument.equals("pH probe"))
            {
                currentSensor = new pHSensor();
                currenttom = TypeOfMeasurement.PH;
            }
            else if (typeOfInstrument.equals("Temperature sensor"))
            {
                currentSensor = new TemperatureSensor();
                currenttom = TypeOfMeasurement.TEMP;
            }
            else if (typeOfInstrument.equals("Conductivity probe"))
            {
                currentSensor = new ConductivitySensor();
                currenttom = TypeOfMeasurement.CONDUCT;
            }

            displayCurrentSensorReading();
            
            appendDebugText("Configured sensor: " + currentSensor.toString());
            //textAreaDataPoints.setText("");
            //JLabel label = (JLabel)controlPanel.getComponent(1);
            //label.setText("Current Sensor: " + currentSensor.toString());
        
            if (toggleButtonIntervals.isSelected())
            {
                currentSensor.setMeasuringToInstant(false);
                //label.setText(label.getText() + " measuring for " + durationSlider.getValue() + "s at " + intervalSlider.getValue() + "s intervals");
            }
            
            dialogConfiguration.setVisible(false);
        
            // Reset the instrument selection menu.
            //instrumentLabel.setText("Chosen Instrument: Not selected");
            //instantButton.setSelected(true);
            //intervalPanel.setVisible(false);
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

    private void buttonSaveDataActionPerformed(ActionEvent evt)
    {
        FileManipulator.saveFile(textAreaDataPoints.getText());
    }

    private void buttonOpenDataActionPerformed(java.awt.event.ActionEvent evt)                                               
    {                                                   
        String bytes_read = FileManipulator.loadFile();
        textAreaDataPoints.setText(bytes_read);
    }                                              

    private void displayCurrentSensorReading()
    {
        if (currentSensor != null)
        {
            try
            {
                textAreaCurrentReading.setText(currentSensor.instantMeasure(arduino).toString());
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private void changeComponents()
    {
        // Hide all the multimeasure info at first
        labelTextDurationSlider.setVisible(false);
        labelTextInterval.setVisible(false);
        sliderInterval.setVisible(false);
        sliderDuration.setVisible(false);

        // Set carets
        DefaultCaret caret = (DefaultCaret)textAreaDebug.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        DefaultCaret caret2 = (DefaultCaret)textAreaDataPoints.getCaret();
        caret2.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        if (networkingAllowed)
        {
            textAreaDataPoints.getDocument().addDocumentListener(this);
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e)
    {
        try
        {
            String updateText = textAreaDataPoints.getText(e.getOffset(), e.getLength());
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
            String updateText = textAreaDataPoints.getText(e.getOffset(), e.getLength());
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

    // TODO Override
    public void appendDebugText(String s)
    {
        textAreaDebug.append(">> " + s + '\n');
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
        
        // Other JComponents to adjust:
        // initDialog
        // configuration
    }

    private void inetError()
    {
        System.out.println("This happened because Inet.getMyAddress() threw an exception");
    }

    @Override
    public void setExperiment(ExperimentGUI e)
    {
        currentExperiment = e;
        appendDebugText("Your instructor has sent you a new experiment: " + e.getTitle());
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

    // Variables declaration - do not modify                     
    private javax.swing.JButton buttonClearData;
    private javax.swing.JButton buttonConfigure;
    private javax.swing.JButton buttonExperimentDetails;
    private javax.swing.JButton buttonInstrumentCancel;
    private javax.swing.JButton buttonInstrumentFinish;
    private javax.swing.JButton buttonMeasure;
    private javax.swing.JButton buttonOpenData;
    private javax.swing.JButton buttonSaveData;
    private javax.swing.JButton buttonSettings;
    private javax.swing.JButton buttonStopMeasure;
    private javax.swing.JButton buttonVisualize;
    private javax.swing.JDialog dialogConfiguration;
    private javax.swing.ButtonGroup instrumentButtons;
    private javax.swing.JLabel labelTextChemberry;
    private javax.swing.JLabel labelTextDataCollection;
    private javax.swing.JLabel labelTextDurationSlider;
    private javax.swing.JLabel labelTextInstrument;
    private javax.swing.JLabel labelTextInterval;
    private javax.swing.JLabel labelTextLiveReading;
    private javax.swing.JLabel labelTextToggle;
    private javax.swing.ButtonGroup measurementTypeButtons;
    private javax.swing.JPanel panelDataDisplay;
    private javax.swing.JRadioButton rbuttonCond;
    private javax.swing.JRadioButton rbuttonTemp;
    private javax.swing.JRadioButton rbuttonpH;
    private javax.swing.JScrollPane scrollpaneCurrentReading;
    private javax.swing.JScrollPane scrollpaneDataPoints;
    private javax.swing.JScrollPane scrollpaneDebug;
    private javax.swing.JSeparator separatorTitle;
    private javax.swing.JSlider sliderDuration;
    private javax.swing.JSlider sliderInterval;
    private javax.swing.JSplitPane splitpaneDataDisplay;
    private javax.swing.JTextPane textAreaCurrentReading;
    private javax.swing.JTextArea textAreaDataPoints;
    private javax.swing.JTextArea textAreaDebug;
    private javax.swing.JToggleButton toggleButtonIntervals;
    private javax.swing.JLabel valueOfDuration;
    private javax.swing.JLabel valueOfInterval;

    private ArrayList <Number> measurements;
    private TypeOfMeasurement currenttom;
    private InstructorInterface proxy;
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
    private ExperimentGUI currentExperiment;
    // End of variables declaration                   
}
