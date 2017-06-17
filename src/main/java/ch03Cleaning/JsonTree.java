package ch03Cleaning;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.google.gson.JsonElement;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import static util.Globals.RESOURCE_FOLDER;

/**
 * Created by Aspire on 17.06.2017.
 */
public class JsonTree {
    public static void main(String[] args) {
        JsonTree jsonTree = new JsonTree();
        jsonTree.parsePersons();
    }

    void parsePersons(){
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode node = mapper.readTree(new File(RESOURCE_FOLDER + "Persons.json"));
//            Iterator<String>fieldNames = node.fieldNames();
//
//            while(fieldNames.hasNext()){
//                fieldNames.next();
//            }

            JsonNode personNode = node.get("persons");
            Iterator<JsonNode>elements = personNode.iterator();

            while(elements.hasNext()){
                JsonNode element = elements.next();
                JsonNodeType nodeType = element.getNodeType();
                if(nodeType == JsonNodeType.STRING){
                    System.out.println("Group: " + element.textValue());
                }

                if(nodeType == JsonNodeType.ARRAY){
                    Iterator<JsonNode>fields = element.iterator();
                    while(fields.hasNext()){
                        parsePerson(fields.next());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void parsePerson(JsonNode node){
        Iterator<JsonNode>fields = node.iterator();
        while(fields.hasNext()){
            JsonNode subNode = fields.next();
            System.out.println(subNode.asText());
        }
    }

}
