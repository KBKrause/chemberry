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
            retval = "t_celsius";
        }

        return retval;
    }
}