package ch04Visualization;

import de.erichseifert.gral.data.DataSeries;
import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.graphics.Insets2D;
import de.erichseifert.gral.io.data.DataReader;
import de.erichseifert.gral.io.data.DataReaderFactory;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.points.SizeablePointRenderer;
import de.erichseifert.gral.ui.InteractivePanel;
import de.erichseifert.gral.util.GraphicsUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.io.FileInputStream;
import java.io.IOException;

import static util.Globals.RESOURCE_FOLDER;

/**
 * Created by Aspire on 08.07.2017.
 */
public class BubbleExample extends JFrame{
    public BubbleExample() throws HeadlessException {
        DataReader readType = DataReaderFactory.getInstance().get("text/csv");
        String fileName = RESOURCE_FOLDER + "MarriageByYears.csv";
        try{
            DataTable bubbleData = (DataTable)readType.read(
                    new FileInputStream(fileName),
                    Integer.class, Integer.class, Integer.class
            );
            DataSeries bubbleSeries = new DataSeries("Bubble", bubbleData);
            XYPlot plot = new XYPlot(bubbleSeries);

            plot.setInsets(new Insets2D.Double(30.0));
            plot.setBackground(new Color(0.75f, 0.75f, 0.75f));
            XYPlot.XYPlotArea2D area = (XYPlot.XYPlotArea2D)plot.getPlotArea();
            area.setBorderColor(null);
            area.setMajorGridX(false);
            area.setMajorGridY(false);
            area.setClippingArea(null);

            plot.getAxisRenderer(XYPlot.AXIS_X).setShapeVisible(false);
            plot.getAxisRenderer(XYPlot.AXIS_X).setTicksVisible(false);
            plot.getAxisRenderer(XYPlot.AXIS_Y).setShapeVisible(false);
            plot.getAxisRenderer(XYPlot.AXIS_Y).setTicksVisible(false);
            plot.getAxis(XYPlot.AXIS_X).setRange(1940, 2020);
            plot.getAxis(XYPlot.AXIS_Y).setRange(17, 30);

            Color color = GraphicsUtils.deriveWithAlpha(Color.black, 96);
            SizeablePointRenderer bubbleRenderer = new SizeablePointRenderer();
            bubbleRenderer.setShape(new Ellipse2D.Double(-3.5, -3.5, 4.0, 4.0));
            bubbleRenderer.setColor(color);
            bubbleRenderer.setColumn(2);
            plot.setPointRenderers(bubbleSeries, bubbleRenderer);

            add(new InteractivePanel(plot), BorderLayout.CENTER);
            setSize(new Dimension(1500, 700));
            setVisible(true);

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new BubbleExample();
    }
}
