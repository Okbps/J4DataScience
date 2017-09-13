package ch12Altogether;

import com.aliasi.classify.Classification;
import com.aliasi.classify.Classified;
import com.aliasi.classify.DynamicLMClassifier;
import com.aliasi.classify.LMClassifier;
import com.aliasi.lm.NGramProcessLM;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

import static util.Globals.RESOURCE_FOLDER;

public class SentimentAnalysisTrainingData {
    private static final String MODEL_PATH = RESOURCE_FOLDER+"/txt_sentoken/model";

    public static void main(String[] args) {
        SentimentAnalysisTrainingData satd = new SentimentAnalysisTrainingData();
        satd.buildModel();
        SentimentAnalysisTrainingData.invokeModel();
    }

    private void buildModel(){
        String[]labels = {"neg", "pos"};
        int nGramSize = 8;

        DynamicLMClassifier<NGramProcessLM> classifier =
                DynamicLMClassifier.createNGramProcess(labels, nGramSize);

        try {
            List<String> lines = Files.readAllLines(
                    Paths.get(RESOURCE_FOLDER + "csv/Sentiment Analysis Dataset.csv"),
                    StandardCharsets.ISO_8859_1
            );

            int count = 0;
            for (String s : lines) {
                if (++count == 1) {
                    System.out.println(LocalDateTime.now() + " started");
                    System.out.println(lines.size() + " lines to go");
                    continue;
                }

                String[] oneLine = s.split(",");

                Classification classification = new Classification(
                        oneLine[1].equals("1") ? "pos" : "neg");

                Classified<CharSequence> classified = new Classified<>(oneLine[3], classification);
                classifier.handle(classified);

                if(count%1000==0) System.out.println(count + " processed");
            }

        }catch (IOException e){

        }

        System.out.println(LocalDateTime.now() + " finished");

        try(ObjectOutputStream os = new ObjectOutputStream(
                new FileOutputStream(MODEL_PATH))
        ){
            classifier.compileTo(os);
        } catch (IOException e){
            e.printStackTrace();
        }

        System.out.println(LocalDateTime.now() + " saved");

    }

    public static LMClassifier invokeModel(){
        try(ObjectInput input = new ObjectInputStream(new FileInputStream(MODEL_PATH));
        ){
            return (LMClassifier) input.readObject();
        } catch (IOException|ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
