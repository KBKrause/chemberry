import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import javax.swing.JFrame;
import java.util.HashSet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kevin
 */
public class ExperimentGUI extends javax.swing.JFrame
{
    private String title;
    private String procedure;
    private String materials;
    private String objectives;
    private HashSet<TypeOfMeasurement> dataTypes;

    // TODO add save / load for experiments, don't forget file extension
    // TODO don't show visible on load
    /**
     * Creates new form ExperimentGUI
     */
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

        this.setVisible(true);
    }

    public String getTitle() 
    {
        return title;
    }

    public String getObjectives()
    {
        return objectives;
    }

    public String getProcedure() 
    {
        return procedure;
    }

    public String getMaterials() 
    {
        return materials;
    }

    public String getDataTypes() 
    {
        String retval = "";

        for (TypeOfMeasurement tom : dataTypes) 
        {
            retval += tom.toString() + "\n";
        }

        return retval;
    }

    public void setTitle(String s)
    {
        fieldTitle.setText(s);
        title = s;
    }

    public void setObjectives(String s)
    {
        textAreaObjectives.setText(s);
        objectives = s;
    }

    public void setProcedure(String s) 
    {
        //System.out.println("Setting proc to " + s);
        textAreaProcedure.setText(s);
        procedure = s;
        //System.out.println("Procedure = " + procedure);
    }

    public void setMaterials(String s) 
    {
        //System.out.println("Setting mats to " + s);
        textAreaMaterials.setText(s);
        materials = s;
        //System.out.println("materials = " + materials);
    }

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
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
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
        setPreferredSize(new java.awt.Dimension(700, 400));
        setSize(new java.awt.Dimension(700, 400));

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
