package ch03Cleaning.transform;

import com.google.common.base.CharMatcher;
import org.apache.commons.lang3.StringUtils;

import static util.Globals.DIRTY_TEXT;

/**
 * Created by Aspire on 18.06.2017.
 */
public class ReplaceText {
    public static void main(String[] args) {
        ReplaceText replaceText = new ReplaceText();
//        replaceText.replace(DIRTY_TEXT, "I", "Ishmael");
//        replaceText.replaceApache(DIRTY_TEXT);
//        replaceText.replacePatternApache(DIRTY_TEXT);
        replaceText.replaceGuava(DIRTY_TEXT);
    }

    void replace(String text, String toFind, String replaceWith){
        text = text.toLowerCase().trim();
        toFind = toFind.toLowerCase().trim();
        if(text.contains(toFind)){
            text = text.replaceAll(toFind, replaceWith);
            System.out.println(text);
        }
    }

    void replaceApache(String text){
        System.out.println(StringUtils.replace(text, "me", "X"));
    }

    void replacePatternApache(String text){
        System.out.println(text);
        System.out.println(StringUtils.replacePattern(text, "\\W\\s", " "));
    }

    void replaceGuava(String text){
        text = text.replace("me", " ");
        System.out.println("With double spaces: " + text);
        String spaced = CharMatcher.whitespace().trimAndCollapseFrom(text, ' ');
        System.out.println("With double spaces removed: " + spaced);
    }
}
