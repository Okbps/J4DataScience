package ch05Statistic;

import com.google.common.math.Stats;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.descriptive.SynchronizedDescriptiveStatistics;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

import java.util.Arrays;

/**
 * Created by Aspire on 11.07.2017.
 */
public class StandardDeviationExample {
    private static double[] getTestData(){
        return new double[]{12.5, 18.3, 11.2, 19.0, 22.1, 14.3, 16.2, 12.5, 17.8, 16.5, 11.2};
    }

    public static void main(String[] args) {
        // 1
        StandardDeviationExample sde = new StandardDeviationExample();
        double testDataDeviation1 = sde.getStandardDeviation(getTestData());
        System.out.println("The standard deviation is " + testDataDeviation1);

        System.out.println("=== Guava ===");

        Stats stats = Stats.of(getTestData());
        double testDataDeviation2 = stats.populationStandardDeviation();
        System.out.println("The population standard deviation is " + testDataDeviation2);
        double testDataDeviation3 = stats.sampleStandardDeviation();
        System.out.println("The sample standard deviation is " + testDataDeviation3);

        System.out.println("=== Apache ===");

        DescriptiveStatistics ds = new SynchronizedDescriptiveStatistics();
        Arrays.stream(getTestData()).forEach(ds::addValue);
        System.out.println("The standard deviation is " + ds.getStandardDeviation());

        StandardDeviation sdSubset = new StandardDeviation(false);
        System.out.println("The population standard deviation is " + sdSubset.evaluate(getTestData()));
        StandardDeviation sdPopulation = new StandardDeviation(true);
        System.out.println("The sample standard deviation is " + sdPopulation.evaluate(getTestData()));
    }

    double getStandardDeviation(double[]testData){
        MeanExample meanExample = new MeanExample();
        double mean = meanExample.getMean(testData);

        int sdSum = 0;

        for (double value : testData) {
            sdSum += Math.pow(value-mean, 2);
        }

        return Math.sqrt(sdSum/testData.length);
    }
}
