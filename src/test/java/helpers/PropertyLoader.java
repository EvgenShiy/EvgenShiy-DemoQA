/*
package helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyLoader {

    private static final Logger log = LoggerFactory.getLogger(PropertyLoader.class);

    public static void loadCredentials() {
        try (FileInputStream fis = new FileInputStream("src/test/resources/properties/credentials.properties")) {

            log.info("Loading credentials from: src/test/resources/properties/credentials.properties");

            Properties properties = new Properties();
            properties.load(fis);

            System.setProperty("profileUserName", properties.getProperty("profileUserName"));
            System.setProperty("profileUserPassword", properties.getProperty("profileUserPassword"));
        } catch (IOException e) {
            log.error("Error loading credentials.properties", e);
            throw new RuntimeException("Error loading credentials.properties", e);
        }
    }
}

 */

package helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyLoader {

    private static final Logger log = LoggerFactory.getLogger(PropertyLoader.class);

    public static void loadCredentials() {
        try (FileInputStream fis = new FileInputStream("src/test/resources/properties/credentials.properties")) {

            log.info("Loading credentials from: src/test/resources/properties/credentials.properties");

            Properties properties = new Properties();
            properties.load(fis);

            System.setProperty("profileUserName", properties.getProperty("profileUserName"));
            System.setProperty("profileUserPassword", properties.getProperty("profileUserPassword"));
        } catch (IOException e) {
            log.error("Error loading credentials.properties", e);
            throw new RuntimeException("Error loading credentials.properties", e);
        }
    }

    /**
     * Читает конкретное свойство из указанного файла свойств.
     *
     * @param filePath путь к файлу свойств.
     * @param key ключ свойства.
     * @return значение свойства.
     */
    public static String getPropertyFromFile(String filePath, String key) {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(filePath)) {
            log.info("Reading property '{}' from file: {}", key, filePath);
            properties.load(fis);
            return properties.getProperty(key);
        } catch (IOException e) {
            log.error("Error reading property '{}' from file: {}", key, filePath, e);
            throw new RuntimeException("Error reading property from file: " + filePath, e);
        }
    }
}
