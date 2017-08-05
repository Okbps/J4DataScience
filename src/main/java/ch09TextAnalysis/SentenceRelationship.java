package ch09TextAnalysis;

import opennlp.tools.cmdline.parser.ParserTool;
import opennlp.tools.parser.Parse;
import opennlp.tools.parser.Parser;
import opennlp.tools.parser.ParserFactory;
import opennlp.tools.parser.ParserModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static util.Globals.RESOURCE_FOLDER;

public class SentenceRelationship {
    public static void main(String[] args) {
        try (InputStream modelInputStream = new FileInputStream(
                new File(RESOURCE_FOLDER + "models/en-parser-chunking.bin")
        );){

            ParserModel parserModel = new ParserModel(modelInputStream);
            Parser parser = ParserFactory.create(parserModel);

            String sentence =  "Let's parse this sentence.";
            Parse[]parseTrees = ParserTool.parseLine(sentence, parser, 3);

            for (Parse tree : parseTrees) {
                System.out.println("Probability: " + tree.getProb());
                tree.show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
