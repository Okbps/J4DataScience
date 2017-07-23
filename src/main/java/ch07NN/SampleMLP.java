package ch07NN;

import weka.classifiers.Evaluation;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;

import java.io.FileReader;

import static util.Globals.RESOURCE_FOLDER;

public class SampleMLP {
    private static final String trainingFileName = RESOURCE_FOLDER + "arff/mlplearn.arff";
    private static final String testingFileName = RESOURCE_FOLDER + "arff/mlptest.arff";

    public static void main(String[] args) {
        try(FileReader trainingReader = new FileReader(trainingFileName);
            FileReader testingReader = new FileReader(testingFileName)
        ) {
            Instances trainingInstances = new Instances(trainingReader);
            trainingInstances.setClassIndex(trainingInstances.numAttributes() - 1);
            Instances testingInstances = new Instances(testingReader);
            testingInstances.setClassIndex(testingInstances.numAttributes() - 1);

            MultilayerPerceptron mlp = new MultilayerPerceptron();
            mlp.setLearningRate(0.1);
            mlp.setMomentum(0.2);
            mlp.setTrainingTime(2000);
            mlp.setHiddenLayers("3");

            mlp.buildClassifier(trainingInstances);

            Evaluation evaluation = new Evaluation(trainingInstances);
            evaluation.evaluateModel(mlp, testingInstances);

            System.out.println(evaluation.toSummaryString());

            for (int i = 0; i < testingInstances.numInstances(); i++) {
                double result = mlp.classifyInstance(testingInstances.instance(i));
                double actual = testingInstances.instance(i).value(testingInstances.numAttributes()-1);
                if(result != actual){
                    System.out.println("Classify result: " + result + " Correct: " + actual);

                    Instance incorrectInstance = testingInstances.instance(i);
                    incorrectInstance.setDataset(trainingInstances);
                    double[]distribution = mlp.distributionForInstance(incorrectInstance);
                    System.out.println("Probability of being positive: " + distribution[0]);
                    System.out.println("Probability of being negative: " + distribution[1]);
                }
            }

            SerializationHelper.write(RESOURCE_FOLDER + "mlpModel", mlp);
            mlp = (MultilayerPerceptron)SerializationHelper.read(RESOURCE_FOLDER + "mlpModel");
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
