public enum TypeOfMeasurement
{
    PH, CONDUCT, TEMP;

    @Override
    public String toString()
    {
        String retval;

        if (this.equals(PH))
        {
            retval = "pH";
        }
        else if (this.equals(CONDUCT))
        {
            retval = "millivolts";
        }
        else
        {
            // TODO Change this literal to "temperature."
            // It's currently "t_celsius" because the output string of the arduino is "temp: "
            retval = "t_celsius";
        }

        return retval;
    }
}