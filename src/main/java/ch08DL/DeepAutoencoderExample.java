package ch08DL;

import org.deeplearning4j.datasets.fetchers.MnistDataFetcher;
import org.deeplearning4j.datasets.iterator.impl.MnistDataSetIterator;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.conf.layers.RBM;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;

import static util.Globals.RESOURCE_FOLDER;

public class DeepAutoencoderExample {
    public static void main(String[] args) {

        try{
            final int numberOfRows = 28;
            final int numberOfColumns = 28;
            int seed = 123;
            int numberOfIterations = 1;

            Iterator iterator = new MnistDataSetIterator(1000, MnistDataFetcher.NUM_EXAMPLES, true);

            MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                    .seed(seed)
                    .iterations(numberOfIterations)
                    .optimizationAlgo(OptimizationAlgorithm.LINE_GRADIENT_DESCENT)
                    .list()
                    .layer(0, new RBM.Builder()
                            .nIn(numberOfRows * numberOfColumns).nOut(1000)
                            .lossFunction(LossFunctions.LossFunction.KL_DIVERGENCE)
                            .build())
                    .layer(1, new RBM.Builder().nIn(1000).nOut(500)
                            .lossFunction(LossFunctions.LossFunction.KL_DIVERGENCE)
                            .build())
                    .layer(2, new RBM.Builder().nIn(500).nOut(250)
                            .lossFunction(LossFunctions.LossFunction.KL_DIVERGENCE)
                            .build())
                    .layer(3, new RBM.Builder().nIn(250).nOut(100)
                            .lossFunction(LossFunctions.LossFunction.KL_DIVERGENCE)
                            .build())
                    .layer(4, new RBM.Builder().nIn(100).nOut(30)
                            .lossFunction(LossFunctions.LossFunction.KL_DIVERGENCE)
                            .build())
                    .layer(5, new RBM.Builder().nIn(30).nOut(100)
                            .lossFunction(LossFunctions.LossFunction.KL_DIVERGENCE)
                            .build())
                    .layer(6, new RBM.Builder().nIn(100).nOut(250)
                            .lossFunction(LossFunctions.LossFunction.KL_DIVERGENCE)
                            .build())
                    .layer(7, new RBM.Builder().nIn(250).nOut(500)
                            .lossFunction(LossFunctions.LossFunction.KL_DIVERGENCE)
                            .build())
                    .layer(8, new RBM.Builder().nIn(500).nOut(1000)
                            .lossFunction(LossFunctions.LossFunction.KL_DIVERGENCE)
                            .build())
                    .layer(9, new OutputLayer.Builder(LossFunctions.LossFunction.MSE)
                            .activation(Activation.SIGMOID)
                            .nIn(1000).nOut(numberOfRows * numberOfColumns)
                            .build())
                    .pretrain(true).backprop(true)
                    .build();

            MultiLayerNetwork model = new MultiLayerNetwork(conf);
            model.init();
            model.setListeners(Collections.singletonList(new ScoreIterationListener()));

            while(iterator.hasNext()){
                DataSet dataSet = (DataSet) iterator.next();
                model.fit(new DataSet(dataSet.getFeatureMatrix(), dataSet.getFeatureMatrix()));
            }

            File modelFile = new File(RESOURCE_FOLDER+"numModel");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    MultiLayerNetwork retrieveModel(){
        File modelFile = new File(RESOURCE_FOLDER+"numModel");
        try {
            return ModelSerializer.restoreMultiLayerNetwork(modelFile);
        } catch (IOException e) {
            return null;
        }
    }
}
