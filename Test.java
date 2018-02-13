import java.util.ArrayList;
import java.time.LocalDateTime;

public class Test
{
    public static void main(String[] args)
    {
        System.out.println("Starting tests");

        ArrayList < Double > vec = new ArrayList < Double > ();
        vec.add(2.0);
        vec.add(1.0);
        vec.add(3.0);

        System.out.println("The average is " + Compute.average(vec) + " and the std is " + Compute.std(vec));
    }
}