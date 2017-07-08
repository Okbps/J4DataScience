package ch04Visualization;

import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.graphics.Insets2D;
import de.erichseifert.gral.plots.PiePlot;
import de.erichseifert.gral.plots.colors.LinearGradient;
import de.erichseifert.gral.plots.legends.ValueLegend;
import de.erichseifert.gral.ui.InteractivePanel;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Aspire on 08.07.2017.
 */
public class DonutExample extends JFrame {

    public static void main(String[] args) {
        new DonutExample();
    }

    public DonutExample() throws HeadlessException {
        int[][]ageCount = {
                {19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29},
                {1, 5, 4, 2, 4, 4, 2, 1, 0, 3, 1}
        };

        DataTable donutData = new DataTable(Integer.class, Integer.class);
        for (int y = 0; y < ageCount[0].length; y++) {
            if(ageCount[1][y] == 0){
                donutData.add(-3, ageCount[0][y]);
            }else{
                donutData.add(ageCount[1][y], ageCount[0][y]);
            }
        }

        PiePlot testPlot = new PiePlot(donutData);
        ((ValueLegend)testPlot.getLegend()).setLabelColumn(1);
        testPlot.getTitle().setText("Donut Plot Example");
        testPlot.setRadius(0.9);
        testPlot.setLegendVisible(true);
        testPlot.setInsets(new Insets2D.Double(20.0, 20.0, 20.0, 20.0));

        PiePlot.PieSliceRenderer renderer = (PiePlot.PieSliceRenderer)testPlot.getPointRenderer(donutData);
        renderer.setInnerRadius(0.4);
        renderer.setGap(0.2);

        LinearGradient colors = new LinearGradient(Color.blue, Color.green);
        renderer.setColor(colors);
        renderer.setValueVisible(true);
        renderer.setValueColor(Color.WHITE);
        renderer.setValueFont(Font.decode(null).deriveFont(Font.BOLD));

        add(new InteractivePanel(testPlot), BorderLayout.CENTER);
        setSize(1500, 700);
        setVisible(true);
    }
}
