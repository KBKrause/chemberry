import java.util.ArrayList;

// This is the base class for all types of sensors.
public abstract class AbstractSensor
{
    protected boolean measuringInstantly;

    // TODO: this may not be necessary to have in the base class.
    // Override the Object.toString() method by having the subclasses of AbstractSensor override them in different ways.
    @Override
    public abstract String toString();

    protected AbstractSensor()
    {
        // Instant measurements are the default.
        measuringInstantly = true;
    }

    public boolean isMeasuringInstantly()
    {
        return measuringInstantly;
    }

    public void setMeasuringToInstant(boolean flag)
    {
        measuringInstantly = flag;
    }

    // Measure the reading as soon as the method is called
    public abstract Measurement instantMeasure(SerialConnection conn);

    // TODO
    // Configure option to continously display results, or do it indefinitely
}