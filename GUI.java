import java.awt.GridLayout;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;

public class GUI extends JFrame
{
    // Config screen and settings screen
    private JDialog GUIconfig;
    private JDialog GUIsettings;

    private JTextArea dataTextArea;
    private JTextArea debugTextArea;

    public GUI()
    {
        super("Chemberry");

        // Initializers
        initializeTextAreas();
        initializeFrames();

        this.setSize(1000, 1000);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(100, 100);

        JButton saveData = new JButton();
        saveData.setText("Save");
        JButton openData = new JButton();
        openData.setText("Open");
        JButton clearData = new JButton();
        clearData.setText("Clear");

        JPanel mainRow = new JPanel();
        mainRow.setLayout(new GridLayout(1, 3));

        mainRow.add(dataTextArea);
        mainRow.add(new JSeparator(SwingConstants.VERTICAL));
        mainRow.add(debugTextArea);

        JPanel bottomRow = new JPanel();
        bottomRow.setLayout(new GridLayout(1, 3));

        bottomRow.add(saveData);
        bottomRow.add(openData);
        bottomRow.add(clearData);

        JPanel topRow = new JPanel();
        topRow.setLayout(new GridLayout(1, 3));

        JButton dataBtn = new JButton();
        dataBtn.setText("Data Collection");
        dataBtn.addActionListener(new ActionListener()
        {   
            @Override
            public void actionPerformed(ActionEvent e)
            {
                // TODO
                // What goes here?
                //GUIconfig.setVisible(false);
                //GUIsettings.setVisible(false);
                //topRow.setVisible(true);
                //menuBar.setVisible(true);
            }
        });

        JButton configBtn = new JButton();
        configBtn.setText("Configuration");
        configBtn.addActionListener(new ActionListener()
        {   
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //GUIdata.setVisible(false);
                //GUIdata.setVisible(false);
                //topRow.setVisible(true);
                //menuBar.setVisible(true);
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
                //GUIconfig.setVisible(false);
                //GUIdata.setVisible(false);
                //topRow.setVisible(true);
                //menuBar.setVisible(true);
                GUIsettings.setVisible(true);
            }
        });

        // Add all the buttons to the panel
        topRow.add(dataBtn);
        topRow.add(configBtn);
        topRow.add(settingsBtn);

        // Set the layout for GUI with 3 rows and 0 columns.
        // The GUI can append one component per row-column intersection.
        this.setLayout(new GridLayout(3, 1));
        this.add(topRow);
        this.add(mainRow);
        this.add(bottomRow);

        setVisible(true);
    }

    private void initializeTextAreas()
    {
        dataTextArea = new JTextArea();
        debugTextArea = new JTextArea();

        dataTextArea.setEditable(false);
        debugTextArea.setEditable(false);
    }

    private void initializeFrames()
    {
        GUIconfig = new JDialog();
        GUIsettings = new JDialog();

        GUIconfig.setSize(1000, 1000);
        GUIconfig.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        GUIconfig.setLocation(100, 100);

        // TODO
        // Add inet address to settings dialog
        // Add server port to settings dialog
        // Add my port to settings dialog

        GUIsettings.setSize(1000, 1000);
        GUIsettings.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        GUIsettings.setLocation(100, 100);

        String addr = InetProxy.getMyAddress();

        if (addr != "null")
        {
            debugTextArea.append(">> Internet (IPv4) address retrieved: " + addr);
        }
        else
        {
            System.out.println("ERROR: Unknown Host Exception");
            debugTextArea.append(">> ERROR: Unable to resolve IPv4 address");
        }
    }
}