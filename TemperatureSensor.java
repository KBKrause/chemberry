import java.util.Random;

// Kelvin or Celsius always a float.
public final class TemperatureSensor extends AbstractSensor
{
    public TemperatureSensor()
    {
        super();
    }

    @Override
    protected Measurement generateMeasurement(String output)
    {
        return new Measurement(TypeOfMeasurement.TEMP, Float.parseFloat(output.substring(5)));
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