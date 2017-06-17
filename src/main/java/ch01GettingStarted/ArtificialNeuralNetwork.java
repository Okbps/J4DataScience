package ch01GettingStarted;

import weka.classifiers.Evaluation;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instances;

import java.io.FileReader;

import static util.Globals.RESOURCE_FOLDER;

/**
 * Created by Aspire on 07.06.2017.
 */
public class ArtificialNeuralNetwork {
    public static void main(String[] args) {
        mlp();
    }

    static void mlp(){
        String trainingFileName = RESOURCE_FOLDER + "dermatologyTrainingSet.arff";
        String testingFileName = RESOURCE_FOLDER + "dermatologyTestingSet.arff";

        try (
                FileReader trainingReader = new FileReader(trainingFileName);
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
        } catch (Exception ex) {
            // Handle exceptions
        }
    }
}
