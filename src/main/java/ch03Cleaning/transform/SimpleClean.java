package ch03Cleaning.transform;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static util.Globals.DIRTY_TEXT;

/**
 * Created by Aspire on 18.06.2017.
 */
public class SimpleClean {
    public static void main(String[] args) {
        SimpleClean simpleClean = new SimpleClean();

        System.out.println("Original dirty text: " + DIRTY_TEXT);

        String cleanText = simpleClean.simpleCleaning1(DIRTY_TEXT);

//        String cleanText = String.join(" ", words);
//        String cleanText = Joiner.on(" ").skipNulls().join(words);

        System.out.println("Original clean text: " + cleanText);

    }

    String simpleCleaning1(String dirtyText){
        dirtyText = dirtyText.toLowerCase().replaceAll("[\\d[^\\w\\s]]+", " ");
        dirtyText = dirtyText.trim();
        while(dirtyText.contains("  ")){
            dirtyText = dirtyText.replaceAll("  ", " ");
        }
        return dirtyText;
    }

    List<String> simpleCleaning2(String dirtyText){
        dirtyText = dirtyText.replaceAll("[\\d[^\\w\\s]]+", "");
        String[]words = dirtyText.toLowerCase().trim().split("[\\W]+");
        return new ArrayList<String>(Arrays.asList(words));
    }
}
