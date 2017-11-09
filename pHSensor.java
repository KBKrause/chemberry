import java.util.Random;

// pH measured in "powers of hydrogen." This usually reported as a decimal, eg. 3.4, 3.5, etc.
public final class pHSensor extends AbstractSensor
{
    public pHSensor()
    {
        
    }

    public Measurement instantMeasure()
    {
        Random r = new Random();
        Measurement measure = new Measurement(TypeOfMeasurement.PH, r.nextFloat());
        
        return measure;
    }

    @Override
    public String toString()
    {
        return "pH";
    }
}