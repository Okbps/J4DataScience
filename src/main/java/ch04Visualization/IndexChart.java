package ch04Visualization;

import javafx.application.Application;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import static ch04Visualization.ChartUtils.addDataItem;
import static ch04Visualization.ChartUtils.showXYChart;

/**
 * Created by Aspire on 04.07.2017.
 */
public class IndexChart extends Application{
    final XYChart.Series<String, Number> series = new XYChart.Series<>();
    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis(8000000, 11000000, 1000000);

    @Override
    public void start(Stage primaryStage) throws Exception {
        simpleIndexChart(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void simpleIndexChart(Stage stage){
        stage.setTitle("Index Chart");
        final LineChart<String, Number> lineChart = new LineChart<String, Number>(xAxis, yAxis);
        lineChart.setTitle("Belgium Population");
        yAxis.setLabel("Population");

        series.setName("Population");
        addDataItem(series, "1950", 8639369);
        addDataItem(series, "1960", 9118700);
        addDataItem(series, "1970", 9637800);
        addDataItem(series, "1980", 9846800);
        addDataItem(series, "1990", 9969310);
        addDataItem(series, "2000", 10263618);

        showXYChart(lineChart, stage, series);
    }

}
