package ch04Visualization;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

/**
 * Created by Aspire on 07.07.2017.
 */
public class ChartUtils {
    public static void addDataItem(XYChart.Series<String, Number> series, String x, Number y){
        series.getData().add(new XYChart.Data<>(x, y));
    }

    public static void showScene(XYChart chart, Stage stage, XYChart.Series<String, Number>...series){
        Scene scene = new Scene(chart, 800, 600);
        chart.getData().addAll(series);
        stage.setScene(scene);
        stage.show();
    }
}
