// pH measured in "powers of hydrogen." This usually reported as a decimal, eg. 3.4, 3.5, etc.
public final class pHSensor extends AbstractSensor
{
    public pHSensor()
    {
        super();
    }

    @Override
    protected Measurement generateMeasurement(String output)
    {
        return new Measurement(TypeOfMeasurement.PH, Float.parseFloat(output.substring(3)));
    }
    
    @Override
    public String toString()
    {
        return "pH probe";
    }

    @Override
    public TypeOfMeasurement getType()
    {
        return TypeOfMeasurement.PH;
    }
}