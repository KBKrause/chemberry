import java.util.ArrayList;

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
}