package ch09TextAnalysis;


import ch09TextAnalysis.tools.LabelSeeker;
import ch09TextAnalysis.tools.MeansBuilder;
import org.deeplearning4j.berkeley.Pair;
import org.deeplearning4j.models.embeddings.inmemory.InMemoryLookupTable;
import org.deeplearning4j.models.paragraphvectors.ParagraphVectors;
import org.deeplearning4j.models.word2vec.VocabWord;
import org.deeplearning4j.text.documentiterator.FileLabelAwareIterator;
import org.deeplearning4j.text.documentiterator.LabelAwareIterator;
import org.deeplearning4j.text.documentiterator.LabelledDocument;
import org.deeplearning4j.text.documentiterator.LabelsSource;
import org.deeplearning4j.text.sentenceiterator.BasicLineIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ClassifyingExample {

    public static void main(String[] args) throws IOException {
        ClassifyingExample example = new ClassifyingExample();

//        example.classifyByLabel();
        example.classifyBySimilarity();
    }

    private void classifyByLabel() throws IOException {
        ParagraphVectors pVect;
        LabelAwareIterator iter;
        TokenizerFactory tFact;

        ClassPathResource resource = new ClassPathResource("txt/labeled");
        iter = new FileLabelAwareIterator.Builder()
                .addSourceFolder(resource.getFile()).build();

        tFact = new DefaultTokenizerFactory();
        tFact.setTokenPreProcessor(new CommonPreprocessor());

        pVect = new ParagraphVectors.Builder()
                .learningRate(0.025)
                .minLearningRate(0.001)
                .batchSize(1000)
                .epochs(20)
                .iterate(iter)
                .trainWordVectors(true)
                .tokenizerFactory(tFact)
                .build();

        pVect.fit();

        ClassPathResource unlabeledText = new ClassPathResource("txt/unlabeled");
        FileLabelAwareIterator unlabeledIter = new FileLabelAwareIterator.Builder()
                .addSourceFolder(unlabeledText.getFile()).build();

        MeansBuilder mBuilder = new MeansBuilder((InMemoryLookupTable<VocabWord>)
                pVect.getLookupTable(), tFact);

        LabelSeeker lSeeker = new LabelSeeker(iter.getLabelsSource().getLabels(),
                (InMemoryLookupTable<VocabWord>)pVect.getLookupTable());

        while(unlabeledIter.hasNextDocument()){
            LabelledDocument doc = unlabeledIter.nextDocument();
            INDArray docCentroid = mBuilder.documentAsVector(doc);
            List<Pair<String,Double>> scores = lSeeker.getScores(docCentroid);
            System.out.println("Document '"
                    + doc.getLabels().get(0)
                    + "' falls into the following categories: ");

            scores.forEach(
                    score -> System.out.println(
                            " " + score.getFirst() + ": " + score.getSecond()));
        }

    }

    private void classifyBySimilarity() throws IOException {
        ClassPathResource srcFile = new ClassPathResource("/txt/raw_sentences.txt");
        File file = srcFile.getFile();
        SentenceIterator iter = new BasicLineIterator(file);

        TokenizerFactory tFact = new DefaultTokenizerFactory();
        tFact.setTokenPreProcessor(new CommonPreprocessor());
        LabelsSource labelFormat = new LabelsSource("LINE_");

        ParagraphVectors vec = new ParagraphVectors.Builder()
                .minWordFrequency(1)
                .iterations(5)
                .epochs(1)
                .layerSize(100)
                .learningRate(0.025)
                .labelsSource(labelFormat)
                .windowSize(5)
                .iterate(iter)
                .trainWordVectors(false)
                .tokenizerFactory(tFact)
                .sampling(0)
                .build();

        vec.fit();

        double similar1 = vec.similarity("LINE_9835", "LINE_12492");
        System.out.println("Comparing lines 9836 & 12493" +
                "('This is my house.'/'This is my world.')" +
                "Similarity = " + similar1);

        double similar2 = vec.similarity("LINE_3720", "LINE_16392");
        System.out.println("Comparing lines 3721 & 16393" +
                "('This is my way .'/'This is my work .')" +
                "Similarity = " + similar2);

        double similar3 = vec.similarity("LINE_6347", "LINE_3720");
        System.out.println("Comparing lines 6348 & 3721" +
                "('This is my case .'/'This is my way .')" +
                "Similarity = " + similar3);

        double dissimilar1 = vec.similarity("LINE_3720", "LINE_9852");
        System.out.println("Comparing lines 3721 & 9853" +
                "('This is my way .'/'We now have one .')" +
                "Similarity = " + dissimilar1);

        double dissimilar2 = vec.similarity("LINE_3720", "LINE_3719");
        System.out.println("Comparing lines 3721 & 3720" +
                "('This is my way .'/'At first he says no .')" +
                "Similarity = " + dissimilar2);
    }
}
