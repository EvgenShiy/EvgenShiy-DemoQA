package helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyLoader {

    private static final Logger logger = LoggerFactory.getLogger(PropertyLoader.class);

    public static void loadCredentials() {
        try (FileInputStream fis = new FileInputStream("src/test/resources/properties/credentials.properties")) {

            logger.info("Loading credentials from: src/test/resources/properties/credentials.properties");

            Properties properties = new Properties();
            properties.load(fis);

            System.setProperty("profileUserName", properties.getProperty("profileUserName"));
            System.setProperty("profileUserPassword", properties.getProperty("profileUserPassword"));
        } catch (IOException e) {
            logger.error("Error loading credentials.properties", e);
            throw new RuntimeException("Error loading credentials.properties", e);
        }
    }
}