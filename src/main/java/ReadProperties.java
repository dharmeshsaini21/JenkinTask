import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ReadProperties {

    private ReadProperties(){}
    public static Map<Object, Object> readPropertiesAsMap(String path) {
        FileInputStream inputStream = null;
        Properties properties = null;

        try {
            inputStream = new FileInputStream(path);
            properties = new Properties();
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            assert inputStream != null;
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new HashMap<>(properties);
    }

    public static Object readPropertyValue(String path, String key) {
        FileInputStream inputStream = null;
        Properties properties = null;

        try {
            inputStream = new FileInputStream(path);
            properties = new Properties();
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            assert inputStream != null;
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return properties.getOrDefault(key, key);
    }
}