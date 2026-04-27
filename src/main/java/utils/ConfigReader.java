package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
* ConfigReader — קריאת הגדרות מקובץ config.properties
* TMDB API key, base URLs, device capabilities :כולל
 */
public class ConfigReader {

    private static Properties properties = new Properties();

    static {
        try {
            FileInputStream fis = new FileInputStream("config/config.properties");
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("❌ Cannot load config.properties: " + e.getMessage());
        }
    }

    public static String get(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("❌ Key not found in config: " + key);
        }
        return value;
    }
}
