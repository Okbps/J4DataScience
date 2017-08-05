package ch09TextAnalysis;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTagger;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.util.Sequence;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static util.Globals.RESOURCE_FOLDER;

// POS - part of speech
public class POSExample {
    public static void main(String[] args) {
        try(InputStream input = new FileInputStream(
                new File(
                        RESOURCE_FOLDER + "models/en-pos-maxent.bin"));){

            String sentence = "Let's parse this sentence.";

            List<String> list = new ArrayList<>();
            Scanner scanner = new Scanner(sentence);

            while(scanner.hasNext()){
                list.add(scanner.next());
            }
            String[]words = new String[1];
            words = list.toArray(words);

            POSModel posModel = new POSModel(input);
            POSTagger posTagger = new POSTaggerME(posModel);

            String[]posTags = posTagger.tag(words);
            for (int i = 0; i < posTags.length; i++) {
                System.out.println(words[i] + " - " + posTags[i]);
            }

            Sequence sequence[] = posTagger.topKSequences(words);
            for (Sequence s : sequence) {
                System.out.println(s);
            }

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
