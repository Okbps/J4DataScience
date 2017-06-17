package ch01GettingStarted;

import com.aliasi.tokenizer.EnglishStopTokenizerFactory;
import com.aliasi.tokenizer.IndoEuropeanTokenizerFactory;
import com.aliasi.tokenizer.Tokenizer;
import com.aliasi.tokenizer.TokenizerFactory;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.*;

import static util.Globals.RESOURCE_FOLDER;

/**
 * Created by Aspire on 06.06.2017.
 */
public class Cleaning {

    public static void main(String[] args){
        stopWords();
    }

    static void stopWords(){
        String text = "";

        try {
            FileInputStream fis = new FileInputStream(RESOURCE_FOLDER + "Ishmael.txt");
            byte[] bytes = new byte[fis.available()];
            fis.read(bytes);
            text = new String(bytes);
            fis.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        text = text.toLowerCase().trim();
        TokenizerFactory fact = IndoEuropeanTokenizerFactory.INSTANCE;
        fact = new EnglishStopTokenizerFactory(fact);
        Tokenizer tok = fact.tokenizer(text.toCharArray(), 0, text.length());
        tok.forEach(s -> System.out.print(s + " "));
    }

    static void readJson(){
        File file = new File(RESOURCE_FOLDER + "Person.json");

        try {
            JsonFactory jsonfactory = new JsonFactory();
            JsonParser parser = jsonfactory.createParser(file);
            while (parser.nextToken() != JsonToken.END_OBJECT) {
                String token = parser.getCurrentName();
                if ("firstname".equals(token)) {
                    parser.nextToken();
                    String fname = parser.getText();
                    System.out.println("firstname : " + fname);
                }
            }
            parser.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
