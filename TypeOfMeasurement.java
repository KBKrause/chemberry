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
            retval = "volts";
        }
        else
        {
            retval = "celsius";
        }

        return retval;
    }
}