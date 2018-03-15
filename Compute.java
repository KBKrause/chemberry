import java.util.ArrayList;
import java.lang.Math;

/**
 * The <code>Compute</code> class handles and manages various calculations and statistics. Most, if not all, methods
 * are public and static.
 * 
 * @author      kevbkraus
 * @since       1.8
 */
public abstract class Compute
{
    /**
     * Returns the arithmetic mean or average of a collection of values.
     * 
     * @param values the values that need to be averaged
     * @return      the arithmetic average of the <code>values</code>' values
     * @since       1.8
     */
    public static double average(ArrayList <Double> values)
    {
        double retval = 0;

        for (int i = 0; i < values.size(); i++)
        {
            retval += values.get(i);
        }

        retval /= values.size();

        return retval;
    }

    /**
     * Returns the standard deviation of the sample (meaning that the divisor is size <code>n-1</code>).
     * 
     * @param values the values to be analyzed
     * @return      standard deviation
     * @since       1.8
     */
    public static double std(ArrayList <Double> values)
    {
        double avg = average(values);
        double differences = 0.0;

        for (int i = 0; i < values.size(); i++)
        {
            differences += Math.pow(values.get(i) - avg, 2);
        }

        differences /= values.size() - 1;

        return (Math.sqrt(differences));
    }
}