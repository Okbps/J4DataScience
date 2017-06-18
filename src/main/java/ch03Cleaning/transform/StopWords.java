package ch03Cleaning.transform;

import com.aliasi.tokenizer.EnglishStopTokenizerFactory;
import com.aliasi.tokenizer.IndoEuropeanTokenizerFactory;
import com.aliasi.tokenizer.Tokenizer;
import com.aliasi.tokenizer.TokenizerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static util.Globals.DIRTY_TEXT;
import static util.Globals.RESOURCE_FOLDER;

/**
 * Created by Aspire on 18.06.2017.
 */
public class StopWords {
    public static void main(String[] args) {
        StopWords stopWords = new StopWords();
        SimpleClean simpleClean = new SimpleClean();

        List<String>cleanText = simpleClean.simpleCleaning2(DIRTY_TEXT);

        List<String>noStopWordsText = stopWords.removeStopWords(cleanText);

        System.out.println("Text without stop words: " + noStopWordsText);

        stopWords.removeStopWordsLP(DIRTY_TEXT);
    }

    List<String> removeStopWords(List<String>dirtyText){
        try {
            Scanner readStop = new Scanner(new File(RESOURCE_FOLDER+"stopwords.txt"));

            List<String> foundWords = new ArrayList();
            while(readStop.hasNextLine()){
                String stopWord = readStop.nextLine().toLowerCase();
                if(dirtyText.contains(stopWord)){
                    foundWords.add(stopWord);
                }
            }
            dirtyText.removeAll(foundWords);
            return dirtyText;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return dirtyText;
        }
    }

    void removeStopWordsLP(String text){
        text = text.toLowerCase().trim();
        TokenizerFactory fact = IndoEuropeanTokenizerFactory.INSTANCE;
        fact = new EnglishStopTokenizerFactory(fact);
        Tokenizer tok = fact.tokenizer(text.toCharArray(), 0, text.length());
        for(String word: tok){
            System.out.print(word + " ");
        }
    }
}
