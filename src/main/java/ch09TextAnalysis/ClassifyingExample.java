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
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.io.ClassPathResource;

import java.io.IOException;
import java.util.List;

public class ClassifyingExample {

    public static void main(String[] args) throws IOException {
        ClassifyingExample example = new ClassifyingExample();

        example.test();
    }

    private void test() throws IOException {
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
}
