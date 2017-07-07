package ch04Visualization;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;

import static ch04Visualization.ChartUtils.showChart;
import static util.Globals.*;

/**
 * Created by Aspire on 07.07.2017.
 */
public class PieChartExample extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {
        ObservableList<PieChart.Data>pieChartData = FXCollections.observableArrayList(
            new PieChart.Data(belgium, 3),
            new PieChart.Data(france, 26),
            new PieChart.Data(germany, 35),
            new PieChart.Data(netherlands, 7),
            new PieChart.Data(sweden, 4),
            new PieChart.Data(unitedKingdom, 25)
        );

        final PieChart pieChart = new PieChart(pieChartData);
        pieChart.setTitle("Country Population");
        primaryStage.setTitle("European Country Population");
        showChart(pieChart, primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
