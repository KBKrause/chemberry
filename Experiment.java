import java.util.ArrayList;
import java.util.HashSet;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.*;
import javax.swing.event.*;

public class Experiment 
{
    private String title;
    private String procedure;
    private String materials;
    private HashSet<TypeOfMeasurement> dataTypes;

    private JDialog experimentDialog;
    private JTextPane titleText;
    private JTextArea procText;
    private JTextArea materialsText;
    private JTextArea dataTypesText;

    private JDialog dialog_procedure;

    public Experiment(String title) 
    {
        this.title = title;
        dataTypes = new HashSet<TypeOfMeasurement>();
        procedure = "";
        materials = "";

        initializeDialogs();
    }

    public String getTitle() 
    {
        return title;
    }

    public String getProcedure() 
    {
        return procedure;
    }

    public String getMaterials() 
    {
        return materials;
    }

    // Returns each TypeOfMeasurement in dataTypes separated by newlines
    public String getDataTypes() 
    {
        String retval = "";

        for (TypeOfMeasurement tom : dataTypes) 
        {
            retval += tom.toString() + "\n";
        }

        return retval;
    }

    public void setProcedure(String s) 
    {
        procedure = s;
    }

    public void setMaterials(String s) 
    {
        materials = s;
    }

    public void addDataType(TypeOfMeasurement tom) 
    {
        dataTypes.add(tom);
    }

   public void setup() 
   {
       if (experimentDialog == null)
       {
           experimentDialog = new JDialog();
            experimentDialog.setLayout(new GridLayout(3, 1));
            experimentDialog.setSize(800, 800);
            experimentDialog.setLocation(100, 100);
            experimentDialog.setTitle("Chemberry - Experiment Design");
            experimentDialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);

            titleText = new JTextPane();
            titleText.setContentType("text/html");
            titleText.setText("<html><h1>" + title + "</h1></html>");
            //titleText.setEditable(false);
            titleText.setSize(200, 200);

            procText = new JTextArea(procedure);
            procText.setText("Use the 'Add Procedure' button to add a procedure to this area.\n" + 
                            "Keep the stepwise instructions short, simple, and easy to understand for the students.");
            JScrollPane scrl_procText = new JScrollPane(procText);
            procText.setEditable(false);

            materialsText = new JTextArea(materials);
            JScrollPane scrl_materialsText = new JScrollPane(materialsText);
            //materialsText.setEditable(false);

            JPanel dataTypesPanel = new JPanel();
            dataTypesPanel.setLayout(new GridLayout(2,1));
            dataTypesPanel.add(new JLabel("Select all measurements that are part of this experiment:"));
            JPanel btmOfData = new JPanel(new GridLayout(1, 3));

            JCheckBox chk_pH = new JCheckBox("pH");
            JCheckBox chk_volt = new JCheckBox("Voltage");
            JCheckBox chk_temp = new JCheckBox("Temperature");

            btmOfData.add(chk_pH);
            btmOfData.add(chk_volt);
            btmOfData.add(chk_temp);
            dataTypesPanel.add(btmOfData);

            JPanel one = new JPanel();
            one.setLayout(new GridLayout(1, 2));

            JPanel two = new JPanel();
            two.setLayout(new GridLayout(2, 1));

            two.add(scrl_materialsText);
            two.add(dataTypesPanel);

            one.add(scrl_procText);
            one.add(two);

            JPanel btm = new JPanel();
            btm.setLayout(new GridLayout(1, 3));
            
            JButton clearbtn = new JButton("Clear All");
            clearbtn.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    titleText.setText("<html><h1>Default title</h1></html>");
                    procText.setText("Use the 'Add Procedure' button to add a procedure to this area.\n" + 
                    "Keep the stepwise instructions short, simple, and easy to understand for the students.");
                    materialsText.setText("");

                    chk_pH.setSelected(false);
                    chk_volt.setSelected(false);
                    chk_temp.setSelected(false);
                }
            });

            JButton procbtn = new JButton("Add Procedure");

            procbtn.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    dialog_procedure.setVisible(true);
                }
            });

            JButton finalbtn = new JButton("Finish");
            finalbtn.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    title = titleText.getText();
                    procedure = procText.getText();
                    materials = materialsText.getText();
                    
                    if (chk_pH.isSelected())
                        dataTypes.add(TypeOfMeasurement.PH);
                    if (chk_temp.isSelected())
                        dataTypes.add(TypeOfMeasurement.TEMP);
                    if (chk_volt.isSelected())
                        dataTypes.add(TypeOfMeasurement.CONDUCT);
                    //experimentDialog.setVisible(false);
                }
            });

            btm.add(clearbtn);
            btm.add(procbtn);
            btm.add(finalbtn);

            experimentDialog.add(titleText);
            experimentDialog.add(one);
            experimentDialog.add(btm);

            experimentDialog.setVisible(true);
       }
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
       
       JButton finishProc = new JButton("Add Procedure to Experiment");
       finishProc.addActionListener(new ActionListener()
       {
           @Override
           public void actionPerformed(ActionEvent e) 
           {
               procText.setText(lhstxt.getText());
               dialog_procedure.setVisible(false);
           }
       });
       
       lhs_btm.add(finishProc);

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
}