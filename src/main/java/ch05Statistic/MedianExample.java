package ch05Statistic;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.util.Arrays;

/**
 * Created by Aspire on 10.07.2017.
 */
public class MedianExample {
    private static double[] getTestData(){
        return new double[]{12.5, 18.7, 11.2, 19.0, 22.1, 14.3, 16.9, 12.5, 17.8, 16.9};
    }

    public static void main(String[] args) {
        // 1
        MedianExample medianExample = new MedianExample();
        medianExample.computeMedian(getTestData());

        // Apache Commons
        DescriptiveStatistics ds = new DescriptiveStatistics();
        Arrays.stream(getTestData()).forEach(ds::addValue);
        System.out.println("The median is " + ds.getPercentile(50));
    }

    void computeMedian(double[]testData){
        Arrays.sort(testData);

        if(testData.length==0){
            System.out.println("No median");
        } else if (testData.length % 2 == 0) {
            double mid1 = testData[testData.length/2-1];
            double mid2 = testData[testData.length/2];
            double med = (mid1 + mid2)/2;
            System.out.println("The median is " + med);
        }else{
            double med = testData[testData.length/2 + 1];
            System.out.println("The median is " + med);
        }
    }
}
