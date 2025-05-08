package Utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
    private static final Properties properties = new Properties();

    static {
        loadProperties();
    }

    public static void loadProperties() {
        try (InputStream input = PropertiesUtil.class.getClassLoader()
                .getResourceAsStream("config.properties")) {

            if (input == null) {
                throw new RuntimeException("config.properties not found in classpath");
            }
            properties.load(input);
            Logs.info("Successfully loaded properties file");

            // Also load system properties
            properties.putAll(System.getProperties());

        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public static String getPropertyValue(String key) {
        String value = properties.getProperty(key);
        if (value == null || value.trim().isEmpty()) {
            throw new RuntimeException("Property '" + key + "' not found or is empty in config.properties");
        }
        return value;
    }
}