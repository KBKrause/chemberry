import java.util.ArrayList;

// This is the base class for all types of sensors.
public abstract class AbstractSensor
{
    protected boolean measuringInstantly;

    @Override
    public abstract String toString();

    protected AbstractSensor()
    {
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
    // TODO
    // The measure() method will call either instantMeasure() or intervalMeasure() depending on the state of the sensor.
    // This way, useres of a sensor will just call measure() and not need to worry about which measurement function to call.
    // public void measure()
    // Measure the reading as soon as the method is called
    public abstract Measurement instantMeasure();

    // TODO
    // Configure option to continously display results, or do it indefinitely
}