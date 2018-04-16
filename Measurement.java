import java.lang.Number;

/**
 * A Measurement wraps together a TypeOfMeasurement with a Number. It encapsulates them to represent a value with a certain meaning, which is usually a data
 * point describing the properties of a solution.
 *
 * @author      KBKrause
 * @see TypeOfMeasurement
 * @see Number
 * @since       1.8
 */
public class Measurement
{
    private TypeOfMeasurement type;
    private Number value;

    /**
     * Creates a Measurement with the specified type of measurement and value. There is no way to change these values.
     * 
     * @author      KBKrause
     * @param tom the TypeOfMeasurement that this number represents
     * @param v the numerical value of this measurement
     * @since       1.8
     */
    public Measurement(TypeOfMeasurement tom, Number v)
    {
        type = tom;
        value = v;
    }

    /**
     * Returns the value of this Measurement, which will be returned as either an int value or float value depending on the type of measurement.
     * 
     * @author      KBKrause
     * @return the value of this measurement
     * @since       1.8
     */
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

    /**
     * Creates a String that represents this Measurement, reported as TypeOfMeasurement >> value.
     * 
     * @author      KBKrause
     * @return the text representation of this Measurement
     * @since       1.8
     */
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