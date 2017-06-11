package ch02DataAcquisiotion.webscraping;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

/**
 * Created by Aspire on 11.06.2017.
 */
public class Scraper {
    public static void main(String[] args) {
        Scraper scraper = new Scraper();

        Document document = scraper.getFromFile();

        if(document!=null) {
            System.out.println("Title: " + document.title());
//            System.out.println("Text: " + document.select("body").text());

            Elements images = document.select("img[src$=.png]");
            images.forEach(image -> System.out.println("Image: " + image));

            Elements links = document.select("a[href]");
            links.forEach(link -> System.out.println(
                    "Link: "+link.attr("href")
                    +" Text: "+link.text()
            ));
        }
    }

    Document getFromUrl(){
        try {
            return Jsoup.connect("https://en.wikipedia.org/wiki/Data_science").get();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    Document getFromString(){
        String html = "<html>\n"
                + "<head><title>Example Document</title></head>\n"
                + "<body>\n"
                + "<p>The body of the document</p>\n"
                + "Interesting Links:\n"
                + "<br>\n"
                + "<a href=\"https://en.wikipedia.org/wiki/Data_science\">" +
                "DataScience</a>\n"
                + "<br>\n"
                + "<a href=\"https://en.wikipedia.org/wiki/Jsoup\">" +
                "Jsoup</a>\n"
                + "<br>\n"
                + "Images:\n"
                + "<br>\n"
                + " <img src=\"eyechart.jpg\" alt=\"Eye Chart\"> \n"
                + "</body>\n"
                + "</html>";

        return Jsoup.parse(html);
    }

    Document getFromFile(){
        File file = new File("src/main/resources/Example.html");
        try {
            return Jsoup.parse(file, "UTF-8", "");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
