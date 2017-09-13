package ch12Altogether;

import com.aliasi.classify.Classification;
import com.aliasi.classify.DynamicLMClassifier;
import com.aliasi.classify.LMClassifier;
import com.aliasi.tokenizer.EnglishStopTokenizerFactory;
import com.aliasi.tokenizer.IndoEuropeanTokenizerFactory;
import com.aliasi.tokenizer.Tokenizer;
import com.aliasi.tokenizer.TokenizerFactory;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TweetHandler {
    private String jsonText;
    private String text;
    private String language;
    private String userName;
    private String category;
    private static int numberOfPositiveReviews = 0;
    private static int numberOfNegativeReviews = 0;
    private Date date;

    private static String[]labels = {"neg", "pos"};
    private static int nGramSize = 8;

    private static LMClassifier classifier =
            DynamicLMClassifier.createNGramProcess(labels, nGramSize);

    public void buildSentimentAnalysisModel(){
        System.out.println("Loading Sentiment Model");
        classifier = SentimentAnalysisTrainingData.invokeModel();
    };

    public TweetHandler() {
        this.jsonText = "";
    }

    public TweetHandler(String jsonText) {
        this.jsonText = jsonText;
    }

    public TweetHandler processJSON(){
        try{
            JSONObject jsonObject = new JSONObject(this.jsonText);
            this.text = jsonObject.getString("text");
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy", Locale.ENGLISH);

            try{
                this.date = sdf.parse(jsonObject.getString("created_at"));
            }catch (ParseException e){
                e.printStackTrace();
            }
            this.language = jsonObject.getString("lang");
            JSONObject user = jsonObject.getJSONObject("user");
            this.userName = user.getString("name");
        }catch(JSONException e){
            e.printStackTrace();
        }

        return this;
    }

    public TweetHandler toLowerCase(){
        this.text = this.text.toLowerCase().trim();
        return this;
    }

    public boolean isEnglish(){
        return this.language.equalsIgnoreCase("en");
    }

    public TweetHandler removeStopWords(){
        TokenizerFactory tokenizerFactory = IndoEuropeanTokenizerFactory.INSTANCE;
        tokenizerFactory = new EnglishStopTokenizerFactory(tokenizerFactory);
        Tokenizer tokens = tokenizerFactory.tokenizer(this.text.toCharArray(), 0, this.text.length());
        StringBuilder buffer = new StringBuilder();
        for (String word : tokens) {
            buffer.append(word + " ");
        }
        this.text = buffer.toString();
        return this;
    }

    public boolean containsCharacter(String character){
        return this.text.contains(character);
    }

    public TweetHandler performSentimentAnalysis(){
        Classification classification = classifier.classify(this.text);
        this.category = classification.bestCategory();
        return this;
    }

    public void computeStats(){
        if(this.category.equalsIgnoreCase("pos")){
            numberOfPositiveReviews++;
        }else{
            numberOfNegativeReviews++;
        }
    }

    public static int getNumberOfPositiveReviews() {
        return numberOfPositiveReviews;
    }

    public static int getNumberOfNegativeReviews() {
        return numberOfNegativeReviews;
    }

    @Override
    public String toString() {
        return "\nText: " + this.text
                + "\nDate: " + this.date
                + "\nCategory: " + this.category;
    }
}
