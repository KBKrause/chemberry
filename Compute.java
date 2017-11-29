import java.util.ArrayList;
import java.lang.Math;

public abstract class Compute
{
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

    // std = standard deviation (of the sample, n-1 in divisor).
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