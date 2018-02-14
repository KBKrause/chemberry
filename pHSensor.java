import java.util.Random;

// pH measured in "powers of hydrogen." This usually reported as a decimal, eg. 3.4, 3.5, etc.
public final class pHSensor extends AbstractSensor
{
    public pHSensor()
    {
        super();
    }

    @Override
    public Measurement instantMeasure(SerialConnection conn)
    {
        String output = "";
        
        try
        {
            output = conn.getData();

            while (output.charAt(0) != 'p')
            {
                output = conn.getData();
            }
        }
        catch(SerialConnectionException e)
        {
            e.printStackTrace();
            System.exit(1);
        }

        Measurement measure = new Measurement(TypeOfMeasurement.PH, Float.parseFloat(output.substring(3)));
        
        return measure;
    }

    @Override
    public String toString()
    {
        return "pH probe";
    }
}