package ch04Visualization;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.Arrays;

import static ch04Visualization.ChartUtils.addDataItem;
import static ch04Visualization.ChartUtils.showXYChart;
import static util.Globals.*;

/**
 * Created by Aspire on 07.07.2017.
 */
public class StackedGraph extends Application{
    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis();
    final XYChart.Series<String, Number> series1 = new XYChart.Series<>();
    final XYChart.Series<String, Number> series2 = new XYChart.Series<>();
    final XYChart.Series<String, Number> series3 = new XYChart.Series<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stackedGraphExample(primaryStage);
    }

    public void stackedGraphExample(Stage stage){
        stage.setTitle("Stacked Bar Chart");
        final StackedBarChart<String, Number> stackedBarChart = new StackedBarChart<String, Number>(xAxis, yAxis);
        stackedBarChart.setTitle("Country Population");

        xAxis.setCategories(
                FXCollections.<String>observableArrayList(
                        Arrays.asList(
                                belgium, germany, france, netherlands, sweden, unitedKingdom)));

        yAxis.setLabel("Population");

        series1.setName("1950");
        addDataItem(series1, belgium, 8639369);
        addDataItem(series1, france, 42518000);
        addDataItem(series1, germany, 68374572);
        addDataItem(series1, netherlands, 10113527);
        addDataItem(series1, sweden, 7014005);
        addDataItem(series1, unitedKingdom, 50127000);

        series2.setName("1960");
        addDataItem(series2, belgium, 9118700);
        addDataItem(series2, france, 46584000);
        addDataItem(series2, germany, 72480869);
        addDataItem(series2, netherlands, 11486000);
        addDataItem(series2, sweden, 7480395);
        addDataItem(series2, unitedKingdom, 52372000);

        series3.setName("1970");
        addDataItem(series3, belgium, 9637800);
        addDataItem(series3, france, 51918000);
        addDataItem(series3, germany, 77783164);
        addDataItem(series3, netherlands, 13032335);
        addDataItem(series3, sweden, 8042803);
        addDataItem(series3, unitedKingdom, 55632000);

        showXYChart(stackedBarChart, stage, series1, series2, series3);
    }
}
