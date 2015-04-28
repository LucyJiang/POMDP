package test;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RefineryUtilities;

public class LineChart_IterationTimeComparision extends ApplicationFrame
{
    public LineChart_IterationTimeComparision(DefaultCategoryDataset dataset)
    {
        super("Iteration Time Comparision");
        JFreeChart lineChart = ChartFactory.createLineChart(
                "IterationTimeComparision",
                "Iteration","Time(ms)",
                dataset,
                PlotOrientation.VERTICAL,
                true,true,false);

        ChartPanel chartPanel = new ChartPanel( lineChart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
        setContentPane( chartPanel );
    }
    public static void createForDataSet(DefaultCategoryDataset dataset){
        LineChart_IterationTimeComparision
                chart = new LineChart_IterationTimeComparision(dataset);
        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible( true );
    }
}