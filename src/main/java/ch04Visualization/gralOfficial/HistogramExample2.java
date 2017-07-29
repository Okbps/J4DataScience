package ch04Visualization.gralOfficial;

import com.opencsv.CSVReader;
import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.graphics.Insets2D;
import de.erichseifert.gral.graphics.Location;
import de.erichseifert.gral.plots.BarPlot;
import de.erichseifert.gral.plots.BarPlot.BarRenderer;
import de.erichseifert.gral.ui.InteractivePanel;
import de.erichseifert.gral.util.GraphicsUtils;

import java.awt.*;
import java.io.FileReader;
import java.io.IOException;

import static util.Globals.RESOURCE_FOLDER;

/**
 * Created by Aspire on 08.07.2017.
 */
public class HistogramExample2 extends ExamplePanel{
    @SuppressWarnings("unchecked")
    public HistogramExample2() {
        // Create example data
        DataTable data = new DataTable(Integer.class, Double.class);

        try(CSVReader dataReader = new CSVReader(new FileReader(RESOURCE_FOLDER + "csv/AgeOfMarriageBY.csv"), ',')){
            String[]nextLine;
            while((nextLine = dataReader.readNext()) != null){
                data.add(Integer.parseInt(nextLine[0]), Double.parseDouble(nextLine[1]));
            }
        }catch(IOException e){
        }

        // Create new bar plot
        BarPlot plot = new BarPlot(data);

        // Format plot
        plot.setInsets(new Insets2D.Double(40.0, 40.0, 40.0, 40.0));
        plot.setBarWidth(0.75);

        // Format bars
        BarRenderer pointRenderer = (BarRenderer) plot.getPointRenderers(data).get(0);
        pointRenderer.setColor(
                new LinearGradientPaint(0f,0f, 0f,1f,
                        new float[] { 0.0f, 1.0f },
                        new Color[] { COLOR1, GraphicsUtils.deriveBrighter(COLOR1) }
                )
        );
        pointRenderer.setBorderStroke(new BasicStroke(3f));
        pointRenderer.setBorderColor(
                new LinearGradientPaint(0f,0f, 0f,1f,
                        new float[] { 0.0f, 1.0f },
                        new Color[] { GraphicsUtils.deriveBrighter(COLOR1), COLOR1 }
                )
        );
        pointRenderer.setValueVisible(true);
        pointRenderer.setValueColumn(1);
        pointRenderer.setValueLocation(Location.CENTER);
        pointRenderer.setValueColor(GraphicsUtils.deriveDarker(COLOR1));
        pointRenderer.setValueFont(Font.decode(null).deriveFont(Font.BOLD));

        // Add plot to Swing component
        add(new InteractivePanel(plot));
    }

    @Override
    public String getTitle() {
        return "Bar plot";
    }

    @Override
    public String getDescription() {
        return "Bar plot with example data and color gradients";
    }

    public static void main(String[] args) {
        new HistogramExample2().showInFrame();
    }
}
