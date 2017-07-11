package ch05Statistic;

import javafx.application.Application;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import org.apache.commons.math3.stat.regression.SimpleRegression;

import java.text.NumberFormat;
import java.util.Arrays;

import static ch04Visualization.ChartUtils.addDataItem;
import static ch04Visualization.ChartUtils.showXYChart;

/**
 * Created by Aspire on 11.07.2017.
 */
public class SimpleLinearRegressionExample extends Application{
    final XYChart.Series<String, Number> seriesActual = new XYChart.Series<>();
    final XYChart.Series<String, Number> seriesProjected = new XYChart.Series<>();
    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis(8000000, 12000000, 1000000);
    final LineChart<String, Number> lineChart = new LineChart<String, Number>(xAxis, yAxis);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        double[][] input = {
                {1950, 8639369},
                {1960, 9118700},
                {1970, 9637800},
                {1980, 9846800},
                {1990, 9969310},
                {2000, 10263618}
        };

        double[] predictionYears = {1950, 1960, 1970, 1980, 1990, 2000, 2010, 2020, 2030, 2040};

        NumberFormat yearFormat = NumberFormat.getNumberInstance();
        yearFormat.setMaximumFractionDigits(0);
        yearFormat.setGroupingUsed(false);
        NumberFormat populationFormat = NumberFormat.getNumberInstance();
        populationFormat.setMaximumFractionDigits(0);

        SimpleRegression sr = new SimpleRegression();
        sr.addData(input);

        // Results output
        for (int i = 0; i < predictionYears.length; i++) {
            System.out.println(yearFormat.format(predictionYears[i])
            + " - "
            + populationFormat.format(sr.predict(predictionYears[i])));
        }

        displayAttribute("Slope", sr.getSlope());
        displayAttribute("Intercept", sr.getIntercept());
        displayAttribute("MeanSquareError", sr.getMeanSquareError());
        displayAttribute("R", sr.getR());
        displayAttribute("RSquare", sr.getRSquare());

        // Index Chart
        primaryStage.setTitle("Simple Linear Regression");
        lineChart.setTitle("Belgium Population");

        seriesActual.setName("Actual");
        Arrays.stream(input)
                .forEach(doubles ->
                        addDataItem(seriesActual, yearFormat.format(doubles[0]), doubles[1]));

        seriesProjected.setName("Projected");
        Arrays.stream(predictionYears)
                .forEach(year ->
                        addDataItem(seriesProjected, yearFormat.format(year), sr.predict(year)));

        showXYChart(lineChart, primaryStage, seriesActual, seriesProjected);

    }

    void displayAttribute(String attribute, double value){
        NumberFormat format = NumberFormat.getNumberInstance();
        format.setMaximumFractionDigits(2);
        System.out.println(attribute + ": " + format.format(value));
    }


}
