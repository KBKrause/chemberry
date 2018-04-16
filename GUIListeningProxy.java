/**
 * GUIListeningProxy is a type of ServerProxy. It is the class that listens for requests on behalf of the student's program.
 * 
 * @author      KBKrause
 * @since       1.8
 */
public class GUIListeningProxy extends ServerProxy
{
    private GUIInterface gui;

    /**
     * Creates, but does not start, a GUIListeningProxy that listens on the specified port number and interacts with the specified GUI.
     * 
     * @param p the port number to listen on
     * @param g an interface, or reference, to the client GUI
     * @author      KBKrause
     * @since       1.8
     */
    public GUIListeningProxy(int p, GUIInterface g)
    {
        super(p);
        gui = g;
    }

    // TODO
    // Javadoc here?
    @Override
    public void handleRequest(String request, String clientIP)
    {
        //System.out.println("Received this request: " + request);
        String decodedRequest = Inet.decodeUpdate(request);

        String tokens[] = decodedRequest.split(":");
        //System.out.println("Decoded and tokenized request:");

        for (int i = 0; i < tokens.length; i++)
        {
            System.out.println("tokens[" + i + "]:" + tokens[i]);
        }
        //System.out.println(tokens);

        if (tokens[0].equals("d") && (tokens[1].equals("esync")))
        {
            gui.appendDebugText("WARNING: The remote server has disconnected");
            //gui.setNetworking(false);
        }
        else if (tokens[0].equals("u"))
        {
            System.out.println("Instructor says: " + tokens[1]);
        }
        else if (tokens[0].equals("exp"))
        {
            // [1]title -- [2]obj -- [3]proc -- [4]mats -- [5]types
            //System.out.println(tokens[1]);
            //System.out.println(tokens[2]);
            //System.out.println(tokens[3]);
            //System.out.println(tokens[4]);

            ExperimentGUI e = new ExperimentGUI();
            e.setVisible(false);

            e.setTitle(tokens[1]);
            e.setObjectives(tokens[2]);
            e.setProcedure(tokens[3]);
            e.setMaterials(tokens[4]);
            
            String[] tokenizedDataTypes = tokens[5].split("\n");

            for (String s : tokenizedDataTypes)
            {
                System.out.println("next tom: " + s);
                if (s.equals("pH"))
                    e.addDataType(TypeOfMeasurement.PH);
                else if (s.equals("celsius"))
                    e.addDataType(TypeOfMeasurement.TEMP);
                else if (s.equals("volts"))
                    e.addDataType(TypeOfMeasurement.CONDUCT);
            }

            gui.setExperiment(e);
        }
    }
}