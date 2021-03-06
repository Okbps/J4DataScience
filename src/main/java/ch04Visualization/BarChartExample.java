package ch04Visualization;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import static ch04Visualization.ChartUtils.addDataItem;
import static ch04Visualization.ChartUtils.showXYChart;
import static util.Globals.*;

/**
 * Created by Aspire on 06.07.2017.
 */
public class BarChartExample extends Application{
    final NumberAxis yAxis = new NumberAxis();
    final XYChart.Series<String, Number> series1 = new XYChart.Series<>();
    final XYChart.Series<String, Number> series2 = new XYChart.Series<>();
    final XYChart.Series<String, Number> series3 = new XYChart.Series<>();
    final XYChart.Series<String, Number> series4 = new XYChart.Series<>();
    final XYChart.Series<String, Number> series5 = new XYChart.Series<>();
    final XYChart.Series<String, Number> series6 = new XYChart.Series<>();
    final CategoryAxis xAxis = new CategoryAxis();

    @Override
    public void start(Stage primaryStage) throws Exception {
//        simpleBarChartByCountry(primaryStage);
        simpleBarChartByYear(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void simpleBarChartByYear(Stage stage){
        stage.setTitle("Bar Chart");
        final BarChart<String, Number> barChart = new BarChart<String, Number>(xAxis, yAxis);
        barChart.setTitle("Year Summary");
        xAxis.setLabel("Year");
        yAxis.setLabel("Population");

        String year1950 = "1950";
        String year1960 = "1960";
        String year1970 = "1970";

        series1.setName(belgium);
        addDataItem(series1, year1950, 8639369);
        addDataItem(series1, year1960, 9118700);
        addDataItem(series1, year1970, 9637800);

        series2.setName(france);
        addDataItem(series2, year1950, 42518000);
        addDataItem(series2, year1960, 46584000);
        addDataItem(series2, year1970, 51918000);

        series3.setName(germany);
        addDataItem(series3, year1950, 68374572);
        addDataItem(series3, year1960, 72480869);
        addDataItem(series3, year1970, 77783164);

        series4.setName(netherlands);
        addDataItem(series4, year1950, 10113527);
        addDataItem(series4, year1960, 11486000);
        addDataItem(series4, year1970, 13032335);

        series5.setName(sweden);
        addDataItem(series5, year1950, 7014005);
        addDataItem(series5, year1960, 7480395);
        addDataItem(series5, year1970, 8042803);

        series6.setName(unitedKingdom);
        addDataItem(series6, year1950, 50127000);
        addDataItem(series6, year1960, 52372000);
        addDataItem(series6, year1970, 55632000);

        Scene scene = new Scene(barChart, 800, 600);
        barChart.getData().addAll(series1, series2, series3, series4, series5, series6);
        stage.setScene(scene);
        stage.show();
    }

    public void simpleBarChartByCountry(Stage stage){
        stage.setTitle("Bar Chart");
        final BarChart<String, Number> barChart = new BarChart<String, Number>(xAxis, yAxis);
        barChart.setTitle("Country Summary");
        xAxis.setLabel("Country");
        yAxis.setLabel("Population");

        series1.setName("1950");
        addDataItem(series1, belgium,8639369);
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

        showXYChart(barChart, stage, series1, series2, series3);
    }
}
