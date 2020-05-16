package main;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ReadPropertyFile {
    Properties prop = null;
    private static ReadPropertyFile single_instance = null;

    private ReadPropertyFile() {
        try {
            FileInputStream ip = new FileInputStream("config/config.properties");
            prop = new Properties();
            prop.load(ip);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // static method to create instance of Singleton class
    public static ReadPropertyFile getInstance() {
        if (single_instance == null)
            single_instance = new ReadPropertyFile();

        return single_instance;
    }

    public String getJDAToken() {
        return prop.getProperty("JDAToken");
    }

    public String getSpotifyClientId() {
        return prop.getProperty("SpotifyClientId");
    }

    public String getSpotifyClientSecret() {
        return prop.getProperty("SpotifyClientSecret");
    }

    public String getOpenWeatherMapsAppId() {
        return prop.getProperty("OWMAppid");
    }

}
