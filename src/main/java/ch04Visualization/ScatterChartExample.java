package ch04Visualization;

import com.opencsv.CSVReader;
import javafx.application.Application;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static ch04Visualization.ChartUtils.showXYChart;
import static util.Globals.RESOURCE_FOLDER;

/**
 * Created by Aspire on 07.07.2017.
 */
public class ScatterChartExample extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Scatter Chart Sample");
        final NumberAxis yAxis = new NumberAxis(0, 250, 10);
        final NumberAxis xAxis = new NumberAxis(60, 120, 10);
        final ScatterChart<Number, Number>scatterChart = new ScatterChart<Number, Number>(xAxis, yAxis);

        XYChart.Series series = new XYChart.Series();

        try(CSVReader dataReader = new CSVReader(new FileReader(RESOURCE_FOLDER + "2014_apple_stock.csv"), ',')){
            String[]nextLine;
            while((nextLine = dataReader.readNext()) != null){
                LocalDate localDate = LocalDate.parse(nextLine[0], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                int day = localDate.getDayOfYear();
                int rate = (int)Double.parseDouble(nextLine[1]);
                series.getData().add(new XYChart.Data(rate, day));
            }
        }

        showXYChart(scatterChart, primaryStage, series);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
