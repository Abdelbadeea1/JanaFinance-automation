package com.jana.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties;
    private static final String CONFIG_PATH =
            "src/main/resources/config.properties";

    // Load properties once when class is first used
    static {
        try {
            FileInputStream fis = new FileInputStream(CONFIG_PATH);
            properties = new Properties();
            properties.load(fis);
            fis.close();
            System.out.println("Config loaded successfully");
        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to load config.properties at: " + CONFIG_PATH, e
            );
        }
    }

    // Get any property by key
    public static String get(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException(
                    "Property '" + key + "' not found in config.properties"
            );
        }
        return value.trim();
    }
}