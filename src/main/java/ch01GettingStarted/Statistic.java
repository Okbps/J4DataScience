package ch01GettingStarted;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.descriptive.SynchronizedDescriptiveStatistics;

/**
 * Created by Aspire on 07.06.2017.
 */
public class Statistic {
    public static void main(String[] args) {
        findMedian();
    }

    static void findMedian(){
        double[] testData = {12.5, 18.3, 11.2, 19.0, 22.1, 14.3, 16.2, 12.5, 17.8, 16.5, 12.5};
        DescriptiveStatistics statTest = new SynchronizedDescriptiveStatistics();

        for(double num : testData){
            statTest.addValue(num);
        }

        System.out.println("The median is " + statTest.getPercentile(50));
    }
}
