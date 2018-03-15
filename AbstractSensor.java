import java.util.ArrayList;

/**
 * The AbstractSensor represents any type of physical sensor connected to the Arduino. The commonality between all of the sensors is
 * grouped into this class. Future sensors should inherit from this class in the form
 * <code> public final {<i>sensorName</i>} extends AbstractSensor </code>
 * 
 * Examples include
 * <ul>
 * <li> pHSensor
 * <li> conductivity probe
 * <li> temperature probe
 * </ul>
 * 
 * @author      kevbkraus
 * @since       1.8
 */
public abstract class AbstractSensor
{
    protected boolean measuringInstantly;

    /** 
     * Returns the type of measurement being recorded by this sensor contained within the <code>TypeOfMeasurement</code> enum.
     * 
     * @return          a value from the TypeOfMeasurement enum
     * @since           1.8
     */
    public abstract TypeOfMeasurement getType();

    /** 
     * A call to <code>super</code> should be used in all concrete sensors to set its {@link #measuringInstantly} member.
     * 
     * @since           1.8
     */
    protected AbstractSensor()
    {
        // Instant measurements are the default.
        measuringInstantly = true;
    }

    /** 
     * Returns the value of {@link #measuringInstantly}, indicating whether or not this sensor has been set to measure in intervals or instantaneously.
     * 
     * @return          <code>true</code> if this sensor is not measuring over intervals of time,
     *                  <code>false</code> if otherwise
     * @since           1.8
     */
    public boolean isMeasuringInstantly()
    {
        return measuringInstantly;
    }

    /** 
     * Changes the state of this sensor to be used continuously or only once in a while.
     * 
     * @param flag      <code>true</code> or <code>false</code> to identify if this sensor
     *                  is set to measure in intervals or not
     * @since           1.8
     */
    public void setMeasuringToInstant(boolean flag)
    {
        measuringInstantly = flag;
    }

     /** 
     * Looks at the serial monitor of the Arduino board and retrieves the most recent reading of the corresponding
     * sensor. This method takes a string and passes it to <code>generateMeasurement</code> to be parsed. This
     * correspondence between the methods is desired because each measurement needs to be parsed differently
     * depending on the measurement being reported, eg. pH measurements are printed to the serial monitor in
     * a slightly different format than conductivity measurements.
     * <p>
     * More robust solutions should override this method.
     * 
     * @param conn      the handle of the Arduino
     * @return          a Measurement representative of the most recent or buffered data transmitted by the Arduino
     * @see             SerialConnection
     * @see             Measurement
     * @since           1.8
     */
    public Measurement instantMeasure(SerialConnection conn)
    {
        String output = "";
        
        try
        {
            output = conn.getData();
            
            while (output.charAt(0) != this.toString().charAt(0))
            {
                output = conn.getData();
            }
        }
        catch(SerialConnectionException e)
        {
            e.printStackTrace();
            //System.exit(1);
        }

        Measurement measure = null;

        try
        {
            measure = generateMeasurement(output);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return measure;
    }

     /** 
     * This is the abstract method used by <code>instantMeasure</code> and overridden by concrete sensor classes. It creates
     * the actual <code>Measurement</code> that is returned by <code>instantMeasure</code>. 
     *
     * @return          a Measurement representative of the most recent or buffered data transmitted by the Arduino
     * @see             Measurement
     * @since           1.8
     */
    protected abstract Measurement generateMeasurement(String output);
}