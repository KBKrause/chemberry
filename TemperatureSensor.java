import java.util.Random;

// Kelvin or Celsius always a float.
public final class TemperatureSensor extends AbstractSensor
{
    public TemperatureSensor()
    {
        super();
    }

    @Override
    public Measurement instantMeasure(SerialConnection conn)
    {
        Random r = new Random();
        Measurement measure = new Measurement(TypeOfMeasurement.TEMP, r.nextFloat());
        
        return measure;
    }

    @Override
    public String toString()
    {
        return "Temperature sensor";
    }

    @Override
    public TypeOfMeasurement getType()
    {
        return TypeOfMeasurement.TEMP;
    }
}