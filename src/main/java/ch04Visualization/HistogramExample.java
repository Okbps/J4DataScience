package ch04Visualization;

import de.erichseifert.gral.data.DataSource;
import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.data.EnumeratedData;
import de.erichseifert.gral.data.statistics.Histogram;
import de.erichseifert.gral.data.statistics.Histogram1D;
import de.erichseifert.gral.graphics.Insets2D;
import de.erichseifert.gral.graphics.Orientation;
import de.erichseifert.gral.io.data.DataReader;
import de.erichseifert.gral.io.data.DataReaderFactory;
import de.erichseifert.gral.plots.BarPlot;
import de.erichseifert.gral.plots.points.PointRenderer;
import de.erichseifert.gral.ui.InteractivePanel;
import de.erichseifert.gral.util.GraphicsUtils;

import javax.swing.*;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;

import static util.Globals.RESOURCE_FOLDER;

/**
 * Created by Aspire on 08.07.2017.
 */
public class HistogramExample extends JFrame{
    public HistogramExample() {
        DataReader readType = DataReaderFactory.getInstance().get("text/csv");
        String fileName = RESOURCE_FOLDER + "AgeOfMarriage.csv";
        try {
            DataTable histData = (DataTable) readType.read(new FileInputStream(fileName), Integer.class);

            DataSource sampleHistData = new EnumeratedData(histData, 19, 1);

            BarPlot testPlot = new BarPlot(sampleHistData);

            testPlot.setInsets(new Insets2D.Double(20.0, 50.0, 50.0, 20.0));
            testPlot.getTitle().setText("Average Age of Marriage");
            testPlot.setBarWidth(0.7);

            testPlot.getAxis(BarPlot.AXIS_X).setRange(18, 30.0);
            testPlot.getAxisRenderer(BarPlot.AXIS_X).setTickAlignment(0.0);
            testPlot.getAxisRenderer(BarPlot.AXIS_X).setTickSpacing(1);
            testPlot.getAxisRenderer(BarPlot.AXIS_X).setMinorTicksVisible(false);

            testPlot.getAxis(BarPlot.AXIS_Y).setRange(0.0, 10.0);
            testPlot.getAxisRenderer(BarPlot.AXIS_Y).setTickAlignment(0.0);
            testPlot.getAxisRenderer(BarPlot.AXIS_Y).setMinorTicksVisible(false);
            testPlot.getAxisRenderer(BarPlot.AXIS_Y).setIntersection(0);

            PointRenderer renderHist = testPlot.getPointRenderers(sampleHistData).get(0);
            renderHist.setColor(GraphicsUtils.deriveWithAlpha(Color.black, 128));
            renderHist.setValueVisible(true);

            InteractivePanel pan = new InteractivePanel(testPlot);
            pan.setPannable(false);
            pan.setZoomable(false);
            add(pan);
            setSize(1500, 700);
            this.setVisible(true);

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new HistogramExample();
    }

    @Override
    public String getTitle() {
        return "Sample Histogram";
    }
}
