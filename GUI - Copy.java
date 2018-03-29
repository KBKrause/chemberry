public class GUI extends JFrame
{
    public GUI(boolean networking, String instructorIP) throws ChemberryException
    {
        //initialize();
        initializeConfig();
        initializeTextAreas();
        initializeSettings();

        initializeOther();
        //controlPanel.add(measurementBtn);
        settingsBtn.setText("Settings");
        settingsBtn.addActionListener(new ActionListener()
        {   
            @Override
            public void actionPerformed(ActionEvent e)
            {
                GUIsettings.setVisible(true);
            }
        });

        JButton procedureBtn = new JButton("Experiment Details");
        procedureBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                if (currentExperiment != null)
                {
                    currentExperiment.studentDisplay();
                }
                else
                {
                    appendDebugText("No experiment has been forwarded by the instructor");
                }
            }
        });
        topRow.add(procedureBtn);

        setVisible(true);
    }

    private void initializeSettings()
    {
        // TODO
        // Set up layout manager for GUIsettings
        GUIsettings = new JDialog();
        GUIsettings.setTitle("Chemberry - Settings");
        GUIsettings.setLayout(new GridLayout());
        GUIsettings.setSize(1000, 1000);
        GUIsettings.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        GUIsettings.setLocation(150, 150);

        // TODO
        // Fix this so that it is not null.
        JTextField IPaddr = new JTextField();
        JTextField port = new JTextField();

        if (networkingAllowed)
        {
            port = new JTextField("Listening Port: 9648");

            try
            {
                IPaddr.setText("Address: " + Inet.getMyAddress());
            }
            catch(ChemberryException cbe)
            {
                inetError();
                closeFrame();
            }
        }
        else
        {
            IPaddr.setText("IPv4 address disabled");
            port.setText("Port disabled");
        }
        
        IPaddr.setEditable(false);
        port.setEditable(false);

        GUIsettings.setLayout(new GridLayout(2, 1));
        GUIsettings.add(IPaddr);
        GUIsettings.add(port);
    }
}
