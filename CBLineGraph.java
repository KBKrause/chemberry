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

/**
 * A CBLineGraph is a visual representation of data collected by students. It is a 2D line graph that plots the indices (x-axis) of an array of measurements
 * against their values (y-axis). The graphing functionality is provided by JFreeChart, so this acts as a kind of wrapper.
 * This class extends ApplicationFrame and still needs an interface.
 * 
 * @author      KBKrause
 * @since       1.8
 */
public class CBLineGraph extends ApplicationFrame 
{
    private TypeOfMeasurement measurementType;
    private ChartPanel graphArea;

    /** 
     * Instantiate a single line graph from one set of data. To render the graph, you must call {@link #displayChart()}.
     * 
     * @param windowTitle the title of the dialog that will contain the graph
     * @param chartTitle the title of the chart placed above the graph
     * @param tom the type of measurement being graphed, eg. pH, conductivity, temperature
     * @param measurements the values being plotted
     * @see TypeOfMeasurement
     * @since           1.8
     */
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

    /** 
     * Create a JFrame containing the line graph. The data used comes from the parameter passed to the constructor.
     * For each new graph, a new CBLineGraph must be created.
     * @see JFrame
     * @since           1.8
     */
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
}