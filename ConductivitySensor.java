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
    protected Measurement generateMeasurement(String output)
    {
        return new Measurement(TypeOfMeasurement.CONDUCT, Float.parseFloat(output.substring(9, output.length() - 1)));
    }

    @Override
    public String toString()
    {
        return "Voltage sensor";
    }

    @Override
    public TypeOfMeasurement getType()
    {
        return TypeOfMeasurement.CONDUCT;
    }
}