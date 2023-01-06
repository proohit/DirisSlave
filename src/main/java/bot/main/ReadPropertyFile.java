package bot.main;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.tinylog.Logger;

public class ReadPropertyFile {
    private static final String VERSION_PROPERTY = "version";

    private static Properties prop = new Properties();

    private static ReadPropertyFile singleInstance = null;

    private ReadPropertyFile() throws IOException {
        try (FileInputStream ip = new FileInputStream("config/config.properties")) {
            Properties versionProps = new Properties();
            versionProps.load(this.getClass().getResourceAsStream("/version.properties"));
            String version = versionProps.getProperty(VERSION_PROPERTY);
            prop.load(ip);
            prop.setProperty(VERSION_PROPERTY, version);
        }
    }

    // static method to create instance of Singleton class
    public static ReadPropertyFile getInstance() {
        if (singleInstance == null)
            try {
                singleInstance = new ReadPropertyFile();
            } catch (IOException e) {
                Logger.error(e);
            }

        return singleInstance;
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

    public String getPixabayKey() {
        return prop.getProperty("PixabayKey");
    }

    public String getDbHost() {
        return prop.getProperty("dbHost");
    }

    public String getDbDatabase() {
        return prop.getProperty("dbDatabase");
    }

    public String getDbPassword() {
        return prop.getProperty("dbPassword");
    }

    public String getDbUser() {
        return prop.getProperty("dbUser");
    }

    public String getVersion() {
        return prop.getProperty(VERSION_PROPERTY);
    }
}
