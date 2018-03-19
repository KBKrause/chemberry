import javax.swing.filechooser.*;
import java.io.PrintWriter;
import java.io.IOException;
import java.lang.StringBuilder;
import java.io.FileReader;
import java.io.BufferedReader;
import javax.swing.JFileChooser;

public class FileManipulator
{
    public static void saveFile(String filecontents)
    {
        JFileChooser jfc = new JFileChooser();
        int retVal = jfc.showSaveDialog(null);

        if (retVal == JFileChooser.APPROVE_OPTION)
        {
            try
            {
                PrintWriter writer = new PrintWriter(jfc.getSelectedFile());

                char[] arr = filecontents.toCharArray();

                for (char ch : arr)
                {
                    if (ch != '\n')
                    {
                        writer.print(ch);
                    }
                    else
                    {
                        writer.println();
                    }
                }

                writer.close();
            }
            catch(IOException ex)
            {
                ex.printStackTrace();
            }
        }
    }

    public static String loadFile()
    {
        StringBuilder strBuilder = new StringBuilder();

        JFileChooser jfc = new JFileChooser();
        int retVal = jfc.showOpenDialog(null);

        if (retVal == JFileChooser.APPROVE_OPTION)
        {
            try
            {         
                BufferedReader bfr = new BufferedReader(new FileReader(jfc.getSelectedFile()));

                String content;
                
                while ((content = bfr.readLine()) != null)
                {
                    strBuilder.append(content);
                    strBuilder.append("\n");
                }

                bfr.close();
            }
            catch(IOException ioe)
            {
                ioe.printStackTrace();
            }
        }

        return strBuilder.toString();
    }
}