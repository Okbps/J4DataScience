package ch06ML;

import weka.classifiers.trees.J48;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static util.Globals.RESOURCE_FOLDER;

/**
 * Created by Aspire on 16.07.2017.
 */
public class BookDecisionTree {
    private Instances trainingData;

    public static void main(String[] args) throws Exception {
        BookDecisionTree decisionTree = new BookDecisionTree(RESOURCE_FOLDER + "books.arff");
        J48 tree = decisionTree.performTraining();
        System.out.println(tree.toString());

        Instance testInstance = decisionTree.getTestInstance("Leather", "yes", "historical");
        int result = (int) tree.classifyInstance(testInstance);
        String results = decisionTree.trainingData.attribute(3).value(result);
        System.out.println("Test with: " + testInstance + " Result: " + results);

        testInstance = decisionTree.getTestInstance("Paperback", "no", "historical");
        result = (int)tree.classifyInstance(testInstance);
        results = decisionTree.trainingData.attribute(3).value(result);
        System.out.println("Test with: " + testInstance + " Result: " + results);
    }

    public BookDecisionTree(String fileName) {
        try{
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            trainingData = new Instances(reader);
            trainingData.setClassIndex(trainingData.numAttributes() - 1);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private J48 performTraining(){
        J48 j48 = new J48();
        String[]options = {"-U"};
        try{
            j48.setOptions(options);
            j48.buildClassifier(trainingData);
        }catch (Exception e){
            e.printStackTrace();
        }
        return j48;
    }

    public Instance getTestInstance(String binding, String multicolor, String genre) {
        Instance instance = new DenseInstance(3);
        instance.setDataset(trainingData);
        instance.setValue(trainingData.attribute(0), binding);
        instance.setValue(trainingData.attribute(1), multicolor);
        instance.setValue(trainingData.attribute(2), genre);
        return instance;
    }
}
