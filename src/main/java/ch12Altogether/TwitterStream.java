package ch12Altogether;

import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.StatusesSampleEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.BasicClient;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import util.Props;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Stream;

public class TwitterStream {
    private String topic;
    private int numberOfTweets;

    public TwitterStream(int numberOfTweets, String topic) {
        this.topic = topic;
        this.numberOfTweets = numberOfTweets;
    }

    public TwitterStream() {
        this(100, "Star Wars");
    }

    Stream<TweetHandler>stream(){
        String consumerKey      = Props.getProperty("consumerKey");
        String consumerSecret   = Props.getProperty("consumerSecret");
        String accessToken      = Props.getProperty("accessToken");
        String accessSecret     = Props.getProperty("accessSecret");

        BlockingQueue<String> statusQueue = new LinkedBlockingQueue<String>(10000);
        StatusesSampleEndpoint ending = new StatusesSampleEndpoint();
        ending.stallWarnings(false);

        Authentication twitterAuth = new OAuth1(consumerKey, consumerSecret, accessToken, accessSecret);

        System.out.println("Creating Twitter Stream");

        BasicClient twitterClient = new ClientBuilder()
                .name("Twitter client")
                .hosts(Constants.STREAM_HOST)
                .endpoint(ending)
                .authentication(twitterAuth)
                .processor(new StringDelimitedProcessor(statusQueue))
                .build();

        twitterClient.connect();

        List<TweetHandler>list = new ArrayList<>();
        List<String>twitterList = new ArrayList<>();

        statusQueue.drainTo(twitterList);
        for (int i = 0; i < numberOfTweets; i++) {
            String message;
            try{
                message = statusQueue.take();
                list.add(new TweetHandler(message));
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }

        twitterClient.stop();
        System.out.printf("%d messages processed!\n", twitterClient.getStatsTracker().getNumMessages());

        return list.stream();
    }
}
