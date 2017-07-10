package ch05Statistic;

import com.google.common.math.Stats;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.descriptive.SynchronizedDescriptiveStatistics;
import org.apache.commons.math3.stat.descriptive.moment.Mean;

import java.util.Arrays;
import java.util.OptionalDouble;

/**
 * Created by Aspire on 10.07.2017.
 */
public class MeanExample {


    public static void main(String[] args) {
        double[] testData = {12.5, 18.7, 11.2, 19.0, 22.1, 14.3, 16.9, 12.5, 17.8, 16.9};

        // 1
        MeanExample meanExample = new MeanExample();
        System.out.println(meanExample.getMean(testData));

        // java 8
        OptionalDouble od = Arrays.stream(testData).average();

        od.ifPresent(System.out::println);
        System.out.println(od.orElse(0));

        // Guava
        Stats stats = Stats.of(testData);
        System.out.println(stats.mean());

        // Apache
        Mean mean = new Mean();
        System.out.println(mean.evaluate(testData));

        DescriptiveStatistics statTest = new SynchronizedDescriptiveStatistics();
        Arrays.stream(testData).forEach(statTest::addValue);
    }

    double getMean(double[]testData){
        double total = 0;
        for (int i = 0; i < testData.length; i++) {
            total += testData[i];
        };

        return total/testData.length;
    }
}
