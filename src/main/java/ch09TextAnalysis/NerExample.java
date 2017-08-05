package ch09TextAnalysis;

// Named Entity Recognition

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static util.Globals.RESOURCE_FOLDER;

// NER - named entity recognition
public class NerExample {
    public static void main(String[] args) {
        try(
                InputStream tokenStream = new FileInputStream(
                        new File(RESOURCE_FOLDER+"models/en-token.bin"));
                InputStream personModelStream = new FileInputStream(
                        new File(RESOURCE_FOLDER+"models/en-ner-person.bin"));
                InputStream locationModelStream = new FileInputStream(
                        new File(RESOURCE_FOLDER+"models/en-ner-location.bin"));
        ){
            TokenizerModel tm = new TokenizerModel(tokenStream);
            TokenizerME tokenizer = new TokenizerME(tm);

            TokenNameFinderModel personModel = new TokenNameFinderModel(personModelStream);
            NameFinderME personFinder = new NameFinderME(personModel);

            findTokens("Mrs. Wilson went to Mary's house for dinner.",
                    tokenizer,
                    personFinder);

            TokenNameFinderModel locationModel = new TokenNameFinderModel(locationModelStream);
            NameFinderME locationFinder = new NameFinderME(locationModel);

            findTokens( "Enid is located north of Oklahoma City.",
                    tokenizer,
                    locationFinder);

            findTokens( "Pond Creek is located north of Oklahoma City.",
                    tokenizer,
                    locationFinder);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void findTokens(String sentence, TokenizerME tokenizer, NameFinderME nf){
        String[] tokens = tokenizer.tokenize(sentence);

        Span[] spans = nf.find(tokens);

        for (int i = 0; i < spans.length; i++) {
            System.out.println(spans[i] + " - " + tokens[spans[i].getStart()]);
        }
    }
}
