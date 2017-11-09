// This is the base class for all types of sensors.
public abstract class AbstractSensor
{
    // Measure the reading as soon as the method is called
    public abstract Measurement instantMeasure();
    /*  A measurement can be taken every n seconds for duration x with this method.
    */
    //public abstract ArrayList <Measurement> intervalMeasure(int everyNSeconds, int duration);
}