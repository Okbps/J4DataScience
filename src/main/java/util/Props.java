package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Aspire on 14.06.2017.
 */
public class Props {
    private static final Properties PROPERTIES = new Properties();

    static{
        try {
            PROPERTIES.load(new FileInputStream("src/main/resources/template.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key){
        return (String) PROPERTIES.get(key);
    }
}
