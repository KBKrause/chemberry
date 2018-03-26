public class GUIListeningProxy extends ServerProxy
{
    private GUIInterface gui;

    public GUIListeningProxy(int p, GUIInterface g)
    {
        super(p);
        gui = g;
    }

    @Override
    public void handleRequest(String request, String clientIP)
    {
        System.out.println("Received this request: " + request);
        String decodedRequest = Inet.decodeUpdate(request);

        String tokens[] = decodedRequest.split(":");
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
        else if (tokens[0].equals("bp"))
        {
            System.out.println("Received this broadcasted procedure: " + tokens[1]);
            gui.setProcedure(tokens[1]);
        }
        else if (tokens[0].equals("exp"))
        {
            // [1]title -- [2]proc -- [3]mats -- [4]types
            //System.out.println(tokens[1]);
            //System.out.println(tokens[2]);
            //System.out.println(tokens[3]);
            //System.out.println(tokens[4]);

            Experiment e = new Experiment(tokens[1]);
            e.setProcedure(tokens[2]);
            e.setMaterials(tokens[3]);
            
            String[] tokenizedDataTypes = tokens[4].split("\n");

            for (String s : tokenizedDataTypes)
            {
                if (s.equals("pH"))
                    e.addDataType(TypeOfMeasurement.PH);
                else if (s.equals("celsius"))
                    e.addDataType(TypeOfMeasurement.CONDUCT);
                else if (s.equals("volts"))
                    e.addDataType(TypeOfMeasurement.TEMP);
            }

            gui.setExperiment(e);
        }
    }
}