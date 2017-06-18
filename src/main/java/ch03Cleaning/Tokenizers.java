package ch03Cleaning;

import com.google.common.base.Splitter;
import org.apache.commons.lang.text.StrTokenizer;

import java.util.StringTokenizer;

/**
 * Created by Aspire on 18.06.2017.
 */
public class Tokenizers {
    public static void main(String[] args) {
        String dirtyText = "Call me Ishmael. Some years ago- never mind how"
        + " long precisely - having little or no money in my purse,"
        + " and nothing particular to interest me on shore, I thought"
        + " I would sail about a little and see the watery part of the world.";

        Tokenizers tokenizers = new Tokenizers();

//        tokenizers.tokenizeJdk(dirtyText);
//        tokenizers.tokenizeApache(dirtyText);
        tokenizers.tokenizeGoogle(dirtyText);
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

    void tokenizeGoogle(String dirtyText){
        Splitter simpleSplit = Splitter.on(',').omitEmptyStrings().trimResults();
        Iterable<String>words = simpleSplit.split(dirtyText);
        for(String token: words){
            System.out.print(token);
        }
    }
}
