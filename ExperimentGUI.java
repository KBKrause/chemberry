import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import javax.swing.JFrame;
import java.util.HashSet;

/**
 * ExperimentGUI contains the logic and UI for an experiment, which defines a human-readable set of instructions for students to follow
 * as they go through their experiment.
 * 
 * @author      KBKrause
 * @since       1.8
 */
public class ExperimentGUI extends javax.swing.JFrame
{
    private String title;
    private String procedure;
    private String materials;
    private String objectives;
    private HashSet<TypeOfMeasurement> dataTypes;

    // TODO the private fields might not be needed.
    // TODO When an experiment is instantiated and you know ahead of time that there is an identified procedure and materials,
    // TODO add abstract / objectives
    //      you end up needing to call the set methods to update the UI. Add another constructor(s) to set these ahead of time.
    //      Add an update method or updateUI() to reset GUI appearance.
    
    // TODO add save / load for experiments, don't forget file extension
    // TODO don't show visible on load

    public ExperimentGUI()
    {
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
            java.util.logging.Logger.getLogger(ExperimentGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(ExperimentGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(ExperimentGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(ExperimentGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        initComponents();
        changeComponents();
        addActionListeners();

        // TODO I hope this doesn't kill the JVM when instantiated from ProfessorGUI
        // TODO dispose or hide?
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        dataTypes = new HashSet<TypeOfMeasurement>();

        //this.setVisible(true);
    }

    /**
     * Shows the experiment details screen.
     * 
     * @author      KBKrause
     * @since       1.8
     */
    public void display()
    {
        this.setVisible(true);
    }

    /**
     * Shows the experiment details screen but makes all fields uneditable. Additionally hides all buttons so that only text areas and labels are visible.
     * 
     * @author      KBKrause
     * @since       1.8
     */
    public void studentDisplay()
    {
        buttonFinish.setVisible(false);
        buttonLoad.setVisible(false);
        fieldTitle.setEditable(false);
        textAreaObjectives.setEditable(false);
        textAreaMaterials.setEditable(false);
        textAreaProcedure.setEditable(false);
        cboxpH.setEnabled(false);
        cboxTemperature.setEnabled(false);
        cboxVoltage.setEnabled(false);

        this.setVisible(true);
    }

    /**
     * Returns the title of this experiment.
     * 
     * @author      KBKrause
     * @return the title of this experiment
     * @since       1.8
     */
    public String getTitle() 
    {
        return title;
    }

    /**
     * Returns the objectives of this experiment, separated by newlines.
     * 
     * @author      KBKrause
     * @return the objectives of this experiment
     * @since       1.8
     */
    public String getObjectives()
    {
        return objectives;
    }

    /**
     * Returns the procedure of this experiment.
     * 
     * @author      KBKrause
     * @return the procedure of this experiment
     * @since       1.8
     */
    public String getProcedure() 
    {
        return procedure;
    }

    /**
     * Returns the materials that are part of this experiment.
     * 
     * @author      KBKrause
     * @return the materials in the experiment
     * @since       1.8
     */
    public String getMaterials() 
    {
        return materials;
    }

    /**
     * Returns each of the data types that are part of this experiment, separated by newlines. These are string representations
     * of the enumerations from TypeOfMeasurement.
     * 
     * @author      KBKrause
     * @return the materials in the experiment
     * @see TypeOfMeasurement
     * @since       1.8
     */
    public String getDataTypes() 
    {
        String retval = "";

        for (TypeOfMeasurement tom : dataTypes) 
        {
            retval += tom.toString() + "\n";
        }

        return retval;
    }

    /**
     * Changes the title of the experiment and updates the corresponding field in the UI.
     * 
     * @author      KBKrause
     * @param s the new title
     * @since       1.8
     */
    public void setTitle(String s)
    {
        fieldTitle.setText(s);
        title = s;
    }

    /**
     * Changes the objectives of the experiment and updates the corresponding field in the UI.
     * 
     * @author      KBKrause
     * @param s the new objectives
     * @since       1.8
     */
    public void setObjectives(String s)
    {
        textAreaObjectives.setText(s);
        objectives = s;
    }

    /**
     * Changes the procedure of the experiment and updates the corresponding field in the UI.
     * 
     * @author      KBKrause
     * @param s the new procedure
     * @since       1.8
     */
    public void setProcedure(String s) 
    {
        textAreaProcedure.setText(s);
        procedure = s;
    }

    /**
     * Changes the materials in the experiment and updates the corresponding field in the UI.
     * 
     * @author      KBKrause
     * @param s the new materials
     * @since       1.8
     */
    public void setMaterials(String s) 
    {
        textAreaMaterials.setText(s);
        materials = s;
    }

    /**
     * Adds the type of measurement to this experiment.
     * 
     * @author      KBKrause
     * @param s the new data type
     * @see TypeOfMeasurement
     * @since       1.8
     */
    public void addDataType(TypeOfMeasurement tom) 
    {
        dataTypes.add(tom);
        
        if (tom.equals(TypeOfMeasurement.PH))
            cboxpH.setSelected(true);
        else if (tom.equals(TypeOfMeasurement.CONDUCT))
            cboxVoltage.setSelected(true);
        else if (tom.equals(TypeOfMeasurement.TEMP))
            cboxTemperature.setSelected(true);
    }

    private void addActionListeners()
    {
        buttonFinish.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                title = fieldTitle.getText();
                procedure = textAreaProcedure.getText();
                materials = textAreaMaterials.getText();
                objectives = textAreaObjectives.getText();
                
                if (cboxpH.isSelected())
                    dataTypes.add(TypeOfMeasurement.PH);
                if (cboxTemperature.isSelected())
                    dataTypes.add(TypeOfMeasurement.TEMP);
                if (cboxVoltage.isSelected())
                    dataTypes.add(TypeOfMeasurement.CONDUCT);

                // Hopefully this refers to the JFrame
                setVisible(false);
            }
        });
    }

    private void changeComponents()
    {
        LocalDateTime date = LocalDateTime.now();
        
        labelTextDate.setText(date.getMonth().toString() + " " + date.getDayOfMonth() + ", " + date.getYear());

        setPreferredSize(new java.awt.Dimension(720, 420));
        setSize(new java.awt.Dimension(720, 420));
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents()
    {

        separatorTitle = new javax.swing.JSeparator();
        labelTextDate = new javax.swing.JLabel();
        fieldTitle = new javax.swing.JTextField();
        buttonFinish = new javax.swing.JButton();
        scrollPaneObjectives = new javax.swing.JScrollPane();
        textAreaObjectives = new javax.swing.JTextArea();
        labelTextAbstract = new javax.swing.JLabel();
        scrollPaneProcedure = new javax.swing.JScrollPane();
        textAreaProcedure = new javax.swing.JTextArea();
        labelTextProcedure = new javax.swing.JLabel();
        scrollPaneMaterials = new javax.swing.JScrollPane();
        textAreaMaterials = new javax.swing.JTextArea();
        labelTextDataTypes = new javax.swing.JLabel();
        labelTextMaterials1 = new javax.swing.JLabel();
        cboxpH = new javax.swing.JCheckBox();
        cboxVoltage = new javax.swing.JCheckBox();
        cboxTemperature = new javax.swing.JCheckBox();
        buttonLoad = new javax.swing.JButton();
        buttonSave = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(700, 420));
        setSize(new java.awt.Dimension(700, 420));

        labelTextDate.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelTextDate.setText("date");

        fieldTitle.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        fieldTitle.setText("Experiment title");

        buttonFinish.setText("Finish");

        textAreaObjectives.setColumns(20);
        textAreaObjectives.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        textAreaObjectives.setRows(5);
        textAreaObjectives.setText("What are the objectives of this expt?\nWhat are we learning today?");
        scrollPaneObjectives.setViewportView(textAreaObjectives);

        labelTextAbstract.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        labelTextAbstract.setText("Abstract / Learning Objectives");

        textAreaProcedure.setColumns(20);
        textAreaProcedure.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        textAreaProcedure.setRows(5);
        scrollPaneProcedure.setViewportView(textAreaProcedure);

        labelTextProcedure.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        labelTextProcedure.setText("Procedure");

        textAreaMaterials.setColumns(20);
        textAreaMaterials.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        textAreaMaterials.setRows(5);
        textAreaMaterials.setText("What glassware and chemicals are needed\nfor this expt?");
        scrollPaneMaterials.setViewportView(textAreaMaterials);

        labelTextDataTypes.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        labelTextDataTypes.setText("Accepted Data Types");

        labelTextMaterials1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        labelTextMaterials1.setText("Materials");

        cboxpH.setText("pH");

        cboxVoltage.setText("Voltage");

        cboxTemperature.setText("Temperature");

        buttonLoad.setText("Load Experiment");

        buttonSave.setText("Save");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(fieldTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(labelTextDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(separatorTitle)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(scrollPaneProcedure, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(labelTextAbstract, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                            .addComponent(scrollPaneObjectives)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(labelTextProcedure)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(scrollPaneMaterials)
                            .addComponent(labelTextMaterials1)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(cboxTemperature))
                                            .addComponent(cboxpH)
                                            .addComponent(labelTextDataTypes, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(54, 54, 54))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(buttonLoad, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                                        .addGap(18, 18, 18)
                                        .addComponent(buttonSave, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(77, 77, 77)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(cboxVoltage)
                                    .addComponent(buttonFinish, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelTextDate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(separatorTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTextAbstract)
                    .addComponent(labelTextMaterials1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(scrollPaneObjectives, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelTextProcedure)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scrollPaneProcedure))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(scrollPaneMaterials, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelTextDataTypes)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboxpH)
                            .addComponent(cboxTemperature)
                            .addComponent(cboxVoltage))
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(buttonLoad, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonSave, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonFinish, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>

    // Variables declaration - do not modify                     
    private javax.swing.JButton buttonFinish;
    private javax.swing.JButton buttonLoad;
    private javax.swing.JButton buttonSave;
    private javax.swing.JCheckBox cboxTemperature;
    private javax.swing.JCheckBox cboxVoltage;
    private javax.swing.JCheckBox cboxpH;
    private javax.swing.JTextField fieldTitle;
    private javax.swing.JLabel labelTextAbstract;
    private javax.swing.JLabel labelTextDataTypes;
    private javax.swing.JLabel labelTextDate;
    private javax.swing.JLabel labelTextMaterials1;
    private javax.swing.JLabel labelTextProcedure;
    private javax.swing.JScrollPane scrollPaneMaterials;
    private javax.swing.JScrollPane scrollPaneObjectives;
    private javax.swing.JScrollPane scrollPaneProcedure;
    private javax.swing.JSeparator separatorTitle;
    private javax.swing.JTextArea textAreaMaterials;
    private javax.swing.JTextArea textAreaObjectives;
    private javax.swing.JTextArea textAreaProcedure;
    // End of variables declaration                   
}
