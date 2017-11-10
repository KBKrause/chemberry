import java.lang.Number;

// TODO
// PROBLEM: Colon is separating tokens. Yikes.
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

    @Override
    public String toString()
    {
        String theString = "";
     
        switch (type)
        {
            case PH :
            theString = theString.concat("pH >> ");
            break;

            case CONDUCT :
            theString = theString.concat("Conductivity >> ");
            break;

            case TEMP :
            theString = theString.concat("Temperature >> ");
        }

        theString = theString.concat(getValue().toString());

        return theString;
    }
}