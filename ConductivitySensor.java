import java.util.Random;
import java.util.ArrayList;

// Conductivity measured in siemens or microsiemens per centimeter (for our purposes).
public final class ConductivitySensor extends AbstractSensor
{
    public ConductivitySensor()
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
            
            while (output.charAt(0) != 'V')
            {
                output = conn.getData();
            }
        }
        catch(SerialConnectionException e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        
        Measurement measure = new Measurement(TypeOfMeasurement.CONDUCT, Float.parseFloat(output.substring(9, output.length() - 1)));

        return measure;
    }

    @Override
    public String toString()
    {
        return "Conductivity sensor";
    }

    @Override
    public TypeOfMeasurement getType()
    {
        return TypeOfMeasurement.CONDUCT;
    }
}