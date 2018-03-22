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
    private JTabbedPane studentTabs;

    private JDialog instructorSettings;
    
    private HashMap <String, Boolean> settings;

    private JDialog dialog_procedure;

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
        initializeDialogs();

        try 
        { 
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }

        // Initialize main frame
        this.setSize(1000, 1000);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() 
        {
            @Override
            public void windowClosing(WindowEvent event) 
            {
                closeWindow();
            }
        });

        studentTabs = new JTabbedPane();

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
        JButton sendDataButton = new JButton("Send something");
        sendDataButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                try
                {
                    ClientConnection conn = new ClientConnection(Inet.getMyAddress(), 9648);
                    conn.sendString("u:Hello ... it's me");
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        });

        JButton procedureBtn = new JButton("Procedure");
        procedureBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                dialog_procedure.setVisible(true);
            }
        });

        JPanel menu = new JPanel();
        menu.setLayout(new GridLayout(2, 1));
        
        menu.add(procedureBtn);
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
                Experiment newexp = new Experiment("Default title");
                newexp.setMaterials("");
                newexp.setProcedure("");
                newexp.addDataType(TypeOfMeasurement.PH);
                newexp.render();
            }
        });

        rightPanel.add(expsetupbtn);

        this.setLocation(100, 100);
        this.setLayout(new GridLayout(1,2));
        
        this.add(studentTabs);
        this.add(rightPanel);

        //this.validate();

        this.setVisible(true);
    }

    private void initializeDialogs()
    {
        dialog_procedure = new JDialog();
        dialog_procedure.setTitle("Experimental Procedure Settings");

        dialog_procedure.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);

        JPanel rhs = new JPanel();
        rhs.setLayout(new GridLayout(3, 1));

        JTextArea lhstxt = new JTextArea();
        lhstxt.setText("No procedure identified");
        lhstxt.setEditable(false);

        JButton writeProcButton = new JButton("Write Procedure");
        writeProcButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                ArrayList < String > stepList = new ArrayList < String >();
                JDialog dialog = new JDialog();
                JDialog newStepDialog = new JDialog();

                newStepDialog.setSize(400, 400);
                newStepDialog.setLayout(new GridLayout(1, 2));
                newStepDialog.setTitle("New Procedure - Add a new step");

                JTextArea stepDesc = new JTextArea();
                
                JButton confirmBtn = new JButton("Add Step");
                JButton undobtn = new JButton("Undo");

                newStepDialog.add(stepDesc);
                newStepDialog.add(undobtn);
                newStepDialog.add(confirmBtn);

                dialog.setTitle("Chemberry - Write a New Procedure");
                dialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
                dialog.setSize(400, 400);
                
                JPanel btm = new JPanel();
                btm.setLayout(new GridLayout(1, 3));

                JButton nextStepBtn = new JButton("Add Next Step");
                nextStepBtn.addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e) 
                    {
                        newStepDialog.setVisible(true);
                    }
                });

                JButton finishbtn = new JButton("Finish");

                btm.add(nextStepBtn);
                btm.add(undobtn);
                btm.add(finishbtn);

                dialog.setLayout(new GridLayout(2, 1));
                
                JTextArea tarea = new JTextArea();
                tarea.setEditable(false);

                confirmBtn.addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e) 
                    {
                        if (stepDesc.getText().equals(""))
                        {
                            System.out.println("Something must be typed in the step description");
                        }
                        else
                        {
                            stepList.add(stepDesc.getText());
                            stepDesc.setText("");
                            tarea.append(stepList.get(stepList.size() - 1) + "\n");
                            newStepDialog.setVisible(false);
                        }
                    }
                });

                undobtn.addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e) 
                    {
                        stepList.remove(stepList.size() - 1);
                        tarea.setText("");

                        for (String s : stepList)
                        {
                            tarea.append(s + "\n");
                        }
                    }
                });

                finishbtn.addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e) 
                    {
                        lhstxt.setText(tarea.getText());
                        dialog.setVisible(false);
                    }
                });

                dialog.add(tarea);
                dialog.add(btm);
                
                dialog.setVisible(true);
            }
        });

        JButton ldbtn = new JButton("Load Existing Procedure");
        ldbtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String fileText = FileManipulator.loadFile();
                lhstxt.setText(fileText);
            }
        });
        rhs.add(ldbtn);

        JButton savebtn = new JButton("Save");
        savebtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                FileManipulator.saveFile(lhstxt.getText());
            }
        });

        rhs.add(savebtn);
        rhs.add(writeProcButton);

        JPanel lhs_btm = new JPanel();
        lhs_btm.setLayout(new GridLayout(1, 1));

        JButton broadBtn = new JButton("Broadcast");
        broadBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                try
                {
                    // TODO Send to all clients.
                    for (int i = 0; i < studentTabs.getTabCount(); i++)
                    {
                        //System.out.println("IP of tab at " + i + " : " + ((StudentPanel)studentTabs.getComponentAt(i)).getIP());
                        String ipaddr = ((StudentPanel)studentTabs.getComponentAt(i)).getIP();
                        ClientConnection conn = new ClientConnection(ipaddr, 9648);
                        conn.sendString(Inet.encodeUpdate("bp:" + lhstxt.getText()));
                    }
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        });

        lhs_btm.add(broadBtn);
        //lhs_btm.add(new JButton("Edit"));

        JPanel lhs = new JPanel();
        lhs.setLayout(new GridLayout(2, 1));

        lhs.add(lhstxt);
        lhs.add(lhs_btm);

        dialog_procedure.setLayout(new GridLayout(1, 2));

        dialog_procedure.add(lhs);
        dialog_procedure.add(rhs);
        dialog_procedure.setSize(400, 400);
    }

    private void initializeSettings()
    {
        instructorSettings = new JDialog();
        instructorSettings.setTitle("Settings");

        settings = new HashMap<String, Boolean>();
        
        instructorSettings.setLayout(new GridLayout(2, 1));

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
        settings.put("autosave", false);

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

    /*
    private StudentPanelInterface getStudentPanelAtIndex(int index)
    {
        StudentPanelInterface retval = null;

        retval = (StudentPanel)studentTabs.getComponentAt(index);

        return retval;
    }
    */

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