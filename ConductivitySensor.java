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
    public Measurement instantMeasure()
    {
        Random r = new Random();
        Measurement measure = new Measurement(TypeOfMeasurement.CONDUCT, r.nextInt() % 10000);

        return measure;
    }

    @Override
    public String toString()
    {
        return "Conductivity sensor";
    }
}