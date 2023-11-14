package com.example.envoisrecois.outils;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
public class RecupConfig {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = RecupConfig.class.getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getUsername() {
        return properties.getProperty("username");
    }

    public static String getPassword() {
        return properties.getProperty("password");
    }
}
