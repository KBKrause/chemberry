import java.awt.Window;
import javax.swing.JFrame;
import java.awt.event.WindowAdapter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.HashMap;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ProfessorGUI extends JFrame implements InstructorInterface
{
    private ExperimentGUI thisExperiment;
    private HashMap <String, Boolean> settings;

    public ProfessorGUI() throws ChemberryException
    {
        super("Chemberry Instructor");

        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(ProfessorGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(ProfessorGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(ProfessorGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(ProfessorGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        settings = new HashMap<String, Boolean>();
        settings.put("autosave", false);

        initComponents();
        changeComponents();
        addActionListeners();

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() 
        {
            @Override
            public void windowClosing(WindowEvent event) 
            {
                closeWindow();
            }
        });

        this.setVisible(true);
    }

    private void changeComponents()
    {
        try
        {
            labelInetAddress.setText(Inet.getMyAddress());
            labelListeningPort.setText("6023");
        }
        catch(ChemberryException cbe)
        {
            cbe.printStackTrace();
        }
    }

    private void addActionListeners()
    {
        buttonExperimentSetup.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                if (thisExperiment == null)
                {
                    thisExperiment = new ExperimentGUI();
                }
                else
                {
                    thisExperiment.setVisible(true);
                }
            }
        });

        buttonExperimentBroadcast.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                if (thisExperiment != null)
                {
                    try
                    {
                        // TODO Hide IP logic
                        ClientConnection conn = new ClientConnection(Inet.getMyAddress(), 9648);
                        conn.sendString(Inet.encodeUpdate("exp:" + 
                            thisExperiment.getTitle() + ":" + 
                            thisExperiment.getObjectives() + ":" +
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

        buttonSettings.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                dialogSettings.setVisible(true);
            }
        });

        buttonSettingsOK.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                HashMap < String, Boolean > changes = new HashMap < String, Boolean >();
                
                changes.put("autosave", cboxAutosave.isSelected());
                
                // TODO
                // Safe reference copy?
                settings = changes;
                dialogSettings.setVisible(false);
            }
        });

        buttonSettingsCancel.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                dialogSettings.setVisible(false);
            }
        });
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents()
    {
        dialogSettings = new javax.swing.JDialog();
        buttonSettingsOK = new javax.swing.JButton();
        buttonSettingsCancel = new javax.swing.JButton();
        labelTextPort = new javax.swing.JLabel();
        labelListeningPort = new javax.swing.JLabel();
        labelInetAddress = new javax.swing.JLabel();
        labelTextIP = new javax.swing.JLabel();
        cboxAutosave = new javax.swing.JCheckBox();
        labelTextInetSettings = new javax.swing.JLabel();
        labelTextGenSettings = new javax.swing.JLabel();
        studentTabs = new javax.swing.JTabbedPane();
        labelTextChemberry = new javax.swing.JLabel();
        separatorTitle = new javax.swing.JSeparator();
        labelTextStudentData = new javax.swing.JLabel();
        buttonExperimentSetup = new javax.swing.JButton();
        buttonExperimentBroadcast = new javax.swing.JButton();
        buttonSettings = new javax.swing.JButton();
        labelTextCurrentBehavior = new javax.swing.JLabel();
        scrollPaneDebug = new javax.swing.JScrollPane();
        textAreaDebug = new javax.swing.JTextArea();
        labelCurrentExperiment = new javax.swing.JLabel();
        labelTextCurrentExperiment = new javax.swing.JLabel();

        dialogSettings.setTitle("Chemberry Instructor Settings");
        dialogSettings.setResizable(false);
        dialogSettings.setSize(new java.awt.Dimension(400, 200));

        buttonSettingsOK.setText("Accept");

        buttonSettingsCancel.setText("Cancel");

        labelTextPort.setText("Listening port:");

        labelListeningPort.setText("port");

        labelInetAddress.setText("ipv4");

        labelTextIP.setText("IPv4:");

        cboxAutosave.setText("Autosave student data");

        labelTextInetSettings.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        labelTextInetSettings.setText("Internet Settings");

        labelTextGenSettings.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        labelTextGenSettings.setText("General Settings");

        javax.swing.GroupLayout dialogSettingsLayout = new javax.swing.GroupLayout(dialogSettings.getContentPane());
        dialogSettings.getContentPane().setLayout(dialogSettingsLayout);
        dialogSettingsLayout.setHorizontalGroup(
            dialogSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogSettingsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dialogSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(labelTextInetSettings)
                    .addGroup(dialogSettingsLayout.createSequentialGroup()
                        .addGroup(dialogSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelTextIP)
                            .addComponent(labelTextPort))
                        .addGap(31, 31, 31)
                        .addGroup(dialogSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelListeningPort)
                            .addComponent(labelInetAddress))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addGroup(dialogSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelTextGenSettings)
                    .addComponent(cboxAutosave))
                .addGap(54, 54, 54))
            .addGroup(dialogSettingsLayout.createSequentialGroup()
                .addGap(101, 101, 101)
                .addComponent(buttonSettingsCancel)
                .addGap(18, 18, 18)
                .addComponent(buttonSettingsOK)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        dialogSettingsLayout.setVerticalGroup(
            dialogSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dialogSettingsLayout.createSequentialGroup()
                .addGroup(dialogSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dialogSettingsLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(dialogSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelTextInetSettings)
                            .addComponent(labelTextGenSettings))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(dialogSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelTextIP)
                            .addComponent(labelInetAddress)))
                    .addGroup(dialogSettingsLayout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(cboxAutosave)))
                .addGap(4, 4, 4)
                .addGroup(dialogSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTextPort)
                    .addComponent(labelListeningPort))
                .addGap(38, 38, 38)
                .addGroup(dialogSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonSettingsCancel)
                    .addComponent(buttonSettingsOK))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setPreferredSize(new java.awt.Dimension(800, 600));
        setResizable(false);
        setSize(new java.awt.Dimension(800, 600));

        labelTextChemberry.setFont(new java.awt.Font("Tahoma", 3, 26)); // NOI18N
        labelTextChemberry.setText("Chemberry Instructor v0.1");

        labelTextStudentData.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        labelTextStudentData.setText("Student Data");

        buttonExperimentSetup.setText("Experiment Setup");

        buttonExperimentBroadcast.setText("Broadcast Experiment");

        buttonSettings.setText("Settings");

        labelTextCurrentBehavior.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        labelTextCurrentBehavior.setText("Notifications");

        textAreaDebug.setEditable(false);
        textAreaDebug.setColumns(20);
        textAreaDebug.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        textAreaDebug.setRows(5);
        textAreaDebug.setText("Welcome to Chemberry Instructor client.\n\nThis is the notifications box, where messages will appear as you interact\nwith the program. Additionally, when students connect or disconnect from\nyour program, you will be notified here.\n\nError messages and other useful\ninformation will periodically appear\nhere.\n\nTo get started, configure or load a pre-made experiment by clicking\n\"Experiment Setup.\" Follow the instructions there, and click \"Broadcast\nExperiment\" once you are finished to send it to all connected students.");
        scrollPaneDebug.setViewportView(textAreaDebug);

        labelCurrentExperiment.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        labelCurrentExperiment.setText("No experiment selected");

        labelTextCurrentExperiment.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        labelTextCurrentExperiment.setText("Current Experiment");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(separatorTitle)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(labelTextChemberry, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(studentTabs))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(114, 114, 114)
                                .addComponent(labelTextStudentData)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(164, 164, 164)
                                        .addComponent(labelTextCurrentBehavior))
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(labelCurrentExperiment, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(scrollPaneDebug, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(6, 6, 6))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(buttonExperimentSetup)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(buttonExperimentBroadcast)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(buttonSettings, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(labelTextCurrentExperiment)
                                        .addGap(132, 132, 132)))))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTextChemberry)
                    .addComponent(buttonExperimentSetup)
                    .addComponent(buttonExperimentBroadcast)
                    .addComponent(buttonSettings))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(separatorTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTextStudentData)
                    .addComponent(labelTextCurrentExperiment))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelCurrentExperiment, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(labelTextCurrentBehavior)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scrollPaneDebug, javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE))
                    .addComponent(studentTabs))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>                        

    // Variables declaration - do not modify                     
    private javax.swing.JButton buttonExperimentBroadcast;
    private javax.swing.JButton buttonExperimentSetup;
    private javax.swing.JButton buttonSettings;
    private javax.swing.JButton buttonSettingsCancel;
    private javax.swing.JButton buttonSettingsOK;
    private javax.swing.JCheckBox cboxAutosave;
    private javax.swing.JDialog dialogSettings;
    private javax.swing.JLabel labelCurrentExperiment;
    private javax.swing.JLabel labelInetAddress;
    private javax.swing.JLabel labelListeningPort;
    private javax.swing.JLabel labelTextChemberry;
    private javax.swing.JLabel labelTextCurrentBehavior;
    private javax.swing.JLabel labelTextCurrentExperiment;
    private javax.swing.JLabel labelTextGenSettings;
    private javax.swing.JLabel labelTextIP;
    private javax.swing.JLabel labelTextInetSettings;
    private javax.swing.JLabel labelTextPort;
    private javax.swing.JLabel labelTextStudentData;
    private javax.swing.JScrollPane scrollPaneDebug;
    private javax.swing.JSeparator separatorTitle;
    private javax.swing.JTabbedPane studentTabs;
    private javax.swing.JTextArea textAreaDebug;
    // End of variables declaration                   

    @Override
    public void receiveUpdate(String update)
    {
        String[] tokens = update.split(":");
        System.out.println("Update msg received by GUI: " + update);
        //System.out.println("Tokenized string: " + tokens.toString());
        System.out.println("Tokenized update: " + tokens[0] + " " + tokens[1]);

        if (tokens[0].equals("h"))
        {
            studentTabs.addTab(tokens[2], new StudentPanel(tokens[1], tokens[2]));
        }
        else if (tokens[0].equals("u"))
        {
            StudentPanelInterface textHolder = getStudentPanelWithIdentifier(tokens[1]);

            if (textHolder == null)
            {
                System.out.println("Couldn't find the tab with matching IP!!!");
            }
            else
            {
                textHolder.append(tokens[2] + "\n");
                textHolder.updateCalculations();
            }
        }
        else if (tokens[0].equals("d"))
        {
            if (settings.get("autosave").equals(true))
            {
                System.out.println("Autosaving a disconnected client");
                // TODO
                // Since this is used elsewhere, maybe condense to a function.
                try
                {
                    LocalDateTime date = LocalDateTime.now();
                    String append = "";

                    append = append.concat(String.valueOf(date.getMonthValue()));
                    append = append.concat("-" + String.valueOf(date.getDayOfMonth()));
                    append = append.concat("-" + String.valueOf(date.getHour()));
                    append = append.concat("-" + String.valueOf(date.getMinute()));

                    StudentPanelInterface panel = getStudentPanelWithIdentifier(tokens[1]);

                    PrintWriter writer = new PrintWriter("./out/" + panel.getName() + "-" + append + ".dat");

                    char[] arr = panel.getStringOfText().toCharArray();

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
                }
                catch(IOException ex)
                {
                    ex.printStackTrace();
                }
            }
            
            System.out.println(tokens[1] + " has disconnected");
            studentTabs.remove(getIndexOfPanelWithIdentifier(tokens[1]));
        }
    }

    // This method finds the StudentPanel with the specified ID.
    // TODO: ventually, it should locate panels by name rather than IP to add a layer of abstraction.
    private StudentPanelInterface getStudentPanelWithIdentifier(String identifier) 
    {
        StudentPanelInterface retval = null;
        
        for (int i = 0; i < studentTabs.getTabCount(); i++) 
        {
            StudentPanelInterface spi = (StudentPanelInterface)studentTabs.getComponentAt(i);
            //System.out.println("IP of current spi: " + spi.getIP());
            if (spi.getIP().equals(identifier))
            {
                retval = (StudentPanelInterface)studentTabs.getComponentAt(i);
            }
        }
        
        return retval;
    }

    private int getIndexOfPanelWithIdentifier(String ident)
    {
        System.out.println("Ident searching for is " + ident);
        int retval = 0;
        
        for (int i = 0; i < studentTabs.getTabCount(); i++)
        {
            System.out.println("IP of tab at " + i + " : " + ((StudentPanel)studentTabs.getComponentAt(i)).getIP());
            // TODO
            // Eventually, tabs should have names of students.
            // Then the if statement below should become
            if (((StudentPanel)studentTabs.getComponentAt(i)).getIP().equals(ident))
            //if (studentTabs.getTitleAt(i).equals(ident));
            {
                System.out.println("Found a match");
                retval = i;
            }
        }

        System.out.println("Index is " + retval);
        
        return retval;
    }

    private void closeWindow()
    {
        for (int i = 0; i < studentTabs.getTabCount(); i++)
        {
            StudentPanelInterface spi = (StudentPanelInterface)studentTabs.getComponentAt(i);
            // Make this more abstract.
            ClientConnection conn = new ClientConnection(spi.getIP(), 9648);
            conn.sendString("d:esync");
        }
        
        this.dispose();
        System.exit(0);
    }
}