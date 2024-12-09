package helpers;

import java.io.IOException;
import java.util.Properties;

public class PropertiesLoader {
    private static final Properties properties = new Properties();

    static {
        try (var inputStream = PropertiesLoader.class.getClassLoader().getResourceAsStream("credentials.properties")) {
            if (inputStream == null) {
                throw new IllegalStateException("Файл credentials.properties не найден в resources.");
            }
            properties.load(inputStream);
        } catch (IOException e) {
            throw new IllegalStateException("Не удалось загрузить файл credentials.properties", e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}