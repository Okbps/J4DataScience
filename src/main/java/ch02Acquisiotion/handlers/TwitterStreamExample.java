package ch02Acquisiotion.handlers;

import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.StatusesSampleEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.BasicClient;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import util.Props;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by Aspire on 11.06.2017.
 */
public class TwitterStreamExample {

    public static void main(String[] args) throws IOException {

        try {
            TwitterStreamExample.streamTwitter(
                    Props.getProperty("consumerKey"),
                    Props.getProperty("consumerSecret"),
                    Props.getProperty("accessToken"),
                    Props.getProperty("accessSecret")
            );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void streamTwitter(
            String consumerKey, String consumerSecret, String accessToken, String accessSecret)
    throws InterruptedException{

        BlockingQueue<String>statusQueue = new LinkedBlockingQueue<String>(10000);
        StatusesSampleEndpoint ending = new StatusesSampleEndpoint();
        ending.stallWarnings(false);

        Authentication twitterAuth = new OAuth1(consumerKey, consumerSecret, accessToken, accessSecret);

        BasicClient twitterClient = new ClientBuilder()
                .name("Twitter client")
                .hosts(Constants.STREAM_HOST)
                .endpoint(ending)
                .authentication(twitterAuth)
                .processor(new StringDelimitedProcessor(statusQueue))
                .build();

        twitterClient.connect();

        for (int msgRead = 0; msgRead < 1000; msgRead++) {
            if(twitterClient.isDone()){
                System.out.println(twitterClient.getExitEvent().getMessage());
                break;
            }

            String msg = statusQueue.poll(10, TimeUnit.SECONDS);
            if(msg==null){
                System.out.println("Waited 10 seconds - no message received");
            }else{
                System.out.println(msg);
            }
        }

        twitterClient.stop();
    }
}
