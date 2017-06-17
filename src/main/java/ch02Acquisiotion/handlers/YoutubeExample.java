package ch02Acquisiotion.handlers;

import com.github.axet.wget.WGet;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import util.Props;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.services.youtube.YouTube;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by Aspire on 13.06.2017.
 */
public class YoutubeExample {
    public static void main(String[] args) {
        YoutubeExample youtubeExample = new YoutubeExample();

        try {
            youtubeExample.searchVideos();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            youtubeExample.downloadVideos();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    void searchVideos() throws IOException{
        YouTube youtube = new YouTube.Builder(
                Auth.HTTP_TRANSPORT,
                Auth.JSON_FACTORY,
                new HttpRequestInitializer() {
                    @Override
                    public void initialize(HttpRequest request) throws IOException {

                    }
                })
                .setApplicationName("application_name")
                .build();

        YouTube.Search.List search = youtube.search().list("id,snippet");

        search.setKey(Props.getProperty("youtubeKey"));
        search.setQ("cats");
        search.setType("video");
        search.setFields("items(id/kind," +
                "id/videoId," +
                "snippet/title," +
                "snippet/description," +
                "snippet/thumbnails/default/url)");
        search.setMaxResults(10L);

        SearchListResponse searchResponse = search.execute();
        List<SearchResult> searchResultList = searchResponse.getItems();

        searchResultList.iterator().forEachRemaining(video ->
                System.out.println(
                        String.format("Kind: %s\nVideo Id: %s\nTitle: %s\nDescription: %s\nThumbnail: %s",
                                video.getKind(),
                                video.getId(),
                                video.getSnippet().getTitle(),
                                video.getSnippet().getDescription(),
                                video.getSnippet().getThumbnails().getDefault().getUrl())));
    }

    void downloadVideos() throws MalformedURLException {
        String url = "https://www.youtube.com/watch?v=videoID";
        String path = ".";
        WGet wGet = new WGet(new URL(url), new File(path));
        wGet.download();
    }
}
