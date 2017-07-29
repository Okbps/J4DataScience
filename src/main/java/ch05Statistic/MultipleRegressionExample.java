package ch05Statistic;

import com.opencsv.CSVReader;
import javafx.application.Application;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

import java.io.FileReader;
import java.text.NumberFormat;

import static util.Globals.RESOURCE_FOLDER;

/**
 * Created by Aspire on 14.07.2017.
 */
public class MultipleRegressionExample extends Application{
    private final double[]data = new double[100];
    private final NumberFormat numberFormat = NumberFormat.getNumberInstance();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        int i = 0;
        try(CSVReader reader = new CSVReader(
                new FileReader(RESOURCE_FOLDER + "csv/cigarettes.csv"), ',')){
            String[]nextLine;
            while((nextLine = reader.readNext()) != null){
                String brandName                = nextLine[0];
                double tarContent               = Double.parseDouble(nextLine[1]);
                double nicotineContent          = Double.parseDouble(nextLine[2]);
                double weight                   = Double.parseDouble(nextLine[3]);
                double carbonMonoxideContent    = Double.parseDouble(nextLine[4]);
                data[i++] = carbonMonoxideContent;
                data[i++] = tarContent;
                data[i++] = nicotineContent;
                data[i++] = weight;
            }

            OLSMultipleLinearRegression ols = new OLSMultipleLinearRegression();

            int numberOfObservations = 25;
            int numberOfIndependentVariables = 3;

            try{
                ols.newSampleData(data, numberOfObservations, numberOfIndependentVariables);
            }catch (IllegalArgumentException e){
                e.printStackTrace();
            }

            numberFormat.setMaximumFractionDigits(2);
            double[]parameters = ols.estimateRegressionParameters();
            for (int j = 0; j < parameters.length; j++) {
                System.out.println("Parameter " + i + ": " + numberFormat.format(parameters[j]));
            }

            double arguments1[] = {1, 4.5, 0.42, 0.9106};
            System.out.println("X: " + 4.9 + " y: " + numberFormat.format(getY(parameters, arguments1)));

            double arguments2[] = {1, 15.2, 1.02, 0.9496};
            System.out.println("X: " + 13.9 + " y: " + numberFormat.format(getY(parameters, arguments2)));

            double arguments3[] = {1, 12.2, 1.65, 0.86};
            System.out.println("X: " + 9.9 + " y: " + numberFormat.format(getY(parameters, arguments3)));
        }
    }

    public double getY(double[]parameters, double[]arguments){
        double result = 0;
        for (int i = 0; i < parameters.length; i++) {
            result += parameters[i] * arguments[i];
        }
        return result;
    }
}
