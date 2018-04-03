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
   }
}