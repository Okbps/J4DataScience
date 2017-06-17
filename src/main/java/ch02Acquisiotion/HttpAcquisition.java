package ch02Acquisiotion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Aspire on 10.06.2017.
 */
public class HttpAcquisition {
    void connect(){
        try {
            URL url = new URL("https://en.wikipedia.org/wiki/Data_science");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            System.out.println(connection.getResponseCode());
            System.out.println(connection.getContentType());
            System.out.println(connection.getContentLength());

            displayContent(connection.getContent());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void displayContent(Object content){
        InputStreamReader isr = new InputStreamReader((InputStream) content);
        BufferedReader br = new BufferedReader(isr);
        StringBuilder buffer = new StringBuilder();
        String line = "";

        do{
            try {
                line = br.readLine();
                buffer.append(line);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }while(line!=null);

        System.out.println(buffer);
    }
}
