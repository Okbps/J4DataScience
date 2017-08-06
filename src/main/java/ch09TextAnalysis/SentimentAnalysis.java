package ch09TextAnalysis;

import ch09TextAnalysis.tools.SentimentExampleIterator;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.io.FileUtils;
import org.deeplearning4j.datasets.iterator.AsyncDataSetIterator;
import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.embeddings.wordvectors.WordVectors;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.GradientNormalization;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.Updater;
import org.deeplearning4j.nn.conf.layers.GravesLSTM;
import org.deeplearning4j.nn.conf.layers.RnnOutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.io.*;
import java.net.URL;

import static util.Globals.RESOURCE_FOLDER;

public class SentimentAnalysis {
    private final String
            TRAINING_DATA_URL = "http://ai.stanford.edu/~amaas/data/sentiment/aclImdb_v1.tar.gz",
            EXTRACT_DATA_PATH = RESOURCE_FOLDER + "news/",
//            FilenameUtils.concat(System.getProperty("java.io.tmpdir"), "dl4j_w2vSentiment/");
            GNEWS_VECTORS_PATH = RESOURCE_FOLDER + "models/GoogleNews-vectors-negative300.bin";

    private final int
            BUFFER_SIZE = 4096,
            batchSize = 50,
            vectorSize = 300,
            nEpochs = 5,
            truncateReviewsToLength = 300;

    public static void main(String[] args) throws Exception {
        SentimentAnalysis sa = new SentimentAnalysis();
        sa.getModelData();
        sa.performAnalysis();
    }

    private void performAnalysis() throws IOException {
        MultiLayerConfiguration sentimentNN = new NeuralNetConfiguration.Builder()
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .iterations(1)
                .updater(Updater.RMSPROP)
                .regularization(true).l2(1e-5)
                .weightInit(WeightInit.XAVIER)
                .gradientNormalization(GradientNormalization.ClipElementWiseAbsoluteValue)
                .gradientNormalizationThreshold(1.0)
                .learningRate(0.0018)
                .list()
                .layer(0, new GravesLSTM.Builder()
                        .nIn(vectorSize)
                        .nOut(200)
                .activation(Activation.SOFTSIGN)
                        .build())
                .layer(1, new RnnOutputLayer.Builder()
                        .activation(Activation.SOFTMAX)
                        .lossFunction(LossFunctions.LossFunction.MCXENT)
                        .nIn(200)
                        .nOut(2)
                        .build())
                .pretrain(false).backprop(true).build();

        MultiLayerNetwork net = new MultiLayerNetwork(sentimentNN);
        net.init();
        net.setListeners(new ScoreIterationListener(1));

        WordVectors wordVectors = WordVectorSerializer.readWord2VecModel(new File(GNEWS_VECTORS_PATH));
//        WordVectors wordVectors = WordVectorSerializer.loadGoogleModel(
//                new File(GNEWS_VECTORS_PATH), true, false);

        DataSetIterator trainData = new AsyncDataSetIterator(
                new SentimentExampleIterator(
                        EXTRACT_DATA_PATH,
                        wordVectors,
                        batchSize,
                        truncateReviewsToLength,
                        true
                ), 1
        );

        DataSetIterator testData = new AsyncDataSetIterator(
          new SentimentExampleIterator(
                  EXTRACT_DATA_PATH,
                  wordVectors,
                  100,
                  truncateReviewsToLength,
                  false
          ), 1
        );

        for (int i = 0; i < nEpochs; i++) {
            net.fit(trainData);
            testData.reset();

            Evaluation evaluation = new Evaluation();
            while(testData.hasNext()){
                DataSet t = testData.next();
                INDArray dataFeatures = t.getFeatureMatrix();
                INDArray dataLabels = t.getLabels();
                INDArray inMask = t.getFeaturesMaskArray();
                INDArray outMask = t.getLabelsMaskArray();
                INDArray predicted = net.output(dataFeatures, false, inMask, outMask);

                evaluation.evalTimeSeries(dataLabels, predicted, outMask);
            }

            testData.reset();
            System.out.println(evaluation.stats());
        }
    }

    private void getModelData() throws Exception{
        File modelDir = new File(EXTRACT_DATA_PATH);
        if(!modelDir.exists()){
            modelDir.mkdir();
        }
        String archivePath = EXTRACT_DATA_PATH + "aclImdb_v1.tar.gz";
        File archiveName = new File(archivePath);
        File extractName = new File(EXTRACT_DATA_PATH + "aclImdb");
        if(!archiveName.exists()){
            FileUtils.copyURLToFile(new URL(TRAINING_DATA_URL), archiveName);
            extractTar(archivePath, EXTRACT_DATA_PATH);
        }else if(!extractName.exists()){
            extractTar(archivePath, EXTRACT_DATA_PATH);
        }
    }

    private void extractTar(String dataIn, String dataOut) throws Exception{
        try(TarArchiveInputStream inStream = new TarArchiveInputStream(
                new GzipCompressorInputStream(
                        new BufferedInputStream(
                                new FileInputStream(dataIn))))){

            TarArchiveEntry tarFile;
            while((tarFile = (TarArchiveEntry)inStream.getCurrentEntry())!=null){
                if(tarFile.isDirectory()){
                    new File(dataOut + tarFile.getName()).mkdirs();
                }else{
                    int count;
                    byte[]data = new byte[BUFFER_SIZE];

                    FileOutputStream fileInStream = new FileOutputStream(dataOut + tarFile.getName());
                    BufferedOutputStream outStream = new BufferedOutputStream(fileInStream, BUFFER_SIZE);

                    while((count = inStream.read(data, 0, BUFFER_SIZE))!=-1){
                        outStream.write(data, 0, count);
                    }
                }
            }
        }
    }
}
