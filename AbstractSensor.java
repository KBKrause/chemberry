import java.util.ArrayList;

// This is the base class for all types of sensors.
public abstract class AbstractSensor
{
    // TODO
    // The measure() method will call either instantMeasure() or intervalMeasure() depending on the state of the sensor.
    // This way, useres of a sensor will just call measure() and not need to worry about which measurement function to call.
    // public void measure()
    // Measure the reading as soon as the method is called
    public abstract Measurement instantMeasure();
    /*  A measurement can be taken every n seconds for duration x with this method.
    */
    public ArrayList <Measurement> intervalMeasure(int everyNSeconds, int duration)
    {
        ArrayList <Measurement> measurements = new ArrayList<Measurement>();

        try
        {
            // Thread.sleep(long n) -- wait n milliseconds (1000 milliseconds = 1 second)

            for (int i = 0; i < duration; i++)
            {
                measurements.add(instantMeasure());
                System.out.println("Sensor measured: " + instantMeasure());
                Thread.sleep(everyNSeconds * 1000);
            }
        }
        catch(InterruptedException ie)
        {
            ie.printStackTrace();
        }

        return measurements;
    }
}