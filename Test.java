import java.util.ArrayList;

public class Test
{
    public static void main(String[] args)
    {
        System.out.println("Starting tests");
        testIntervalMeasurements();
    }

    public static void testInstantMeasurements()
    {
        AbstractSensor sensor = new TemperatureSensor();
        
        for (int i = 0; i < 10; i++)
        {
            printMeasurement(sensor);
        }

        sensor = new pHSensor();

        for (int i = 0; i < 10; i++)
        {
            printMeasurement(sensor);
        }

        sensor = new ConductivitySensor();

        for (int i = 0; i < 10; i++)
        {
            printMeasurement(sensor);
        }
    }

    public static void testIntervalMeasurements()
    {
        AbstractSensor sensor = new ConductivitySensor();

        ArrayList<Measurement> meas = sensor.intervalMeasure(2, 60);
    }

    private static void printMeasurement(AbstractSensor sensor)
    {
        System.out.println("Sensor reads: " + sensor.instantMeasure().getValue());
    }
}