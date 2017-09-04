package ch10VisualAudio;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;
import edu.cmu.sphinx.result.WordResult;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;

import static util.Globals.RESOURCE_FOLDER;

public class CMUPhinxExample {
    public static void main(String[] args) throws IOException {
        Configuration configuration = new Configuration();

        // https://github.com/cmusphinx/sphinx4/tree/master/sphinx4-data/src/main/resources/edu/cmu/sphinx/models
        String prefix = RESOURCE_FOLDER + "sphinx/en-us/";
        configuration.setAcousticModelPath(prefix   + "en-us");
        configuration.setDictionaryPath(prefix      + "cmudict-en-us.dict");
        configuration.setLanguageModelPath(prefix   + "en-us.lm.bin");

        StreamSpeechRecognizer recognizer = new StreamSpeechRecognizer(configuration);
        InputStream stream = new FileInputStream(new File(RESOURCE_FOLDER+"PCM_16BIT-MONO-44100-3862.pcm"));
        recognizer.startRecognition(stream);
        SpeechResult result;
        while((result = recognizer.getResult()) != null){
            System.out.println("Hypothesis: " + result.getHypothesis());

            Collection<String>results = result.getNbest(3);
            results.forEach(System.out::println);

            List<WordResult>words = result.getWords();
            for (WordResult wordResult : words) {
                System.out.print(wordResult.getWord() + " ");

                System.out.printf("%s\n\tConfidence: %.3f\n\tTime Frame: %s\n",
                        wordResult.getWord(),
                        result.getResult().getLogMath().logToLinear((float)wordResult.getConfidence()),
                        wordResult.getTimeFrame());
            }
        }
        recognizer.stopRecognition();
    }
}
