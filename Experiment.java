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
    private JTextField titleText;
    private JTextArea procText;
    private JTextArea materialsText;
    private JTextArea dataTypesText;

    public Experiment(String title) 
    {
        this.title = title;
        dataTypes = new HashSet<TypeOfMeasurement>();
        procedure = "";
        materials = "";
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

    public String stringifyDataTypes() 
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
        experimentDialog = new JDialog();
        experimentDialog.setLayout(new GridLayout(3, 1));
        experimentDialog.setSize(800, 800);
        experimentDialog.setLocation(100, 100);
        experimentDialog.setTitle("Chemberry - " + title);
        experimentDialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);

        titleText = new JTextField(title);
        //titleText.setEditable(false);
        titleText.setSize(600, 200);

        procText = new JTextArea(procedure);
        JScrollPane scrl_procText = new JScrollPane(procText);
        procText.setEditable(false);

        materialsText = new JTextArea(materials);
        JScrollPane scrl_materialsText = new JScrollPane(materialsText);
        //materialsText.setEditable(false);

        dataTypesText = new JTextArea(stringifyDataTypes());
        JScrollPane scrl_dataTypesText = new JScrollPane(dataTypesText);
        dataTypesText.setEditable(false);

        JPanel one = new JPanel();
        one.setLayout(new GridLayout(1, 2));

        JPanel two = new JPanel();
        two.setLayout(new GridLayout(2, 1));

        two.add(scrl_materialsText);
        two.add(scrl_dataTypesText);

        one.add(scrl_procText);
        one.add(two);

        JPanel btm = new JPanel();
        btm.setLayout(new GridLayout(1, 3));
        
        JButton clearbtn = new JButton("Clear All");
        JButton procbtn = new JButton("Add Procedure");

        // TODO continue building GUI here for instructor

        experimentDialog.add(titleText);
        experimentDialog.add(one);
        experimentDialog.setVisible(true);
   }

    public void render() 
    {

    }
}