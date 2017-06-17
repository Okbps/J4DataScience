package ch03Cleaning;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.File;
import java.io.IOException;

import static util.Globals.RESOURCE_FOLDER;

/**
 * Created by Aspire on 17.06.2017.
 */
public class JsonStreaming {
    public static void main(String[] args) {
        JsonStreaming streaming = new JsonStreaming();
        streaming.parsePersons();
    }

    void parsePersons(){
        File file = new File(RESOURCE_FOLDER + "Persons.json");

        try {
            JsonFactory jsonfactory = new JsonFactory();
            JsonParser parser = jsonfactory.createParser(file);
            while (parser.nextToken() != JsonToken.END_OBJECT) {
                String token = parser.getCurrentName();
                if("groupname".equals(token)) {
                    parser.nextToken();
                    String groupname = parser.getText();
                    System.out.println("Group: "+groupname);
                    parser.nextToken();
                    token = parser.getCurrentName();
                    if("person".equals(token)) {
                        parsePerson(parser);
                    }
                }
            }
            parser.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    void parsePerson(JsonParser parser) throws IOException {
        while(parser.nextToken() != null){
            String token = parser.getCurrentName();
            if ("firstname".equals(token)) {
                parser.nextToken();
                String fname = parser.getText();
                System.out.println("firstname : " + fname);
            }
            if ("lastname".equals(token)) {
                parser.nextToken();
                String lname = parser.getText();
                System.out.println("lastname : " + lname);
            }
            if ("phone".equals(token)) {
                parser.nextToken();
                long phone = parser.getLongValue();
                System.out.println("phone : " + phone);
            }
            if ("address".equals(token)) {
                parser.nextToken();
                while(parser.nextToken() != JsonToken.END_ARRAY) {
                    System.out.println(parser.getText());
                }
            }
        }
    }
}
