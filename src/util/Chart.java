package util;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import java.awt.*;

/**
 * Utilities about Chart
 */
public class Chart {

    /**
     * Show a Line Chart
     * @param window
     * @param title
     * @param xA
     * @param yA
     * @param dataset
     */
    public static void showLineChart(
            String window,
            String title,
            String xA,
            String yA,
            DefaultCategoryDataset dataset) {
        LineChart chart = new LineChart(window, title, xA, yA, dataset);
        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);
    }

    /**
     * Show a Bar Chart
     * @param window
     * @param title
     * @param xA
     * @param yA
     * @param dataset
     */
    public static void showBarChart(
            String window,
            String title,
            String xA,
            String yA,
            DefaultCategoryDataset dataset) {
        BarChart chart = new BarChart(window, title, xA, yA, dataset);
        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);
    }


    private static class BarChart extends ApplicationFrame {
        public BarChart(
                String window,
                String title,
                String xA,
                String yA,
                DefaultCategoryDataset dataset) {
            super(window);
            JFreeChart barChart = ChartFactory.createBarChart(
                    title,
                    xA,
                    yA,
                    dataset,
                    PlotOrientation.VERTICAL,
                    true, true, false);

            ChartPanel chartPanel = new ChartPanel(barChart);
            chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
            setContentPane(chartPanel);
        }

    }


    private static class LineChart extends ApplicationFrame {
        public LineChart(
                String window,
                String title,
                String xA,
                String yA,
                DefaultCategoryDataset dataset) {
            super(window);
            JFreeChart lineChart = ChartFactory.createLineChart(
                    title,
                    xA, yA,
                    dataset,
                    PlotOrientation.VERTICAL,
                    true, true, false);

            CategoryPlot plot = lineChart.getCategoryPlot();
            plot.getRenderer().setStroke(new BasicStroke(3.0f));

            ChartPanel chartPanel = new ChartPanel(lineChart);
            chartPanel.setPreferredSize(new Dimension(560, 367));
            setContentPane(chartPanel);
        }
    }

}