package ch02DataAcquisiotion;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by Aspire on 10.06.2017.
 */
public class SimpleWebCrawler {
    private String topic;
    private String startingURL;
    private String urlLimiter;
    private final int pageLimit = 20;
    private ArrayList<String> visitedList = new ArrayList<>();
    private ArrayList<String> pageList = new ArrayList<>();

    public static void main(String[] args) {
        new SimpleWebCrawler();
    }

    public SimpleWebCrawler() {
        startingURL = "https://en.wikipedia.org/wiki/Bishop_Rock, Isles_of_Scilly";
        urlLimiter = "Bishop_Rock";
        topic = "shipping route";
        visitPage(startingURL);
    }

    public void visitPage(String url){
        if(pageList.size()>=pageLimit){
            return;
        }

        if(visitedList.contains(url)){

        }else{
            visitedList.add(url);

            try{
                Document doc = Jsoup.connect(url).get();

                if(doc.text().contains(topic)){
                    System.out.println((pageList.size() + 1) + ": [" + url + "]");
                    pageList.add(url);

                    Elements questions = doc.select("a[href]");
                    for(Element link: questions){
                        if(link.attr("href").contains(urlLimiter)){
                            visitPage(link.attr("abs:href"));
                        }
                    }
                }
            }catch(Exception ex){
//                ex.printStackTrace();
            }
        }
    }
}
