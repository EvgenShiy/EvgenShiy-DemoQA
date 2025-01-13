package helpers;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyLoader {
    public static void loadCredentials() {
        try (FileInputStream fis = new FileInputStream("src/test/resources/properties/credentials.properties")) {

            System.out.println("Loading credentials from: src/test/resources/properties/credentials.properties");

            Properties properties = new Properties();
            properties.load(fis);

            System.setProperty("profileUserName", properties.getProperty("profileUserName"));
            System.setProperty("profileUserPassword", properties.getProperty("profileUserPassword"));
        } catch (IOException e) {
            throw new RuntimeException("Error loading credentials.properties", e);
        }
    }
}