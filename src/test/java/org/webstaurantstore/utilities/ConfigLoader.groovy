package org.webstaurantstore.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private Properties properties;

    public ConfigLoader() {
        properties = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config/config.properties")) {
            if (inputStream == null) {
                throw new RuntimeException("Configuration file 'config.properties' not found in the classpath");
            }
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Configuration could not be loaded", e);
        }
    }

    public String getBrowser() {
        return properties.getProperty("browser", "chrome");
    }

    public String getTestUrl() {
        return properties.getProperty("testurl", "https://www.webstaurantstore.com/");
    }


}