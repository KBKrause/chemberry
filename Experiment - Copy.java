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
    private JDialog experimentDialog;
    private JPanel btm;
    private JPanel one;
    private JTextPane titleText;
    private JTextArea procText;
    private JTextArea materialsText;
    private JCheckBox chk_pH;
    private JCheckBox chk_volt;
    private JCheckBox chk_temp;

    private JDialog dialog_procedure;

    // TODO the private fields might not be needed.
    // TODO When an experiment is instantiated and you know ahead of time that there is an identified procedure and materials,
    // TODO add abstract / objectives
    //      you end up needing to call the set methods to update the UI. Add another constructor(s) to set these ahead of time.
    //      Add an update method or updateUI() to reset GUI appearance.
    public Experiment(String title) 
    {
        this.title = title;
        dataTypes = new HashSet<TypeOfMeasurement>();
        procedure = "";
        materials = "";

        initializeDialogs();
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

   public void showSetup() 
   {
       //btm.setVisible(true);
       experimentDialog.setVisible(true);
   }

   public void studentDisplay()
   {
       JDialog studentDialog = new JDialog();
       studentDialog.setTitle("Chemberry - Student Experiment");
       studentDialog.setLayout(new GridLayout(2, 1));
       //btm.setVisible(false);
       titleText.setEditable(false);
       procText.setEditable(false);
       materialsText.setEditable(false);

       chk_pH.setEnabled(false);
       chk_temp.setEnabled(false);
       chk_volt.setEnabled(false);

      //experimentDialog.remove(btm);
       //experimentDialog.validate();

       studentDialog.setSize(600, 600);
       studentDialog.add(titleText);
       studentDialog.add(one);

       studentDialog.setVisible(true);
   }

   private void initializeDialogs()
   {
       // Procedure dialog //
       dialog_procedure = new JDialog();
       dialog_procedure.setTitle("Experimental Procedure Settings");

       dialog_procedure.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);

       JPanel rhs = new JPanel();
       rhs.setLayout(new GridLayout(3, 1));

       JTextArea lhstxt = new JTextArea();
       lhstxt.setText("No procedure identified");
       lhstxt.setEditable(false);

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

       // Experiment dialog //
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

       chk_pH = new JCheckBox("pH");

       chk_volt = new JCheckBox("Voltage");

       chk_temp = new JCheckBox("Temperature");

       btmOfData.add(chk_pH);
       btmOfData.add(chk_volt);
       btmOfData.add(chk_temp);
       dataTypesPanel.add(btmOfData);

       one = new JPanel();
       one.setLayout(new GridLayout(1, 2));

       JPanel two = new JPanel();
       two.setLayout(new GridLayout(2, 1));

       two.add(scrl_materialsText);
       two.add(dataTypesPanel);

       one.add(scrl_procText);
       one.add(two);

       JPanel btm = new JPanel();
       btm.setLayout(new GridLayout(1, 3));

       btm.add(clearbtn);
       btm.add(procbtn);
       btm.add(finalbtn);

       experimentDialog.add(titleText);
       experimentDialog.add(one);
       experimentDialog.add(btm);
   }
}