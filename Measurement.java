import java.lang.Number;

public class Measurement
{
    private TypeOfMeasurement type;
    private Number value;

    public Measurement(TypeOfMeasurement tom, Number v)
    {
        type = tom;
        value = v;
    }

    public Number getValue()
    {
        if (type.equals(TypeOfMeasurement.CONDUCT))
        {
            return value.intValue();
        }
        else
        {
            return value.floatValue();
        }
    }
}