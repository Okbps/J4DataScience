package ch12Altogether;

import java.util.Scanner;
import java.util.stream.Stream;

public class ApplicationDriver {
    private String topic;
    private String subTopic;
    private int numberOfTweets;

    public ApplicationDriver() {
        Scanner scanner = new Scanner(System.in);
        TweetHandler swt = new TweetHandler();
        swt.buildSentimentAnalysisModel();

        System.out.println("Welcome to the Tweet Analysis Application");

        System.out.println("Enter a topic: ");
        this.topic = scanner.nextLine();

//        System.out.println("Enter a sub-topic: ");
//        this.subTopic = scanner.nextLine().toLowerCase();

        System.out.println("Enter number of tweets: ");
        this.numberOfTweets = scanner.nextInt();

        performAnalysis();
    }

    public void performAnalysis(){
        Stream<TweetHandler>stream = new TwitterStream(
                this.numberOfTweets, this.topic)
                .stream();

        stream
                .map(TweetHandler::processJSON)
                .map(TweetHandler::toLowerCase)
                .filter(TweetHandler::isEnglish)
                .map(TweetHandler::removeStopWords)
//                .filter(s -> s.containsCharacter(this.subTopic))
                .map(TweetHandler::performSentimentAnalysis)
                .forEach(s -> {
                    s.computeStats();
                    System.out.println(s);
                });

        System.out.println();
        System.out.println("Positive Reviews: " + TweetHandler.getNumberOfPositiveReviews());
        System.out.println("Negative Reviews: " + TweetHandler.getNumberOfNegativeReviews());

    }

    public static void main(String[] args) {
        new ApplicationDriver();
    }
}
