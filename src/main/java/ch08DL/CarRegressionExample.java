package ch08DL;

import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.split.FileSplit;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.SplitTestAndTrain;
import org.nd4j.linalg.dataset.api.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.NormalizerStandardize;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.io.File;
import java.io.IOException;

import static util.Globals.RESOURCE_FOLDER;

public class CarRegressionExample {
    public static void main(String[] args) {
        new CarRegressionExample();
    }

    public CarRegressionExample(){
        try{
            RecordReader recordReader = new CSVRecordReader(0, ",");
            recordReader.initialize(new FileSplit(new File(RESOURCE_FOLDER + "csv/car-numeric.csv")));
            DataSetIterator iterator = new RecordReaderDataSetIterator(recordReader, 1728, 6, 4);

            DataSet dataset = iterator.next();
            dataset.shuffle();
            SplitTestAndTrain testAndTrain = dataset.splitTestAndTrain(0.65);
            DataSet trainingData = testAndTrain.getTrain();
            DataSet testData = testAndTrain.getTest();

            DataNormalization normalizer = new NormalizerStandardize();
            normalizer.fit(trainingData);
            normalizer.transform(trainingData);
            normalizer.transform(testData);

            MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                    .iterations(1000)
                    .activation(Activation.RELU)
                    .weightInit(WeightInit.XAVIER)
                    .learningRate(0.4)
                    .list()
                    .layer(0, new DenseLayer.Builder()
                            .nIn(6).nOut(3)
                            .build())
                    .layer(1, new OutputLayer
                            .Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                            .activation(Activation.SOFTMAX)
                            .nIn(3).nOut(4).build())
                    .backprop(true).pretrain(false)
                    .build();

            MultiLayerNetwork model = new MultiLayerNetwork(conf);
            model.init();
            model.setListeners(new ScoreIterationListener(100));
            model.fit(trainingData);

//            iterator.reset();
//            while(iterator.hasNext()){
//                DataSet ds = iterator.next();
//                model.fit(new org.nd4j.linalg.dataset.DataSet(
//                        ds.getFeatures(), ds.getFeatures()));
//            }

            Evaluation evaluation = new Evaluation(4);
            INDArray output = model.output(testData.getFeatures());
            evaluation.eval(testData.getLabels(), output);


            System.out.println(evaluation.stats());

        }catch(IOException|InterruptedException e){
            e.printStackTrace();
        }
    }
}
