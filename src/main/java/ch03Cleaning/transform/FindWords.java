package ch03Cleaning.transform;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import static util.Globals.DIRTY_TEXT;
import static util.Globals.RESOURCE_FOLDER;

/**
 * Created by Aspire on 18.06.2017.
 */
public class FindWords {
    public static void main(String[] args) {
        FindWords findWords = new FindWords();
//        findWords.findWord(DIRTY_TEXT, "I");
//        findWords.findWordScanner(DIRTY_TEXT, "I");
        findWords.findWordBuffer("I");
    }

    void findWord(String text, String toFind){
        text = text.toLowerCase().trim();
        toFind = toFind.toLowerCase().trim();
        int count = 0;

        if(text.contains(toFind)){
            String[]words = text.split(" ");
            for(String word: words){
                if(word.equals(toFind)){
                    count++;
                }
            }
        }
        System.out.println("Found " + toFind + " " + count + " times in the text.");
    }

    void findWordScanner(String text, String toFind){
        text = text.toLowerCase().trim();
        toFind = toFind.toLowerCase().trim();
        Scanner textLine = new Scanner(text);
        System.out.println("Found " + textLine.findWithinHorizon(toFind, 10));
    }

    void findWordBuffer(String toFind){
        String path = RESOURCE_FOLDER+"txt/Ishmael.txt";
        try{
            String textLine = "";
            toFind = toFind.toLowerCase().trim();
            BufferedReader reader = new BufferedReader(new FileReader(path));
            while((textLine = reader.readLine()) != null){
                if(textLine.toLowerCase().trim().contains(toFind)){
                    System.out.println("Found " + toFind + " in \"" + textLine + "\"");
                }
            }
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
