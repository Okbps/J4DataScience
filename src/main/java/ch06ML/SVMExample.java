package ch06ML;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.SMO;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static util.Globals.RESOURCE_FOLDER;

/**
 * Created by Aspire on 16.07.2017.
 */
public class SVMExample {
    public static void main(String[] args) throws Exception {
        SVMExample example = new SVMExample();
        BufferedReader dataFile = example.readDataFile(RESOURCE_FOLDER + "camping.arff");

        Instances data = new Instances(dataFile);
        data.setClassIndex(data.numAttributes()-1);

        Instances trainingData = new Instances(data, 0, 14);
        Instances testingData = new Instances(data, 14, 5);

        Evaluation evaluation = new Evaluation(trainingData);
        Classifier smo = new SMO();
        smo.buildClassifier(data);

        evaluation.evaluateModel(smo, testingData);
        System.out.println(evaluation.toSummaryString());

        // classify individual instance
        Instance instance = new DenseInstance(3);
        instance.setValue(data.attribute("age"), 78);
        instance.setValue(data.attribute("income"), 125700);
        instance.setValue(data.attribute("camps"), 1);

        instance.setDataset(data);
        System.out.println(smo.classifyInstance(instance));
    }

    private BufferedReader readDataFile(String filename){
        BufferedReader inputReader = null;
        try{
            inputReader = new BufferedReader(new FileReader(filename));
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return inputReader;
    }
}
