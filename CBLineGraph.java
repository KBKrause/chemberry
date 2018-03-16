import org.jfree.chart.ChartPanel;
import java.util.ArrayList;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import java.awt.BorderLayout;

public class CBLineGraph extends ApplicationFrame 
{
    private TypeOfMeasurement measurementType;
    private ChartPanel graphArea;

    
    public CBLineGraph(String windowTitle, String chartTitle, TypeOfMeasurement tom, ArrayList <Number> measurements) 
    {
        super(windowTitle);
        measurementType = tom;
        // x axis title, y axis title
        JFreeChart lineChart = ChartFactory.createLineChart(chartTitle, "", "Value (" + tom.toString() + ")", createDataset(measurements), PlotOrientation.VERTICAL, true, true, false);
        ChartPanel chartPanel = new ChartPanel(lineChart);
        graphArea = chartPanel;
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        setContentPane(chartPanel);
    }

    public void displayChart()
    {
        JFrame jf = new JFrame("Chemberry Data Visualization");
        jf.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        jf.setLayout(new BorderLayout(0, 5));
        jf.add(graphArea, BorderLayout.CENTER);
        jf.pack();
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);
    }
    
    private DefaultCategoryDataset createDataset(ArrayList <Number> measurements) 
    {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset( );

        for (int i = 0; i < measurements.size(); i++)
        {
            dataset.addValue(measurements.get(i), measurementType.toString(), i);
        }
       
        return dataset;
    }
       /*
       public static void main( String[ ] args ) {
          CBLineGraph chart = new CBLineGraph(
              // window title
             "School Vs Years" ,
             // chart title
             "Numer of Schools vs years");
    
          chart.pack( );
          RefineryUtilities.centerFrameOnScreen( chart );
          chart.setVisible( true );
       }
       */
    }