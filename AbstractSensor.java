import java.util.ArrayList;

// This is the base class for all types of sensors.
public abstract class AbstractSensor
{
    protected boolean measuringInstantly;

    // TODO: this may not be necessary to have in the base class.
    // Override the Object.toString() method by having the subclasses of AbstractSensor override them in different ways.
    @Override
    public abstract String toString();

    public abstract TypeOfMeasurement getType();

    protected AbstractSensor()
    {
        // Instant measurements are the default.
        measuringInstantly = true;
    }

    public boolean isMeasuringInstantly()
    {
        return measuringInstantly;
    }

    public void setMeasuringToInstant(boolean flag)
    {
        measuringInstantly = flag;
    }

    // Measure the reading as soon as the method is called
    // TODO MAke this method final.
    public Measurement instantMeasure(SerialConnection conn)
    {
        String output = "";
        
        try
        {
            output = conn.getData();
            
            while (output.charAt(0) != this.toString().charAt(0))
            {
                output = conn.getData();
            }
        }
        catch(SerialConnectionException e)
        {
            e.printStackTrace();
            //System.exit(1);
        }

        Measurement measure = null;

        try
        {
            measure = generateMeasurement(output);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return measure;
    }

    protected abstract Measurement generateMeasurement(String output);

    // TODO
    // Configure option to continously display results, or do it indefinitely
}