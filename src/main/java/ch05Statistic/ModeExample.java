package ch05Statistic;

import org.apache.commons.math3.stat.StatUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Aspire on 10.07.2017.
 */
public class ModeExample {
    private static double[] getTestData(){
        return new double[]{12.5, 18.3, 11.2, 19.0, 22.1, 14.3, 16.2, 12.5, 17.8, 16.5, 11.2};
    }

    public static void main(String[] args) {
        // 1
        ModeExample modeExample = new ModeExample();
        modeExample.computeSingleMode(getTestData());
        modeExample.computeMultiMode(getTestData());
        modeExample.computeMultiModeMap(getTestData());

        // Apache
        double[]modes = StatUtils.mode(getTestData());
        for (double mode : modes) {
            System.out.println(mode + " is a mode");
        }
    }

    void computeMultiModeMap(double[]testData){
        ArrayList<Double>modes = new ArrayList<Double>();
        HashMap<Double, Integer>modeMap = new HashMap<Double, Integer>();
        int maxMode = 0;

        for (double value : testData) {
            int modeCnt = 0;
            if (modeMap.containsKey(value)){
                modeCnt = modeMap.get(value) + 1;
            }else{
                modeCnt = 1;
            }

            modeMap.put(value, modeCnt);
            if (modeCnt > maxMode){
                maxMode = modeCnt;
            }
        }

        for (Map.Entry<Double, Integer> multiModes : modeMap.entrySet()) {
            if(multiModes.getValue() == maxMode){
                modes.add(multiModes.getKey());
            }
        }

        for (Double mode : modes) {
            System.out.println(mode + " is a mode and appears " + maxMode + " times");
        }
    }

    void computeMultiMode(double[]testData){
        ArrayList<Integer>modeCount = new ArrayList<Integer>();
        ArrayList<Double>mode = new ArrayList<Double>();
        int tempMode = 0;

        for (double testValue : testData) {
            int loc = mode.indexOf(testValue);
            if(loc==-1){
                mode.add(testValue);
                modeCount.add(1);
            }else{
                modeCount.set(loc, modeCount.get(loc)+1);
            }
        }

        for (int i = 0; i < modeCount.size(); i++) {
            if (tempMode < modeCount.get(i)){
                tempMode = modeCount.get(i);
            }
        }

        for (int i = 0; i < modeCount.size(); i++) {
            if (tempMode == modeCount.get(i)){
                System.out.println(mode.get(i)
                        + " is a mode and appears "
                        + modeCount.get(i)
                        + " times");
            }
        }
    }

    void computeSingleMode(double[]testData){
        int modeCount = 0;
        double mode = 0;
        int tempCnt = 0;

        for (double testValue : testData) {
            tempCnt = 0;
            for (double value : testData){
                if (testValue==value){
                    tempCnt++;
                }
            }

            if (tempCnt > modeCount){
                modeCount = tempCnt;
                mode = testValue;
            }
        }

        System.out.println("Mode "+mode+" appears "+modeCount+" times.");
    }
}
