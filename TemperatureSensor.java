import java.util.Random;

// Kelvin or Celsius always a float.
public final class TemperatureSensor extends AbstractSensor
{
    public TemperatureSensor()
    {
        
    }

    @Override
    public Measurement instantMeasure()
    {
        Random r = new Random();
        Measurement measure = new Measurement(TypeOfMeasurement.TEMP, r.nextFloat());
        
        return measure;
    }

    @Override
    public String toString()
    {
        return "Temperature";
    }
}