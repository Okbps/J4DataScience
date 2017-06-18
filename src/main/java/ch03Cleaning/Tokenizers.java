package ch03Cleaning;

import com.google.common.base.Splitter;
import org.apache.commons.lang.text.StrTokenizer;

import java.util.StringTokenizer;

import static util.Globals.DIRTY_TEXT;

/**
 * Created by Aspire on 18.06.2017.
 */
public class Tokenizers {
    public static void main(String[] args) {
        Tokenizers tokenizers = new Tokenizers();

//        tokenizers.tokenizeJdk(DIRTY_TEXT);
//        tokenizers.tokenizeApache(DIRTY_TEXT);
        tokenizers.tokenizeGuava(DIRTY_TEXT);
    }

    void tokenizeJdk(String dirtyText){
        StringTokenizer tokenizer = new StringTokenizer(dirtyText, " ");
        while(tokenizer.hasMoreTokens()){
            System.out.print(tokenizer.nextToken() + " ");
        }
    }

    void tokenizeApache(String dirtyText){
        StrTokenizer tokenizer = new StrTokenizer(dirtyText, ",");
        while(tokenizer.hasNext()){
            System.out.print(tokenizer.next() + " ");
        }
    }

    void tokenizeGuava(String dirtyText){
        Splitter simpleSplit = Splitter.on(',').omitEmptyStrings().trimResults();
        Iterable<String>words = simpleSplit.split(dirtyText);
        for(String token: words){
            System.out.print(token);
        }
    }
}
