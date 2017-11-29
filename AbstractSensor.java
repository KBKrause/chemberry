import java.util.ArrayList;

// This is the base class for all types of sensors.
public abstract class AbstractSensor
{
    protected boolean measuringInstantly;

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
    public abstract Measurement instantMeasure();

    // TODO
    // Configure option to continously display results, or do it indefinitely
}