import java.util.Random;
import java.util.ArrayList;

/**
 * The <code>ConductivitySensor</code> is a type of <code>AbstractSensor</code>. It measures the voltage of solutions; measurements are reported in
 * units of V (volts). As with the other sensors, this class expects a certain format to be printed by the arduino board when it measures
 * from one if its analog inputs.
 * 
 * @author      KBKrause
 * @see         AbstractSensor
 * @since       1.8
 */
public final class ConductivitySensor extends AbstractSensor
{
    /** 
     * The default constructor calls <code>super()</code> to <code>AbstractSensor</code>.
     * 
     * @since           1.8
     */
    public ConductivitySensor()
    {
        super();
    }

    @Override
    protected Measurement generateMeasurement(String output)
    {
        return new Measurement(TypeOfMeasurement.CONDUCT, Float.parseFloat(output.substring(9, output.length() - 1)));
    }

    /** 
     * Returns a string literal describing this conductivity sensor.
     * 
     * @return the string "Voltage sensor"
     * @since           1.8
     */
    @Override
    public String toString()
    {
        return "Voltage sensor";
    }

    /** 
     * Returns the type of data being collected by this conductivity sensor.
     * 
     * @return {@value TypeOfMeasurement#CONDUCT}
     * @since           1.8
     */
    @Override
    public TypeOfMeasurement getType()
    {
        return TypeOfMeasurement.CONDUCT;
    }
}